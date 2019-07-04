package it.polimi.ingsw.network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface exposes some rmi-client's methods rmi-server can call.
 */

public interface RmiClientInterface extends Remote {

    /**
     * Manages message sending and answer getting.
     *
     * @param message is the string containing message
     * @return the user's answer
     */
    String sendMessageAndGetAnswer(String message) throws RemoteException;

    /**
     * Tests client presence calling a remote method of it.
     *
     * @return always true
     */
    boolean testAliveness() throws RemoteException;
}
