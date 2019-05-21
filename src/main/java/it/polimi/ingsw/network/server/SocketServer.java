package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class SocketServer implements Runnable {

    private int port = 9000;
    private ServerManager serverManager;
    private ServerSocket serverSocket;
    private boolean keepAlive = true;
    //private Map<Socket, Boolean> clientReady = new HashMap<>();
    private Map<Socket, Scanner> fromClient = new HashMap<>();
    private Map<Socket, PrintWriter> toClient = new HashMap<>();

    public SocketServer(ServerManager server) {
        this.serverManager = server;
    }

    public void run() {
        try {
            startServer();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String sendMessageAndGetAnswer(Socket addressee, String message) {
        toClient.get(addressee).println(message);
        try {
            return fromClient.get(addressee).nextLine();
        } catch (NoSuchElementException e) {
            serverManager.removeClient(addressee);
            return "Impossibile raggiungere il client.";
        }
    }

    public synchronized void registry(Socket client) throws IOException {
        serverManager.addClient(client);
        fromClient.put(client, new Scanner(client.getInputStream()));
        toClient.put(client, new PrintWriter(client.getOutputStream(), true));
        //clientReady.put(client, false);
        int number = serverManager.getNumber(client);
        System.out.println("User " + number + " accettato sul SocketServer");
        //serverManager.bidWelcome(client);
        serverManager.addClientToLobby(number);
    }

    public void unregistry(Socket client) {
        fromClient.remove(client);
        toClient.remove(client);
    }

    public void stopServer() {
        keepAlive = false;
    }

    public void startServer() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("SocketServer avviato.");
        serverManager.setSocketReady();
        new Thread(new SocketReceptionist(serverSocket, this)).start();
        while (keepAlive) {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("SocketServer spento.");
    }

}