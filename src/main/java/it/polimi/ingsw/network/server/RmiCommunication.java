package it.polimi.ingsw.network.server;

public class RmiCommunication implements Runnable {
    private String message;
    private RmiClientInterface client;
    private RmiServer rmiServer;
    private int number;
    private ServerManager serverManager;

    public RmiCommunication(String message, RmiClientInterface client, RmiServer rmiServer, int number, ServerManager serverManager) {
        this.message = message;
        this.client = client;
        this.rmiServer = rmiServer;
        this.number = number;
        this.serverManager = serverManager;
    }

    @Override
    public void run() {
        String answer = rmiServer.getImplementation().sendMessageAndGetAnswer(client, message);
        System.out.println("User " + number + ": " + answer);
        if (answer.equals("quit"))
            serverManager.removeClient(client);
    }
}
