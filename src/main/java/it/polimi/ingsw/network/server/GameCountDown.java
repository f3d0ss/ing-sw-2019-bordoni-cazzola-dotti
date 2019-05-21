package it.polimi.ingsw.network.server;

public class GameCountDown extends Thread {

    private ServerManager serverManager;
    private boolean stopped = false;
    int secondsToWait = 20;

    public GameCountDown(ServerManager serverManager){
        this.serverManager = serverManager;
    }

    public void reset(){
        stopped = true;
        secondsToWait = 0;
    }

    public int getTimeLeft(){
        return secondsToWait;
    }

    public void run(){
        stopped = false;
        while (secondsToWait > 0) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {}
            secondsToWait--;
        }
        if(!stopped)
            serverManager.startNewGame();
    }
}
