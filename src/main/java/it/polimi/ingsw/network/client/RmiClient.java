package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.network.server.RmiClientInterface;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import static java.lang.Thread.sleep;

/**
 * This class represent a client using rmi connection and
 * implements the Rmi-client interface exposed to rmi server.
 */

public class RmiClient extends Client implements RmiClientInterface {

    private static final int TEST_ALIVENESS_TIME = 2000;
    private static final String TYPE = "RMI";

    public RmiClient(Ui ui) {
        super(ui);
    }

    /**
     * Starts a client according to the rmi communication and
     * calls periodically a server's method in order to check connection.
     * When a problem occurs, it closes the process after having notified the disconnection.
     */

    @Override
    public void run() {
        RmiServerInterface rmiServerInterface;
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
        while (keepAlive) {
            try {
                sleep(TEST_ALIVENESS_TIME);
                rmiServerInterface.testAliveness();
            } catch (InterruptedException | RemoteException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        manageMessage(parser.serialize(new Message(Protocol.UNREACHABLE_SERVER, "", null)));
        try {
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException e) {
            System.out.println("Impossibile chiudere l'interfaccia rmi: " + e.getMessage());
        }
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
}
