package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.RmiClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface exposes some rmi-server's methods rmi-client can call.
 */

public interface RmiServerInterface extends Remote {

    String NAME = "adrenaline";

    void registry(RmiClientInterface client) throws RemoteException;

    boolean testAliveness() throws RemoteException;
}
