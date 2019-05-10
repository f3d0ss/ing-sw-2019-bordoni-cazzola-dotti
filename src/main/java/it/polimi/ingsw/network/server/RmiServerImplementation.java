package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.RmiServerInterface;

import java.rmi.RemoteException;

public class RmiServerImplementation implements RmiServerInterface {

    private RmiServer server;

    public RmiServerImplementation(RmiServer server) {
        this.server = server;
    }

    public synchronized void registry(RmiClientInterface client) {
        server.registry(client);
        System.out.println("Client registrato.");
    }

    public synchronized void testAliveness() {
        server.getImplementation();
    }

    public synchronized void unregistry(RmiClientInterface client) {
        server.unregistry(client);
        System.out.println("Client rimosso.");
    }

    public String sendMessageAndGetAnswer(RmiClientInterface addressee, String message) {
        try {
            return addressee.sendMessageAndGetAnswer(message);
        } catch (RemoteException e) {
            System.out.println("Impossibile raggiungere il client. " + e.getMessage());
            server.unregistry(addressee);
        }
        return "Answer missing";
    }
}
