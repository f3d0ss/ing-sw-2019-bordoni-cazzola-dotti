package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.network.Message;

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
    private GameCountDown countDown = new GameCountDown(this);

    public void addClient(Socket client) {
        socketClients.put(idClient, client);
        idClient++;
    }

    public void addClient(RmiClientInterface client) {
        rmiClients.put(idClient, client);
        idClient++;
    }

    public void addClientToLobby(int id) {
        lobby.put(id, "");
        askForNickname(id);
        if (lobby.size() == 3) {
            new Thread(countDown).start();
        } else if (lobby.size() == 5) {
            countDown.reset();
            startNewGame();
        }
    }

    private void askForNickname(int id) {
        String nickname;
        List<String> possibleAnswers = new ArrayList<>();
        String question = "Benvenuto su Adrenalina!\nSei stato accettato con il codice " + id + ". Memorizzalo per riconnetterti a seguito di disconnessioni impreviste.\n";
        possibleAnswers.add("string");
        if (lobby.size() == 1) {
            question = question + "Sei il primo giocatore; ";
        } else {
            question = question + "Sono in attesa di una nuova partita: ";
            for (int i : lobby.keySet())
                if (!lobby.get(i).equals(""))
                    question = question + lobby.get(i) + "; ";
        }
        question = question + "digita il tuo nickname:";
        nickname = sendMessageAndWaitForAnswer(id, new Message(question, possibleAnswers, 0));
        while (lobby.containsValue(nickname)) {
            question = "Nickname già presente. Scegline un altro: ";
            nickname = sendMessageAndWaitForAnswer(id, new Message(question, possibleAnswers, 0));
        }
        lobby.put(id, answers.get(id));
    }

    public void startNewGame() {
        //TODO: create a new game (controller)
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
        answers.put(client, answer);
        answerReady.put(client, true);
    }

    public boolean isActive(int number) {
        return socketClients.containsKey(number) || rmiClients.containsKey(number);
    }

    public synchronized void removeClient(Socket client) {
        int number = getNumber(client);
        socketClients.remove(number);
        socketServer.unregistry(client);
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
        lobby.remove(number);
        if (lobby.size() < 3 && countDown.isAlive())
            countDown.reset();
    }

    public void printClients() {
        socketClients.forEach((integer, socket) -> System.out.printf("%d ", integer));
        rmiClients.forEach((integer, rmi) -> System.out.printf("%d ", integer));
    }

    public String sendMessageAndWaitForAnswer(int number, Message message) {
        answerReady.put(number, false);
        Gson gson = new Gson();
        String json = gson.toJson(message);
        if (socketClients.containsKey(number)) {
            new Thread(new SocketCommunication(json, socketClients.get(number), socketServer, number, this)).start();
        } else if (rmiClients.containsKey(number)) {
            new Thread(new RmiCommunication(json, rmiClients.get(number), rmiServer, number, this)).start();
        } else
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
    }
}
