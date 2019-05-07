package it.polimi.ingsw.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static java.lang.Thread.sleep;

public class SocketServer implements Runnable {

    private int port = 9000;
    private ServerManager serverManager;
    private ServerSocket serverSocket;
    private String message;
    private String answer;
    private boolean questionSend = true;
    private boolean answerReceived = false;
    //private boolean clientReady = false;
    private boolean keepAlive = true;
    private List<Socket> clients = new ArrayList<>();
    private Map<Socket, Boolean> clientReady = new HashMap<>();
    private Map<Socket, Scanner> fromClient = new HashMap<>();
    private Map<Socket, PrintWriter> toClient = new HashMap<>();
    private Socket currentAddressee;

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

    public void sendMessage(Socket addressee, String message) {
        System.out.println("SendMessage invoked");
        setAddressee(addressee);
        this.message = message;
        /*this.message = message;
        answerReceived = false;
        questionSend = false;*/
    }

    public String getAnswer() {
        return answer;
    }

    public void setAddressee(Socket addressee) {
        currentAddressee = addressee;
    }

    public boolean isAnswerReady() {
        return answerReceived;
    }

    public void stopServer() {
        keepAlive = false;
    }

    public synchronized void registry(Socket client) throws IOException {
        serverManager.addClient(client);
        fromClient.put(client, new Scanner(client.getInputStream()));
        toClient.put(client, new PrintWriter(client.getOutputStream(), true));
        clientReady.put(client, false);
        System.out.println("User accettato sul SocketServer");
        serverManager.bidWelcome(client);
    }

    public void startServer() throws IOException {
        serverSocket = new ServerSocket(port);
        new Thread(new SocketReceptionist(serverSocket, this)).start();

        while (keepAlive) {
            while (questionSend) {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                }
            }
            toClient.get(currentAddressee).println(message);
            questionSend = true;
            while (answerReceived) {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                }
            }
            answer = fromClient.get(currentAddressee).nextLine();
            answerReceived = true;
        }
        /*fromClient.close();
        toClient.close();
        socket.close();
        server.close();
        System.out.println("Chiuso");*/
    }

}