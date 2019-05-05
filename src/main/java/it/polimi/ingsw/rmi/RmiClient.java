package it.polimi.ingsw.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class RmiClient implements Client {

    private boolean messageArrived = false;

    public void run() {
        try {
            startClient();
        } catch (RemoteException | NotBoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setMessageNotification(){
        messageArrived = true;
    }

    public void startClient() throws RemoteException, NotBoundException {
        Scanner stdin = new Scanner(System.in);
        Registry registry = LocateRegistry.getRegistry();
        /*System.out.print("RMI registry bindings: ");
        String[] e = registry.list();
        for (int i = 0; i < e.length; i++)
            System.out.println(e[i]);*/
        RmiInterface rmiInterface = (RmiInterface) registry.lookup("adrenaline");
        rmiInterface.
        System.out.println("Would you like to start a game? [Y/N]");
        String inputLine = stdin.nextLine();
        System.out.println(centralRmiInterface.getAnswer(inputLine));
    }
}
