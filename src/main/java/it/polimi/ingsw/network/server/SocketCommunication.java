package it.polimi.ingsw.network.server;

import java.net.Socket;

public class SocketCommunication implements Runnable {

    private String message;
    private Socket client;
    private SocketServer socketServer;
    private int number;
    private ServerManager serverManager;

    public SocketCommunication(String message, Socket client, SocketServer socketServer, int number, ServerManager serverManager) {
        this.message = message;
        this.client = client;
        this.socketServer = socketServer;
        this.number = number;
        this.serverManager = serverManager;
    }

    @Override
    public void run() {
        String answer = socketServer.sendMessageAndGetAnswer(client, message);
        serverManager.setAnswer(number, answer);
        System.out.println("User " + number + ": " + answer);
        if (answer.equals("quit"))
            serverManager.removeClient(client);
    }
}
