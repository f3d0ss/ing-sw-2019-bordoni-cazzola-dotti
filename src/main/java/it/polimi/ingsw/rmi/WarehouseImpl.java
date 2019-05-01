package it.polimi.ingsw.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class WarehouseImpl extends UnicastRemoteObject implements RmiInterface {

    public WarehouseImpl() throws RemoteException {}

    public String getAnswer(String command) throws RemoteException {
        if(command.equals("Y"))
            return "Gooo!";
        return "It has been a plaesure!";
    }
}
