package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.RmiClientInterface;

import java.rmi.RemoteException;

public class RmiClientImplementation implements RmiClientInterface {

    private RmiClient client;

    public RmiClientImplementation(RmiClient client) {
        this.client = client;
    }

    public void sendMessage(String message) throws RemoteException {
        client.setMessage(message);
    }
}
