package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.RmiClientInterface;

public class RmiClientImplementation implements RmiClientInterface {

    private RmiClient client;

    public RmiClientImplementation(RmiClient client) {
        this.client = client;
    }

    public String sendMessageAndGetAnswer(String message) {
        return client.printMessageAndGetAnswer(message);
    }
}
