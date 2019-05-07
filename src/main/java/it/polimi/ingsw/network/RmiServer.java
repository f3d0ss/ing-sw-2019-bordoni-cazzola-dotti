package it.polimi.ingsw.network;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer implements Runnable {

    private ServerManager serverManager;
    RmiServerImplementation server;

    public void run() {
        try {
            startServer();
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public RmiServer(ServerManager serverManager){
        this.serverManager = serverManager;
    }

    public void registry(RmiClientInterface client){
        serverManager.addClient(client);
        serverManager.bidWelcome(client);
    }

    public void receiveAnswer(String answer){
        serverManager.receiveAnswer(answer);
    }

    public RmiServerImplementation getImplementation(){
        return server;
    }

    public void startServer() throws RemoteException, AlreadyBoundException {
        server = new RmiServerImplementation(this);
        RmiServerInterface stub = (RmiServerInterface) UnicastRemoteObject.exportObject(server, 39000);
        LocateRegistry.createRegistry(1099);
        Registry registry = LocateRegistry.getRegistry(1099);
        registry.bind("adrenaline", stub);
        System.out.println("Server ready");
    }
}