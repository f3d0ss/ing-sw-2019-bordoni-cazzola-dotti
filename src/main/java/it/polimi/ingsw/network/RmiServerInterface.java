package it.polimi.ingsw.network;

import java.rmi.*;

public interface RmiServerInterface extends Remote {
    /*dichiara qui tutti i metodi che possono essere chiamati in remoto dal client*/
    void registry(RmiClientInterface client) throws RemoteException;
    void sendAnswer(String answer) throws RemoteException;
}
