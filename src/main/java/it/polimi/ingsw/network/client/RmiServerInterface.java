package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.RmiClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerInterface extends Remote {
    /*dichiara qui tutti i metodi che possono essere chiamati in remoto dal client*/
    void registry(RmiClientInterface client) throws RemoteException;

    void unregistry(RmiClientInterface client) throws RemoteException;

    boolean testAliveness() throws RemoteException;
}
