package it.polimi.ingsw.rmi;

import javax.naming.NamingException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws NamingException, RemoteException, NotBoundException {
        Scanner scan = new Scanner(System.in);

        Registry registry = LocateRegistry.getRegistry();
        System.out.print("RMI registry bindings: ");
        String[] e = registry.list();
        for (int i = 0; i < e.length; i++)
            System.out.println(e[i]);
        String remoteObjectName = "central_warehouse";
        RmiInterface centralRmiInterface = (RmiInterface) registry.lookup(remoteObjectName);
        System.out.println("Would you like to start a game? [Y/N]");

        String descr = scan.nextLine();
        System.out.println(centralRmiInterface.getAnswer(descr));
    }
}
