package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.network.server.RmiClientInterface;
import it.polimi.ingsw.utils.Parser;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiClient extends Client {

    int port;
    private String ip;
    private String serverName = "adrenaline";
    private RmiClientImplementation rmiClientImplementation;
    private RmiServerInterface rmiServerInterface;
    private RmiClientInterface stub;
    private Parser parser = new Parser();

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
        if (message == Protocol.ping)
            return Protocol.ack;
        return manageMessage(message);
        /*if (answer.equals("quit")) {
            System.out.println("Disconnessione in corso.");
            closeClient();
        }
        return answer;*/
    }

    private void closeClient() {
        try {
            rmiServerInterface.unregistry(stub);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
        return;
    }

    public void startClient() throws RemoteException, NotBoundException {
        System.out.println("Connessione al RMI server in corso...");
        try {
            Registry registry = LocateRegistry.getRegistry(ip, 1099);
            rmiServerInterface = (RmiServerInterface) registry.lookup(serverName);
            rmiClientImplementation = new RmiClientImplementation(this);
            stub = (RmiClientInterface) UnicastRemoteObject.exportObject(rmiClientImplementation, port);
            rmiServerInterface.registry(stub);
            System.out.println("Connessione stabilita.");
        } catch (ConnectException e) {
            System.out.println(e.getMessage());
            ip = manageMessage(parser.serialize(new Message(Protocol.INSERT_IP_AGAIN, "", null, 0)));
            run();
        }
        /*
        while (true) {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                break;
            }
            try {
                rmiServerInterface.testAliveness();
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
                break;
            }
        }*/
        System.out.println("Impossibile raggiungere il server. Disconnessione in corso.");
        System.exit(-1);
    }
}
