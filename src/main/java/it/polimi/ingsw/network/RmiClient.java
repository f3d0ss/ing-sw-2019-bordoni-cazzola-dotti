package it.polimi.ingsw.network;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiClient implements Client {

    private RmiClientImplementation rmiClientImplementation;
    private RmiServerInterface rmiServerInterface;
    private boolean messageArrived = false;
    private boolean answerSet = false;
    private boolean keepAlive = true;
    private String messageFromServer;
    private String answer;
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

    public boolean isMessageArrived() {
        return messageArrived;
    }

    public void setMessageNotification() {
        messageArrived = true;
    }

    public String getMessageFromServer(){
        messageArrived = false;
        return messageFromServer;
    }

    public void sendAnswerToServer(String answer) {
        try {
            rmiServerInterface.sendAnswer(answer);
        }catch (RemoteException e){
            System.out.println(e.getMessage());
        }
    }

    public void setMessage(String message) {
        this.messageFromServer = message;
        answerSet = false;
        messageArrived = true;
    }

    public void startClient() throws RemoteException, NotBoundException {
        System.out.println("Cerco Server");
        Registry registry = LocateRegistry.getRegistry(1099);
        rmiServerInterface = (RmiServerInterface) registry.lookup("adrenaline");
        rmiClientImplementation = new RmiClientImplementation(this);
        RmiClientInterface stub = (RmiClientInterface) UnicastRemoteObject.exportObject(rmiClientImplementation, port);
        System.out.println("Client in registrazione");
        rmiServerInterface.registry(stub);
        System.out.println("Client registrato");
    }
}
