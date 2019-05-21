package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.server.RmiClientInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class RmiClient implements Client {

    int port;
    private String ip;
    private String serverName = "adrenaline";
    private RmiClientImplementation rmiClientImplementation;
    private RmiServerInterface rmiServerInterface;
    private RmiClientInterface stub;
    private Scanner stdin = new Scanner(System.in);

    public RmiClient(String ip, int port) {
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
        String answer;
        Gson gson = new Gson();
        Message fromServer = gson.fromJson(message, Message.class);
        System.out.println(fromServer.question);
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
        Registry registry = LocateRegistry.getRegistry(ip, 1099);
        rmiServerInterface = (RmiServerInterface) registry.lookup(serverName);
        rmiClientImplementation = new RmiClientImplementation(this);
        stub = (RmiClientInterface) UnicastRemoteObject.exportObject(rmiClientImplementation, port);
        rmiServerInterface.registry(stub);
        System.out.println("Connessione stabilita. Digitare quit per uscire");
        while (true) {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                break;
            }
            try {
                rmiServerInterface.testAliveness();
            } catch (RemoteException e){
                System.out.println(e.getMessage());
                break;
            }
        }
        System.out.println("Impossibile raggiungere il server. Disconnessione in corso.");
        System.exit(-1);
    }
}
