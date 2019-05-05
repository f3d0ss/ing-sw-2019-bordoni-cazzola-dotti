package it.polimi.ingsw.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiHandler extends UnicastRemoteObject implements RmiInterface {

    public RmiHandler() throws RemoteException {}

    public String getAnswer(String command) throws RemoteException {
        if(command.equals("Y"))
            return "Gooo!";
        return "It has been a plaesure!";
    }
}