package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.utils.Parser;

import java.net.Socket;

public class SocketCommunication extends SingleCommunication {

    private Socket client;
    private SocketServer socketServer;

    public SocketCommunication(String message, Socket client, SocketServer socketServer, int number, ServerManager serverManager) {
        super(number, serverManager, message);
        this.client = client;
        this.socketServer = socketServer;
    }

    @Override
    public void run() {
        String answer = socketServer.sendMessageAndGetAnswer(client, message);
        serverManager.setAnswer(number, answer);
        System.out.println("User " + number + ": " + answer);
        if(timeExceeded) {
            answer = socketServer.sendMessageAndGetAnswer(client, new Parser().serialize(new Message(Protocol.TIME_EXCEEDED, "", null, 0)));
            serverManager.setAnswer(number, answer);
            System.out.println("User " + number + ": " + answer);
            socketServer.unregistry(client);
        }
    }
}