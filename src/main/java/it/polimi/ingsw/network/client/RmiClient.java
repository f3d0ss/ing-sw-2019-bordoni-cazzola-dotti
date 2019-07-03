package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.network.server.RmiClientInterface;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class represent a client using rmi connection and
 * implements the Rmi-client interface exposed to rmi server.
 */

public class RmiClient extends Client implements RmiClientInterface {

    private static final String TYPE = "RMI";
    private RmiServerInterface rmiServerInterface;

    RmiClient(Ui ui) {
        super(ui);
    }

    /**
     * Starts a client according to the rmi technology.
     */

    @Override
    public void run() {
        manageIpAndPortInsertion();
        while (true) {
            manageMessage(parser.serialize(new Message(Protocol.CONNECTING, TYPE, null)));
            try {
                rmiServerInterface = (RmiServerInterface) LocateRegistry.getRegistry(ip, port).lookup(RmiServerInterface.NAME);
                rmiServerInterface.registry((RmiClientInterface) UnicastRemoteObject.exportObject(this, 0));
                break;
            } catch (RemoteException | NotBoundException e) {
                manageMessage(parser.serialize(new Message(Protocol.INVALID_CONNECTION_PARAMETERS, "", null)));
                manageIpAndPortInsertion();
            }
        }
        clientReady = true;
    }

    /**
     * Delegates messages management to superclass Client.
     *
     * @param message is the gson-coded string containing message
     * @return the user's answer
     */

    public String sendMessageAndGetAnswer(String message) {
        return manageMessage(message);
    }

    /**
     * This method is called by server in order to test client presence.
     *
     * @return always true
     */

    public boolean testAliveness() {
        return true;
    }

    /**
     * Detects if the rmi server is reachable.
     *
     * @return true if is reachable, false otherwise
     */

    @Override
    boolean isServerReachable() {
        try {
            rmiServerInterface.testAliveness();
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

    /**
     * Closes the rmi client.
     */

    @Override
    public void stop() {
        try {
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException e) {
            System.out.println("Impossibile chiudere l'interfaccia rmi: " + e.getMessage());
        }
    }
}
