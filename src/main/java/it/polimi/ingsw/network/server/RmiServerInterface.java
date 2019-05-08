package it.polimi.ingsw.network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerInterface extends Remote {
    /*dichiara qui tutti i metodi che possono essere chiamati in remoto dal client*/
    void registry(RmiClientInterface client) throws RemoteException;

    void sendAnswer(String answer) throws RemoteException;
}
