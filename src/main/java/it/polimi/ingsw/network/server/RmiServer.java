package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.RmiServerInterface;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class represent the server for communications using rmi,
 * implementing the interface exposed to rmi client.
 */

public class RmiServer implements Runnable, RmiServerInterface {

    private ServerManager serverManager;
    private int port;

    public RmiServer(ServerManager serverManager, int port) {
        this.serverManager = serverManager;
        this.port = port;
    }

    /**
     * Registries a new rmi client on the server manager,
     * starting a thread devoted to look after the enrollment.
     *
     * @param client is the rmi interface of the new client
     */

    public void registry(RmiClientInterface client) {
        serverManager.addClient(client);
        int number = serverManager.getNumber(client);
        System.out.println("User " + number + " accettato sul RmiServer.");
        new Thread(new ClientReception(serverManager, number)).start();
    }

    /**
     * Unregisters a rmi client.
     *
     * @param client is the interface of the outgoing client
     */

    public void unregister(RmiClientInterface client) {
        serverManager.removeClient(client);
    }

    /**
     * This method is called by client in order to test server presence.
     *
     * @return always true
     */

    public synchronized boolean testAliveness() {
        return true;
    }

    /**
     * Sends a message to a rmi client. If the client is unreachable, it unregisters it.
     *
     * @param addressee is the rmi interface of the addressee
     * @param message is the string containing the sending message
     * @return is the answer coming from the client
     */

    public String sendMessageAndGetAnswer(RmiClientInterface addressee, String message) {
        try {
            return addressee.sendMessageAndGetAnswer(message);
        } catch (RemoteException e) {
            unregister(addressee);
            return "Impossibile raggiungere il client. " + e.getMessage();
        }
    }

    /**
     * Starts a server according to the rmi technology.
     */

    public void run() {
        try {
            System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
            RmiServerInterface stub = (RmiServerInterface) UnicastRemoteObject.exportObject(this, 0);
            LocateRegistry.createRegistry(port);
            LocateRegistry.getRegistry(port).bind(RmiServerInterface.NAME, stub);
            System.out.println("RmiServer avviato.");
        } catch (UnknownHostException | RemoteException | AlreadyBoundException e) {
            System.out.println(e.getMessage());
        }
    }
}