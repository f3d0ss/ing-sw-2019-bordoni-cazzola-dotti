package it.polimi.ingsw.rmi;

import java.rmi.*;

public interface RmiInterface extends Remote {
    String getAnswer(String command) throws RemoteException;
}
