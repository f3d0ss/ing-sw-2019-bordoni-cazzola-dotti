package it.polimi.ingsw.network.server;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerManager implements Runnable {

    private Map<Integer, Socket> socketClients = new HashMap<>();
    private Map<Integer, RmiClientInterface> rmiClients = new HashMap<>();
    private SocketServer socketServer;
    private RmiServer rmiServer;
    private String answer;
    private boolean answerArrived;
    private int idClient = 0;

    public void addClient(Socket client) {
        socketClients.put(idClient, client);
        idClient++;
    }

    public void addClient(RmiClientInterface client) {
        rmiClients.put(idClient, client);
        idClient++;
    }

    public int getNumber(Socket client) throws NoSuchElementException{
        for (int i = 0; i < idClient; i++)
            if (socketClients.get(i) == client)
                return i;
        throw new NoSuchElementException();
    }

    public int getNumber(RmiClientInterface client) throws NoSuchElementException{
        for (int i = 0; i < idClient; i++)
            if (rmiClients.get(i) == client)
                return i;
        throw new NoSuchElementException();
    }

    public boolean isActive(int number){
        return socketClients.containsKey(number) || rmiClients.containsKey(number);
    }

    public synchronized void removeClient(Socket client) {
        int number = getNumber(client);
        socketClients.remove(number);
        socketServer.unregistry(client);
        System.out.println("Client " + number + " rimosso.");
    }

    public synchronized void removeClient(RmiClientInterface client) {
        int number = getNumber(client);
        rmiClients.remove(number);
        System.out.println("Client " + number + " rimosso.");
    }

    public void printClients() {
        socketClients.forEach((integer, socket) -> System.out.printf("%d ", integer));
        rmiClients.forEach((integer, rmi) -> System.out.printf("%d ", integer));
    }

    public void sendMessageAndWaitForAnswer(int number, String message) {
        if (socketClients.containsKey(number)) {
            new Thread(new SocketCommunication(message, socketClients.get(number), socketServer, number, this)).start();
        } else if (rmiClients.containsKey(number)) {
            new Thread(new RmiCommunication(message, rmiClients.get(number), rmiServer, number, this)).start();
        } else
            System.out.println("Client non registrato");
    }

    public void sendMessage(int number, String message){

    }

    public void shutDownAllServers(){
        socketServer.stopServer();
        //TODO: can rmiServer be stopped?
    }

    @Override
    public void run() {
        socketServer = new SocketServer(this);
        rmiServer = new RmiServer(this);
        Scanner stdin = new Scanner(System.in);
        String message;
        new Thread(socketServer).start();
        new Thread(rmiServer).start();
    }
}
