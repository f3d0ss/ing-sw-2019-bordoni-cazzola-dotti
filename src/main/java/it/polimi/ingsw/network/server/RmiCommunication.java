package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.utils.Parser;

/**
 * This class represent a single communication between server and client according to rmi technology.
 */

public class RmiCommunication extends SingleCommunication {

    private RmiClientInterface client;
    private RmiServer rmiServer;

    public RmiCommunication(String message, RmiClientInterface client, RmiServer rmiServer, int number, ServerManager serverManager) {
        super(number, serverManager, message);
        this.client = client;
        this.rmiServer = rmiServer;
    }

    /**
     * Sends message through rmi server. If time exceeds, it sends a notification then it unregisters the client.
     */

    @Override
    public void run() {
        String answer = rmiServer.sendMessageAndGetAnswer(client, message);
        showAndSetAnswer(number, answer);
        if(timeExceeded) {
            answer = rmiServer.sendMessageAndGetAnswer(client, new Parser().serialize(new Message(Protocol.TIME_EXCEEDED, "", null)));
            showAndSetAnswer(number, answer);
            rmiServer.unregister(client);
        }
    }
}
