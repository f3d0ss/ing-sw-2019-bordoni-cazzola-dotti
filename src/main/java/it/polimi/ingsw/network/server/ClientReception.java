package it.polimi.ingsw.network.server;

/**
 * This class allows to manage parallel client enrollments.
 */

public class ClientReception implements Runnable {

    private ServerManager serverManager;
    private int number;

    public ClientReception(ServerManager serverManager, int number) {
        this.serverManager = serverManager;
        this.number = number;
    }

    @Override
    public void run() {
        serverManager.addClientToLog(number);
    }
}
