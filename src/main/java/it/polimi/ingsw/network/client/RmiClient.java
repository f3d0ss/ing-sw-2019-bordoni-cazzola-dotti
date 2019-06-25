package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.network.server.RmiClientInterface;

import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static java.lang.Thread.sleep;

public class RmiClient extends Client {

    private RmiClientImplementation rmiClientImplementation;
    private RmiServerInterface rmiServerInterface;
    private RmiClientInterface stub;
    private final static int TEST_ALIVENESS_TIME = 2000;
    private boolean keepAlive = true;
    private final static String TYPE = "RMI";

    public RmiClient(String ip, int port, Ui ui) {
        super(ui);
        this.ip = ip;
        this.port = port;
    }

    public void run() {
        try {
            startClient();
        } catch (RemoteException | NotBoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public String printMessageAndGetAnswer(String message) {
        return manageMessage(message);
    }

    //TODO: can a client be closed while running?
    private void closeClient() {
        try {
            rmiServerInterface.unregistry(stub);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    public void startClient() throws RemoteException, NotBoundException {
        while(true) {
            manageMessage(parser.serialize(new Message(Protocol.CONNECTING, TYPE, null)));
            try {
                Registry registry = LocateRegistry.getRegistry(ip, port);
                rmiServerInterface = (RmiServerInterface) registry.lookup(RmiServerInterface.NAME);
                rmiClientImplementation = new RmiClientImplementation(this);
                stub = (RmiClientInterface) UnicastRemoteObject.exportObject(rmiClientImplementation, 0);
                rmiServerInterface.registry(stub);
                break;
            } catch (ConnectException | ConnectIOException e) {
                //System.out.println(e.getMessage());
                do {
                    manageInvalidIpOrPort();
                }while(!isValidIp(ip) || port < 0);
            }
        }
        while (keepAlive) {
            try {
                sleep(TEST_ALIVENESS_TIME);
            } catch (InterruptedException e) {
                break;
            }
            try {
                rmiServerInterface.testAliveness();
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        manageMessage(parser.serialize(new Message(Protocol.UNREACHABLE_SERVER, "", null)));
        System.exit(0);
    }
}
