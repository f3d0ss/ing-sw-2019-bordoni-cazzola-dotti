package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.RmiClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface exposes some rmi-server's methods rmi-client can call.
 */

public interface RmiServerInterface extends Remote {

    String NAME = "adrenaline";

    /**
     * Registries a new rmi client on the server manager,
     * starting a thread devoted to look after the enrollment.
     *
     * @param client is the rmi interface of the new client
     */
    void registry(RmiClientInterface client) throws RemoteException;

    /**
     * Tests server presence calling a remote method of it.
     *
     * @return always true
     */
    boolean testAliveness() throws RemoteException;
}
