package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.utils.Parser;

import java.net.Socket;
import java.util.*;

import static java.lang.Thread.sleep;

public class ServerManager implements Runnable {

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
    private GameCountDown countDown = new GameCountDown(this, 20);
    private Parser parser = new Parser();

    public void addClient(Socket client) {
        socketClients.put(idClient, client);
        answerReady.put(idClient, true);
        idClient++;
    }

    public void addClient(RmiClientInterface client) {
        rmiClients.put(idClient, client);
        answerReady.put(idClient, true);
        idClient++;
    }

    public void addClientToLobby(int id) {
        connected.add(id);
        lobby.put(id, "");
        login(id);
        if (lobby.size() == 3) {
            countDown.restore();
            new Thread(countDown).start();
            //notifyTimeLeft();
        } else if (lobby.size() == 5) {
            countDown.reset();
            checkAllConnections();
        }
    }

    private void login(int id) {
        sendMessageAndWaitForAnswer(id, new Message(Protocol.WELCOME, String.valueOf(id), null, 0));
        notifyNewConnection(id);
        if (lobby.size() == 1) {
            sendMessageAndWaitForAnswer(id, new Message(Protocol.LOGIN_FIRST, "", null, 0));
        } else {
            String playerAlreadyIn = "";
            List<String> players = new ArrayList<>();
            for (int i : lobby.keySet())
                if (!lobby.get(i).equals("")) {
                    playerAlreadyIn = playerAlreadyIn + lobby.get(i) + "; ";
                    players.add(lobby.get(i));
                }
            sendMessageAndWaitForAnswer(id, new Message(Protocol.LOGIN_OTHERS, playerAlreadyIn, null, 0));
            while (lobby.containsValue(answers.get(id))) {
                sendMessageAndWaitForAnswer(id, new Message(Protocol.LOGIN_REPEAT, "", null, 0));
            }
        }
        String name = answers.get(id);
        lobby.put(id, name);
        sendMessageAndWaitForAnswer(id, new Message(Protocol.LOGIN_CONFIRM, "", null, 0));
        if (lobby.size() == 1)
            chooseBoard(id);
        notifyNewEntry(id, name);
    }

    private void notifyNewEntry(int id, String newEntry) {
        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients) {
            if (i != id)
                sendMessageAndWaitForAnswer(i, new Message(Protocol.NEW_ENTRY, newEntry, null, 0));
            notifyTimeLeft(i, clients.length);
        }
    }

    private void notifyNewConnection(int id) {
        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients) {
            if (i != id)
                sendMessageAndWaitForAnswer(i, new Message(Protocol.NEW_CONNECTION, "", null, 0));
            notifyTimeLeft(i, clients.length);
        }
    }

    private void notifyTimeLeft(int id, int size) {
        if (size < 3)
            sendMessageAndWaitForAnswer(id, new Message(Protocol.WAIT_FOR_PLAYERS, String.valueOf(3 - size), null, 0));
        else
            sendMessageAndWaitForAnswer(id, new Message(Protocol.COUNTDOWN, String.valueOf(countDown.getTimeLeft()), null, 0));
    }

    private void notifyGameStarting() {
        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients)
            sendMessageAndWaitForAnswer(i, new Message(Protocol.ARE_YOU_READY, "", null, 0));
    }

    private void chooseBoard(int id) {
        sendMessageAndWaitForAnswer(id, new Message(Protocol.CHOOSE_BOARD, "", Arrays.asList("Board1", "Board2", "Board3", "Board4"), 0));
        //TODO:
    }

    public void checkAllConnections() {
        notifyGameStarting();
        if (lobby.size() >= 3) {
            lobby.clear();
            //TODO: create a new game (controller)
        }
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
        for (int i = 0; i < idClient; i++)
            if (socketClients.get(i) == client)
                return i;
        throw new NoSuchElementException();
    }

    public int getNumber(RmiClientInterface client) {
        for (int i = 0; i < idClient; i++)
            if (rmiClients.get(i) == client)
                return i;
        throw new NoSuchElementException();
    }

    public void setAnswer(int client, String answer) {
        if (!answer.equals(Protocol.ack))
            answers.put(client, answer);
        answerReady.put(client, true);
    }

    public boolean isActive(int number) {
        return socketClients.containsKey(number) || rmiClients.containsKey(number);
    }

    public synchronized void removeClient(Socket client) {
        int number = getNumber(client);
        socketClients.remove(number);
        //socketServer.unregistry(client);
        removeClientFromLobby(number);
        System.out.println("Client " + number + " rimosso.");
    }

    public synchronized void removeClient(RmiClientInterface client) {
        int number = getNumber(client);
        rmiClients.remove(number);
        removeClientFromLobby(number);
        System.out.println("Client " + number + " rimosso.");
    }

    private void removeClientFromLobby(int number) {
        String name = lobby.get(number);
        lobby.remove(number);
        if (lobby.size() < 3 && countDown.isAlive())
            countDown.reset();
        Integer[] clients = lobby.keySet().toArray(new Integer[0]);
        for (int i : clients) {
            sendMessageAndWaitForAnswer(i, new Message(Protocol.REMOVAL, name, null, 0));
            notifyTimeLeft(i, clients.length);
        }
    }

    public void printClients() {
        socketClients.forEach((integer, socket) -> System.out.printf("%d ", integer));
        rmiClients.forEach((integer, rmi) -> System.out.printf("%d ", integer));
    }

    public String sendMessageAndWaitForAnswer(int number, Message message) {
        while (!answerReady.get(number)) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        answerReady.put(number, false);
        String serializedMessage = parser.serialize(message);
        if (socketClients.containsKey(number))
            new Thread(new SocketCommunication(serializedMessage, socketClients.get(number), socketServer, number, this)).start();
        else if (rmiClients.containsKey(number))
            new Thread(new RmiCommunication(serializedMessage, rmiClients.get(number), rmiServer, number, this)).start();
        else
            System.out.println("Client non registrato");
        while (!answerReady.get(number)) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        return answers.get(number);
    }
/*
    public void sendPing(int number) {
        if (socketClients.containsKey(number))
            socketServer.sendMessageAndGetAnswer(socketClients.get(number), Protocol.ping);
        else if (rmiClients.containsKey(number))
            rmiServer.getImplementation().sendMessageAndGetAnswer(rmiClients.get(number), Protocol.ping);
        else
            System.out.println("Client non registrato");
    }*/

    public void shutDownAllServers() {
        socketServer.stopServer();
        //TODO: can rmiServer be stopped?
    }

    @Override
    public void run() {
        socketServer = new SocketServer(this);
        rmiServer = new RmiServer(this);
        new Thread(socketServer).start();
        new Thread(rmiServer).start();
        while (true) {
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
            /*
            Integer[] clients = lobby.keySet().toArray(new Integer[0]);
            for (int i : clients)
                if (answerReady.get(i))
                    sendPing(i);*/
        }
    }
}
