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

    private int port;
    private ServerManager serverManager;
    private ServerSocket serverSocket;
    private boolean keepAlive = true;
    //private Map<Socket, Boolean> clientReady = new HashMap<>();
    private Map<Socket, Scanner> fromClient = new HashMap<>();
    private Map<Socket, PrintWriter> toClient = new HashMap<>();

    public SocketServer(ServerManager server, int port) {
        this.serverManager = server;
        this.port = port;
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
            //serverManager.removeClient(addressee);
            unregistry(addressee);
            return "Impossibile raggiungere il client." + e.getMessage();
        }
    }

    public void registry(Socket client) throws IOException {
        serverManager.addClient(client);
        fromClient.put(client, new Scanner(client.getInputStream()));
        toClient.put(client, new PrintWriter(client.getOutputStream(), true));
        int number = serverManager.getNumber(client);
        System.out.println("User " + number + " accettato sul SocketServer");
        new Thread(new ClientReception(serverManager, number)).start();
    }

    public void unregistry(Socket client) {
        fromClient.remove(client);
        toClient.remove(client);
        serverManager.removeClient(client);
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