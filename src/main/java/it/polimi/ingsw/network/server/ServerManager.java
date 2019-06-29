package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.MatchController;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.utils.Parser;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.VirtualView;

import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;

import static java.lang.Thread.sleep;

/**
 * This class manages all communications on the server-side.
 */

public class ServerManager implements Runnable {

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 5;
    private static final int DEFAULT_BOARD = 1;
    private static final int MILLIS_TO_WAIT = 10;
    private static final int MILLIS_IN_SECOND = 1000;
    private static final String RECONNECT = "Reconnect";
    private static final String NEW_GAME = "New game";
    private int socketPort;
    private int rmiPort;
    private int skulls;
    private Map<Integer, Socket> socketClients = new HashMap<>();
    private Map<Integer, RmiClientInterface> rmiClients = new HashMap<>();
    private Map<Integer, String> answers = new HashMap<>();
    private Map<Integer, Boolean> answerReady = new HashMap<>();
    private SocketServer socketServer;
    private RmiServer rmiServer;
    private int idClient = 12345;
    private Map<Integer, String> lobby = new HashMap<>();
    private List<Integer> awayFromKeyboardOrDisconnected = new ArrayList<>();
    private Map<Integer, String> nicknames = new HashMap<>();
    private GameCountDown countDown;
    private Parser parser = new Parser();
    private int chosenBoard = 0;
    private int secondsDuringTurn;
    private Map<Integer, MatchController> activeMatches = new HashMap<>();

    public ServerManager(int secondsAfterThirdConnection, int secondsDuringTurn, int socketPort, int rmiPort, int skulls) {
        countDown = new GameCountDown(this, secondsAfterThirdConnection);
        this.secondsDuringTurn = secondsDuringTurn;
        this.socketPort = socketPort;
        this.rmiPort = rmiPort;
        this.skulls = skulls;
    }

    /**
     * Adds clients that use socket technology.
     *
     * @param client is the socket of the incoming client
     */

    public void addClient(Socket client) {
        socketClients.put(idClient, client);
        answerReady.put(idClient, true);
        idClient++;
    }

    /**
     * Checks if a client is marked as disconnected.
     *
     * @param code is the unique code of the client
     * @return true if the client is disconnected
     */

    public boolean isAwayFromKeyboardOrDisconnected(int code) {
        return awayFromKeyboardOrDisconnected.contains(code);
    }

    /**
     * Adds clients that use rmi technology.
     *
     * @param client is the rmi interface of the incoming client
     */

    public void addClient(RmiClientInterface client) {
        rmiClients.put(idClient, client);
        answerReady.put(idClient, true);
        idClient++;
    }

    public String getNickname(int playerId) {
        return nicknames.get(playerId);
    }

    /**
     * Manages the enrollment of a incoming client, asking him if he wants to start a new game
     * or if wants to reconnect to an ongoing match.
     *
     * @param temporaryId is the code of the incoming client
     */

    public void addClientToLog(int temporaryId) {
        String code;
        int oldId;
        while (true) {
            String reconnect = sendMessageAndWaitForAnswer(temporaryId, new Message(Protocol.RECONNECT, "", Arrays.asList(NEW_GAME, RECONNECT)));
            if (reconnect.equals(Protocol.ERR))
                break;
            else if (reconnect.equals(RECONNECT)) {
                code = sendMessageAndWaitForAnswer(temporaryId, new Message(Protocol.INSERT_OLD_CODE, "", null));
                if (code.equals(Protocol.ERR))
                    break;
                if (checkIfDisconnected(code)) {
                    oldId = Integer.parseInt(code);
                    if (!switchClientId(oldId, temporaryId))
                        break;
                    awayFromKeyboardOrDisconnected.remove((Object) oldId);
                    sendMessageAndWaitForAnswer(oldId, new Message(Protocol.WELCOME_BACK, nicknames.get(oldId), null));
                    activeMatches.get(oldId).reconnect(nicknames.get(oldId));
                    break;
                } else
                    sendMessageAndWaitForAnswer(temporaryId, new Message(Protocol.INVALID_OLD_CODE, "", null));
            } else {
                addClientToLobby(temporaryId);
                break;
            }
        }
    }

    /**
     * Checks if a client is effectively disconnected.
     *
     * @param code is the unique code of the client
     * @return true if a client is disconnected even after having tried to communicate with him one last time
     */

    private boolean checkIfDisconnected(String code) {
        int oldId;
        try {
            oldId = Integer.parseInt(code);
        } catch (NumberFormatException e) {
            return false;
        }
        if (isAwayFromKeyboardOrDisconnected(oldId))
            return true;
        if (!answerReady.get(oldId))
            return false;
        sendMessageAndWaitForAnswer(oldId, new Message(Protocol.ARE_YOU_ALIVE, "", null));
        if (isAwayFromKeyboardOrDisconnected(oldId))
            return true;
        return false;
    }

    /**
     * Reassigns the old code to a client that is reconnecting to an ongoing match.
     *
     * @param oldId       is the old code of the client
     * @param temporaryId is the new code of the client, assigned during reconnection
     * @return true if reassignment successes
     */

    private boolean switchClientId(int oldId, int temporaryId) {
        if (socketClients.containsKey(temporaryId)) {
            socketClients.put(oldId, socketClients.get(temporaryId));
            socketClients.remove(temporaryId);
            return true;
        }
        if (rmiClients.containsKey(temporaryId)) {
            rmiClients.put(oldId, rmiClients.get(temporaryId));
            rmiClients.remove(temporaryId);
            return true;
        }
        return false;
    }

    /**
     * Adds a client to the lobby after having concluded the enrollment.
     *
     * @param id is the code of the client
     */

    public void addClientToLobby(int id) {
        login(id);
        if (lobby.size() == MIN_PLAYERS) {
            if (!countDown.isRunning())
                new Thread(countDown).start();
        } else if (lobby.size() == MAX_PLAYERS) {
            countDown.stopCount();
            checkAllConnections();
        }
    }

    /**
     * Manages the login asking for nickname and, if the client is the first of the lobby,
     * the board on which play the match.
     *
     * @param id is the code of the incoming client
     */

    private void login(int id) {
        String name;
        if (sendMessageAndWaitForAnswer(id, new Message(Protocol.WELCOME, String.valueOf(id), null)).equals(Protocol.ERR))
            return;
        notifyNewConnection(id);
        name = sendMessageAndWaitForAnswer(id, new Message(Protocol.LOGIN_FIRST, "", null));
        if (name.equals(Protocol.ERR))
            return;
        while (lobby.containsValue(name)) {
            name = sendMessageAndWaitForAnswer(id, new Message(Protocol.LOGIN_REPEAT, "", null));
            if (name.equals(Protocol.ERR))
                return;
        }
        if (chosenBoard == 0) {
            chosenBoard = DEFAULT_BOARD;
            String ans = sendMessageAndWaitForAnswer(id, new Message(Protocol.CHOOSE_BOARD, "", Arrays.asList("Board1", "Board2", "Board3", "Board4")));
            if (ans.equals(Protocol.ERR))
                return;
            else
                try {
                    chosenBoard = Integer.parseInt(ans.substring(5, 6));
                } catch (NumberFormatException e) {
                    chosenBoard = DEFAULT_BOARD;
                }
        }
        lobby.put(id, name);
        notifyNewEntry(id, name);
        if (sendMessageAndWaitForAnswer(id, new Message(Protocol.LOGIN_CONFIRM, name, null)).equals(Protocol.ERR))
            return;
    }

    /**
     * Notifies all clients already logged in that a new client has concluded the enrollment
     * and keeps up-to-date them about the time left before the game start.
     *
     * @param id       is the code of the last client logged in
     * @param newEntry is the name of the last client logged in
     */

    private void notifyNewEntry(int id, String newEntry) {
        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients) {
            if (answerReady.get(i)) {
                if (i != id)
                    sendMessageAndWaitForAnswer(i, new Message(Protocol.NEW_ENTRY, newEntry, null));
                notifyTimeLeft(i, clients.length);
            }
        }
    }

    /**
     * Notifies all clients already logged in that a new client has connected.
     *
     * @param id is the code of the incoming client
     */

    private void notifyNewConnection(int id) {
        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients) {
            if (i != id && answerReady.getOrDefault(i, false)) {
                sendMessageAndWaitForAnswer(i, new Message(Protocol.NEW_CONNECTION, "", null));
            }
        }
    }

    /**
     * Notifies a client how much time is left (if the minimum players number is already reached)
     * or how many players miss (if the minimum players number is not yet reached).
     *
     * @param id   is the code of the addressee client
     * @param size is the current size of the lobby
     */

    private void notifyTimeLeft(int id, int size) {
        if (size < MIN_PLAYERS)
            sendMessageAndWaitForAnswer(id, new Message(Protocol.WAIT_FOR_PLAYERS, String.valueOf(MIN_PLAYERS - size), null));
        else
            sendMessageAndWaitForAnswer(id, new Message(Protocol.COUNTDOWN, String.valueOf(countDown.getTimeLeft()), null));
    }

    /**
     * Sends a message to all clients in lobby in order to check theirs presence.
     */

    private void lastCheckBeforeGameStarting() {
        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients)
            sendMessageAndWaitForAnswer(i, new Message(Protocol.ARE_YOU_READY, "", null));
    }

    /**
     * Notifies all clients in lobby that the game is starting.
     */

    private void notifyGameStarting() {
        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients)
            sendMessageAndWaitForAnswer(i, new Message(Protocol.LET_US_START, "", null));
    }

    /**
     * Checks connection of all clients in lobby and
     * if minimum players number is satisfied starts a new match.
     */

    public void checkAllConnections() {
        lastCheckBeforeGameStarting();
        if (lobby.size() >= MIN_PLAYERS) {
            notifyGameStarting();
            createNewGame();
            chosenBoard = 0;
            lobby.clear();
            countDown.restore();
        }
    }

    /**
     * Starts a new match.
     */

    private void createNewGame() {
        Map<String, ViewInterface> gamers = new HashMap<>();
        lobby.keySet().forEach(i -> gamers.put(lobby.get(i), new VirtualView(this, i)));
        MatchController match = new MatchController(gamers, chosenBoard, skulls);
        lobby.keySet().forEach(i -> {
            activeMatches.put(i, match);
            nicknames.put(i, lobby.get(i));
        });
        gamers.values().forEach(v -> ((VirtualView) v).setController(match));
        //TODO handle disconnections during modelupdate
        match.sendFirstStateOfModel();
        new Thread(match).start();
    }

    /**
     * Removes the match reference associated to a client and removes all the client's references.
     *
     * @param playerId is the number of client
     */

    public void removeGame(int playerId) {
        activeMatches.remove(playerId);
        awayFromKeyboardOrDisconnected.remove((Object) playerId);
        if(socketClients.containsKey(playerId))
            removeClient(socketClients.get(playerId));
        if(rmiClients.containsKey(playerId))
            removeClient(rmiClients.remove(playerId));
    }

    /**
     * @param client is the client using socket
     * @return the unique number associated to the client
     */

    public int getNumber(Socket client) {
        for (int i : socketClients.keySet())
            if (socketClients.get(i) == client)
                return i;
        throw new NoSuchElementException();
    }

    /**
     * @param client is the client using rmi
     * @return the unique number associated to the client
     */

    public int getNumber(RmiClientInterface client) {
        for (int i : rmiClients.keySet())
            if (rmiClients.get(i) == client)
                return i;
        throw new NoSuchElementException();
    }

    /**
     * Sets the answer of a client coming from the last comminication.
     *
     * @param client is the number of the client
     * @param answer is the string containing the client's answer
     */

    public void setAnswer(int client, String answer) {
        if (!answer.equals(Protocol.ACK))
            answers.put(client, answer);
        answerReady.put(client, true);
    }

    /**
     * Removes a client using socket.
     *
     * @param client is the socket of the client
     */

    public void removeClient(Socket client) {
        try {
            int number = getNumber(client);
            socketClients.remove(number);
            removeClient(number);
        } catch (NoSuchElementException e) {
        }
    }

    /**
     * Removes a client using rmi.
     *
     * @param client is the rmi interface of the client
     */

    public void removeClient(RmiClientInterface client) {
        try {
            int number = getNumber(client);
            rmiClients.remove(number);
            removeClient(number);
        } catch (NoSuchElementException e) {
        }
    }

    /**
     * Removes a client regardless the communication technology used.
     *
     * @param number is the unique number of the client
     */

    private void removeClient(int number) {
        if (lobby.containsKey(number))
            removeClientFromLobby(number);
        else if (activeMatches.containsKey(number)) {
            awayFromKeyboardOrDisconnected.add(number);
            activeMatches.get(number).disconnect(nicknames.get(number));
        }
        System.out.println("Client " + number + " rimosso.");
    }

    /**
     * Removes a client from the lobby, notifying others clients. If the minimum number had been reached,
     * it checks if that number is already satisfied, otherwise stops the countdown.
     *
     * @param number is the outgoing client
     */

    private void removeClientFromLobby(int number) {
        String name = lobby.get(number);
        lobby.remove(number);
        if (lobby.size() < MIN_PLAYERS)
            countDown.stopCount();
        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients) {
            sendMessageAndWaitForAnswer(i, new Message(Protocol.REMOVAL, name, null));
            notifyTimeLeft(i, clients.length);//to be removed?
        }
    }

    /**
     * @param user is the client
     * @return true if the client is ready to receive message,
     * false if it already have to reply to the last message
     */

    public boolean isListening(int user) {
        return answerReady.get(user);
    }

    /**
     * Manages the delivery of a message and the wait of client's answer measuring time:
     * if time exceeds before the answer's arrival, the client is marked as afk.
     *
     * @param number is the unique code of the addressee
     * @param message is the outgoing message
     * @return the client's answer
     */

    public String sendMessageAndWaitForAnswer(int number, Message message) {
        if (isAwayFromKeyboardOrDisconnected(number))
            return Protocol.ERR;
        String serializedMessage = parser.serialize(message);
        while (!answerReady.get(number)) {
            try {
                sleep(MILLIS_TO_WAIT);
            } catch (InterruptedException e) {
                break;
            }
        }
        answerReady.put(number, false);
        SingleCommunication communication;
        if (socketClients.containsKey(number)) {
            communication = new SocketCommunication(serializedMessage, socketClients.get(number), socketServer, number, this);
            new Thread(communication).start();
        } else if (rmiClients.containsKey(number)) {
            communication = new RmiCommunication(serializedMessage, rmiClients.get(number), rmiServer, number, this);
            new Thread(communication).start();
        } else {
            System.out.println("Client non registrato");
            return Protocol.ERR;
        }
        boolean isTimeExceeded = false;
        int counter = 0;
        while (!answerReady.get(number)) {
            try {
                sleep(MILLIS_TO_WAIT);
                counter++;
                if (counter % 10 == 0 && rmiClients.containsKey(number)) {
                    try {
                        rmiClients.get(number).testAliveness();
                    } catch (RemoteException e) {
                        System.out.println("Impossibile raggiungere il client. " + e.getMessage());
                        rmiServer.unregister(rmiClients.get(number));
                        return Protocol.ERR;
                    }
                }
            } catch (InterruptedException e) {
                break;
            }
            if (counter > secondsDuringTurn * MILLIS_IN_SECOND / MILLIS_TO_WAIT) {
                isTimeExceeded = true;
                break;
            }
        }
        if (isTimeExceeded) {
            communication.setTimeExceeded();
            awayFromKeyboardOrDisconnected.add(number);
            activeMatches.get(number).disconnect(nicknames.get(number));
            return Protocol.ERR;
        }
        return answers.get(number);
    }

    /**
     * Starts one server for each type of communication technology.
     */

    @Override
    public void run() {
        socketServer = new SocketServer(this, socketPort);
        rmiServer = new RmiServer(this, rmiPort);
        new Thread(socketServer).start();
        new Thread(rmiServer).start();
    }
}
