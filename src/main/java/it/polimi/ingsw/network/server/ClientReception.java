package it.polimi.ingsw.network.server;

/**
 * This class allows to manage parallel client enrollments.
 */

class ClientReception implements Runnable {

    private final ServerManager serverManager;
    private final int number;

    ClientReception(ServerManager serverManager, int number) {
        this.serverManager = serverManager;
        this.number = number;
    }

    /**
     * Starts a client enrollment.
     */
    @Override
    public void run() {
        serverManager.addClientToLog(number);
    }
}
