package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.utils.Parser;

public class RmiCommunication extends SingleCommunication {

    private RmiClientInterface client;
    private RmiServer rmiServer;

    public RmiCommunication(String message, RmiClientInterface client, RmiServer rmiServer, int number, ServerManager serverManager) {
        super(number, serverManager, message);
        this.client = client;
        this.rmiServer = rmiServer;
    }

    @Override
    public void run() {
        String answer = rmiServer.getImplementation().sendMessageAndGetAnswer(client, message);
        serverManager.setAnswer(number, answer);
        System.out.println("User " + number + ": " + answer);//it shows answer on server log
        if(timeExceeded) {
            answer = rmiServer.getImplementation().sendMessageAndGetAnswer(client, new Parser().serialize(new Message(Protocol.TIME_EXCEEDED, "", null, 0)));
            serverManager.setAnswer(number, answer);
            System.out.println("User " + number + ": " + answer);
            rmiServer.unregistry(client);
        }
    }
}
