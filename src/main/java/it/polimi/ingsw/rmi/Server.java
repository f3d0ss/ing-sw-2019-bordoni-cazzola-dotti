package it.polimi.ingsw.rmi;

import java.rmi.*;
import java.rmi.registry.*;

public class Server {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        System.out.println("Constructing server impl.");
        WarehouseImpl centralWarehouse = new WarehouseImpl();
        System.out.println("Binding server impl. to registry");
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("central_warehouse", centralWarehouse);
        System.out.println("Waiting for invocations from clients");
    }
}