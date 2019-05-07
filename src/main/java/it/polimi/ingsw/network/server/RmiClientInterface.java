package it.polimi.ingsw.network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiClientInterface extends Remote {
    /*dichiara qui tutti i metodi che possono essere chiamati in remoto dal server*/
    void sendMessage(String message) throws RemoteException;
}
