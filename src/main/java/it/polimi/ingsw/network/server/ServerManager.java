package it.polimi.ingsw.network.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerManager implements Runnable {

    SocketServer socketServer;
    RmiServer rmiServer;
    private List<Socket> socketClients = new ArrayList<>();
    private List<RmiClientInterface> rmiClients = new ArrayList<>();

    public void addClient(Socket client) {
        socketClients.add(client);
    }

    public void addClient(RmiClientInterface client) {
        rmiClients.add(client);
    }

    public void bidWelcome(Socket client) {
        if (socketClients.contains(client))
            socketServer.sendMessage(client, "Benvenuto");
    }

    public void bidWelcome(RmiClientInterface client) {
        System.out.println("BidWelcome invoked");
        if (rmiClients.contains(client))
            rmiServer.getImplementation().sendMessage(client, "Benvenuto");
    }

    public void receiveAnswer(String answer) {
        System.out.println(answer);
    }

    @Override
    public void run() {
        socketServer = new SocketServer(this);
        rmiServer = new RmiServer(this);
        Scanner stdin = new Scanner(System.in);
        String message;
        String answer;
        new Thread(socketServer).start();
        System.out.printf("SocketServer avviato. ");
        new Thread(rmiServer).start();
        System.out.println("RmiServer avviato.");
    }
}
