package it.polimi.ingsw.network.server;

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

    public void setTimeExceeded(){
        timeExceeded = true;
        System.out.println("User " + number + ": TEMPO SCADUTO");
    }
}
