package it.polimi.ingsw.network.client;

import java.io.IOException;
import java.rmi.NotBoundException;

public interface Client extends Runnable {
    void startClient() throws NotBoundException, IOException;
}
