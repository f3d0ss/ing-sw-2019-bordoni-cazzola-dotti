package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.RmiClientInterface;

import java.rmi.*;

public interface RmiServerInterface extends Remote {
    /*dichiara qui tutti i metodi che possono essere chiamati in remoto dal client*/
    void registry(RmiClientInterface client) throws RemoteException;
    void unregistry(RmiClientInterface client) throws RemoteException;
    void testAliveness() throws RemoteException;
}
