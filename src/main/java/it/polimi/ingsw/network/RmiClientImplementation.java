package it.polimi.ingsw.network;

import java.rmi.RemoteException;

public class RmiClientImplementation implements RmiClientInterface {

    private RmiClient client;

    public RmiClientImplementation(RmiClient client){
        this.client = client;
    }

    public void sendMessage(String message) throws RemoteException {
        client.setMessage(message);
    }
}
