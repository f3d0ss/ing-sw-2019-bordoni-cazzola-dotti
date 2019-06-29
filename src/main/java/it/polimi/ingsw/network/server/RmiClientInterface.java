package it.polimi.ingsw.network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface exposes some rmi-client's methods rmi-server can call.
 */

public interface RmiClientInterface extends Remote {
    String sendMessageAndGetAnswer(String message) throws RemoteException;

    boolean testAliveness() throws RemoteException;
}
