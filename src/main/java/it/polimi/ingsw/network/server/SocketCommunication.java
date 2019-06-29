package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.utils.Parser;

import java.net.Socket;

/**
 * This class represent a single communication between server and client according to socket technology.
 */

public class SocketCommunication extends SingleCommunication {

    private Socket client;
    private SocketServer socketServer;

    public SocketCommunication(String message, Socket client, SocketServer socketServer, int number, ServerManager serverManager) {
        super(number, serverManager, message);
        this.client = client;
        this.socketServer = socketServer;
    }

    /**
     * Sends message through socket server. If time exceeds, it sends a notification then it unregisters the client.
     */

    @Override
    public void run() {
        String answer = socketServer.sendMessageAndGetAnswer(client, message);
        showAndSetAnswer(number, answer);
        if(timeExceeded) {
            answer = socketServer.sendMessageAndGetAnswer(client, new Parser().serialize(new Message(Protocol.TIME_EXCEEDED, "", null)));
            showAndSetAnswer(number, answer);
            socketServer.unregister(client);
        }
    }
}