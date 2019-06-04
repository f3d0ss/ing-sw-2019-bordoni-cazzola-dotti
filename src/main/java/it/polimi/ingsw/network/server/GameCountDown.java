package it.polimi.ingsw.network.server;

public class GameCountDown extends Thread {

    private int secondsToWait;
    private int seconds;
    private ServerManager serverManager;
    private boolean stopped = false;

    public GameCountDown(ServerManager serverManager, int secondsToWait) {
        this.serverManager = serverManager;
        this.secondsToWait = secondsToWait;
    }

    public void reset() {
        stopped = true;
        seconds = 0;
    }

    public void restore() {
        stopped = false;
        seconds = secondsToWait;
    }

    public int getTimeLeft() {
        return seconds;
    }

    public void run() {
        while (seconds > 0) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
            }
            seconds--;
            System.out.println(seconds);
        }
        if (!stopped)
            serverManager.checkAllConnections();
    }
}
