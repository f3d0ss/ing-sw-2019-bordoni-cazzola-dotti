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

public class ServerManager implements Runnable {

    public final static int MIN_PLAYERS = 2;
    private final static int MAX_PLAYERS = 3;
    private final static int DEFAULT_BOARD = 1;
    private final static int MILLIS_TO_WAIT = 10;
    private final static int MILLIS_IN_SECOND = 1000;
    private final static String RECONNECT = "Reconnect";
    private final static String NEW_GAME = "New game";
    private int socketPort;
    private int rmiPort;
    private int skulls;
    private Map<Integer, Socket> socketClients = new HashMap<>();
    private Map<Integer, RmiClientInterface> rmiClients = new HashMap<>();
    private Map<Integer, String> answers = new HashMap<>();
    private Map<Integer, Boolean> answerReady = new HashMap<>();
    private SocketServer socketServer;
    private RmiServer rmiServer;
    private boolean socketServerReady = false;
    private boolean rmiServerReady = false;
    private int idClient = 12345;
    private Map<Integer, String> lobby = new HashMap<>();
    private List<Integer> connected = new ArrayList<>();
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

    public void addClient(Socket client) {
        socketClients.put(idClient, client);
        answerReady.put(idClient, true);
        idClient++;
    }

    public boolean isAwayFromKeyboardOrDisconnected(int code) {
        return awayFromKeyboardOrDisconnected.contains(code);
    }

    public void addClient(RmiClientInterface client) {
        rmiClients.put(idClient, client);
        answerReady.put(idClient, true);
        idClient++;
    }

    public String getNickname(int playerId) {
        return nicknames.get(playerId);
    }

    public void addClientToLog(int temporaryId) {
        connected.add(temporaryId);
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
                oldId = Integer.parseInt(code);
                if (checkIfDisconnected(oldId)) {
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

    private boolean checkIfDisconnected(int oldId) {
        if (isAwayFromKeyboardOrDisconnected(oldId))
            return true;
        if (!answerReady.get(oldId))
            return false;
        sendMessageAndWaitForAnswer(oldId, new Message(Protocol.ARE_YOU_ALIVE, "", null));
        if (isAwayFromKeyboardOrDisconnected(oldId))
            return true;
        return false;
    }

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

    public void addClientToLobby(int id) {
        connected.add(id);
        login(id);
        connected.remove((Object) id);
        if (lobby.size() == MIN_PLAYERS) {
            if (!countDown.isRunning())
                new Thread(countDown).start();
        } else if (lobby.size() == MAX_PLAYERS) {
            countDown.stopCount();
            checkAllConnections();
        }
    }

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

    private void notifyNewConnection(int id) {
        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients) {
            if (i != id && answerReady.getOrDefault(i, false)) {
                sendMessageAndWaitForAnswer(i, new Message(Protocol.NEW_CONNECTION, "", null));
                //notifyTimeLeft(i, clients.length);
            }
        }
    }

    private void notifyTimeLeft(int id, int size) {
        if (size < MIN_PLAYERS)
            sendMessageAndWaitForAnswer(id, new Message(Protocol.WAIT_FOR_PLAYERS, String.valueOf(MIN_PLAYERS - size), null));
        else
            sendMessageAndWaitForAnswer(id, new Message(Protocol.COUNTDOWN, String.valueOf(countDown.getTimeLeft()), null));
    }

    private void lastCheckBeforeGameStarting() {
        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients)
            sendMessageAndWaitForAnswer(i, new Message(Protocol.ARE_YOU_READY, "", null));
    }

    private void notifyGameStarting() {
        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients)
            sendMessageAndWaitForAnswer(i, new Message(Protocol.LET_US_START, "", null));
    }

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

    public void removeGame(int playerId) {
        activeMatches.remove(playerId);
        awayFromKeyboardOrDisconnected.remove((Object) playerId);
    }

    public boolean allServerReady() {
        return rmiServerReady && socketServerReady;
    }

    public void setRmiReady() {
        rmiServerReady = true;
    }

    public void setSocketReady() {
        socketServerReady = true;
    }

    public int getNumber(Socket client) {
        for (int i : socketClients.keySet())
            if (socketClients.get(i) == client)
                return i;
        throw new NoSuchElementException();
    }

    public int getNumber(RmiClientInterface client) {
        for (int i : rmiClients.keySet())
            if (rmiClients.get(i) == client)
                return i;
        throw new NoSuchElementException();
    }

    public void setAnswer(int client, String answer) {
        if (!answer.equals(Protocol.ACK))
            answers.put(client, answer);
        answerReady.put(client, true);
    }

    public boolean isActive(int number) {
        return socketClients.containsKey(number) || rmiClients.containsKey(number);
    }

    public void removeClient(Socket client) {
        try {
            int number = getNumber(client);
            socketClients.remove(number);
            removeClient(number);
        } catch (NoSuchElementException e) {
        }
    }

    public void removeClient(RmiClientInterface client) {
        try {
            int number = getNumber(client);
            rmiClients.remove(number);
            removeClient(number);
        } catch (NoSuchElementException e) {
        }
    }

    private void removeClient(int number) {
        if (lobby.containsKey(number))
            removeClientFromLobby(number);
        else if (activeMatches.containsKey(number)) {
            awayFromKeyboardOrDisconnected.add(number);
            activeMatches.get(number).disconnect(nicknames.get(number));
        }
        System.out.println("Client " + number + " rimosso.");
    }

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

    public void printClients() {
        socketClients.forEach((integer, socket) -> System.out.printf("%d ", integer));
        rmiClients.forEach((integer, rmi) -> System.out.printf("%d ", integer));
    }

    public boolean isListening(int user){
        return answerReady.get(user);
    }

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
                        //System.out.println("test rmi");
                        rmiClients.get(number).testAliveness();
                    } catch (RemoteException e) {
                        System.out.println("Impossibile raggiungere il client. " + e.getMessage());
                        rmiServer.unregistry(rmiClients.get(number));
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

    public void shutDownAllServers() {
        socketServer.stopServer();
        //TODO: can rmiServer be stopped?
    }

    @Override
    public void run() {
        socketServer = new SocketServer(this, socketPort);
        rmiServer = new RmiServer(this, rmiPort);
        new Thread(socketServer).start();
        new Thread(rmiServer).start();
    }
}
