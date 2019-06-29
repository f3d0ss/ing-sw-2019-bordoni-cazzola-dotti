package it.polimi.ingsw.network.server;

/**
 * This class represent a single communication between server and client regardless the communication technology.
 */

public class SingleCommunication implements Runnable {

    protected boolean timeExceeded = false;
    protected int number;
    protected ServerManager serverManager;
    protected String message;

    public SingleCommunication(int number, ServerManager serverManager, String message) {
        this.number = number;
        this.serverManager = serverManager;
        this.message = message;
    }

    public void run(){
    }

    /**
     * Sets time exceed and print advise on server log.
     */

    public void setTimeExceeded(){
        timeExceeded = true;
        System.out.println("User " + number + ": TEMPO SCADUTO");
    }

    /**
     * Sets client's answer and prints it on server log.
     */

    protected void showAndSetAnswer(int number, String answer){
        serverManager.setAnswer(number, answer);
        System.out.println("User " + number + ": " + answer);
    }
}
