package it.polimi.ingsw.network.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RmiServerImplementation implements RmiServerInterface {

    private List<RmiClientInterface> clients = new ArrayList<>();
    private RmiServer server;

    public RmiServerImplementation(RmiServer server) {
        this.server = server;
    }

    public synchronized void registry(RmiClientInterface client) {
        if (!clients.contains(client)) {
            clients.add(client);
            server.registry(client);
            System.out.println("New client registered.");
        }
    }

    public synchronized void sendAnswer(String answer) {
        server.receiveAnswer(answer);
    }

    public synchronized void sendMessage(RmiClientInterface addressee, String message) {
        try {
            addressee.sendMessage(message);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }
}
