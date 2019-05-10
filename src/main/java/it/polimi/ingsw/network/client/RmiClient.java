package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.RmiClientInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class RmiClient implements Client {

    private String serverName = "adrenaline";
    private RmiClientImplementation rmiClientImplementation;
    private RmiServerInterface rmiServerInterface;
    private RmiClientInterface stub;
    private Scanner stdin = new Scanner(System.in);
    int port;

    public RmiClient(int port) {
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
        String answer;
        System.out.println(message);
        answer = stdin.nextLine();
        if (answer.equals("quit")) {
            System.out.println("Disconnessione in corso.");
            closeClient();
        }
        return answer;
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
        Registry registry = LocateRegistry.getRegistry(1099);
        rmiServerInterface = (RmiServerInterface) registry.lookup(serverName);
        rmiClientImplementation = new RmiClientImplementation(this);
        stub = (RmiClientInterface) UnicastRemoteObject.exportObject(rmiClientImplementation, port);
        rmiServerInterface.registry(stub);
        while (true) {
            rmiServerInterface.testAliveness();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Impossibile raggiongere il server. Disconnessione in corso.");
    }
}
