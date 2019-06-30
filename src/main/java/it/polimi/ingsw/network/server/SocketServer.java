package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class represent the server for communications using socket.
 */

class SocketServer implements Runnable {

    private final int port;
    private final ServerManager serverManager;
    private final Map<Socket, Scanner> fromClient = new HashMap<>();
    private final Map<Socket, PrintWriter> toClient = new HashMap<>();
    private boolean keepAlive = true;

    SocketServer(ServerManager server, int port) {
        this.serverManager = server;
        this.port = port;
    }

    /**
     * Sends a message to a socket client. If the client is unreachable, it unregisters it.
     *
     * @param addressee is the socket of the addressee
     * @param message   is the string containing the sending message
     * @return is the answer coming from the client
     */

    String sendMessageAndGetAnswer(Socket addressee, String message) {
        toClient.get(addressee).println(message);
        try {
            return fromClient.get(addressee).nextLine();
        } catch (NoSuchElementException e) {
            unregister(addressee);
            return "Impossibile raggiungere il client." + e.getMessage();
        }
    }

    /**
     * Registries a new socket client on the server manager,
     * starting a thread devoted to look after the enrollment.
     *
     * @param client is the rmi interface of the new client
     */

    private void registry(Socket client) throws IOException {
        serverManager.addClient(client);
        fromClient.put(client, new Scanner(client.getInputStream()));
        toClient.put(client, new PrintWriter(client.getOutputStream(), true));
        int number = serverManager.getNumber(client);
        System.out.println("User " + number + " accettato sul SocketServer");
        new Thread(new ClientReception(serverManager, number)).start();
    }

    /**
     * Unregisters a socket client.
     *
     * @param client is the socket of the outgoing client
     */

    void unregister(Socket client) {
        fromClient.remove(client);
        toClient.remove(client);
        serverManager.removeClient(client);
    }

    /**
     * Starts a server according to the socket technology, keep it listening to new connection requests.
     */

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("SocketServer avviato.");
            while (keepAlive) {
                Socket client = serverSocket.accept();
                registry(client);
            }
        } catch (IOException e) {
            System.out.println("SocketServer chiuso.");
        }
    }

}