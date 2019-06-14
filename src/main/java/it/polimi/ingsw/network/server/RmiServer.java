package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.RmiServerInterface;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer implements Runnable {

    private RmiServerImplementation server;
    private ServerManager serverManager;
    private final static int STUB_PORT = 39000;
    private final static int REGISTRY_PORT = 1099;

    public RmiServer(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    public void run() {
        try {
            startServer();
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void registry(RmiClientInterface client) {
        serverManager.addClient(client);
        int number = serverManager.getNumber(client);
        System.out.println("User " + number + " accettato sul RmiServer.");
        new Thread(new ClientReception(serverManager, number)).start();
    }

    public void unregistry(RmiClientInterface client) {
        serverManager.removeClient(client);
    }

    public RmiServerImplementation getImplementation() {
        return server;
    }

    public void startServer() throws RemoteException, AlreadyBoundException {
        try {
            System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
        }
        server = new RmiServerImplementation(this);
        RmiServerInterface stub = (RmiServerInterface) UnicastRemoteObject.exportObject(server, STUB_PORT);
        LocateRegistry.createRegistry(REGISTRY_PORT);
        Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
        registry.bind(RmiServerInterface.NAME, stub);
        System.out.println("RmiServer avviato.");
        serverManager.setRmiReady();
    }
}