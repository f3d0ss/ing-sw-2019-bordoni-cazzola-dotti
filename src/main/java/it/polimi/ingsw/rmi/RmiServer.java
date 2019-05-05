package it.polimi.ingsw.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer implements Runnable {

    public void run() {
        try {
            startServer();
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void startServer() throws RemoteException, AlreadyBoundException {
        RmiHandler handler = new RmiHandler();
        java.rmi.registry.LocateRegistry.createRegistry(1099);
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("adrenaline", handler);
    }
}