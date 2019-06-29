package it.polimi.ingsw.network.server;

/**
 * This class manage the countdown after the minimum players number is reached.
 * Game starts when countdown reach 0 or the maximum players number is reached.
 */

class GameCountDown extends Thread {

    private static final int MILLIS_IN_SECOND = 1000;
    private final int secondsToWait;
    private final ServerManager serverManager;
    private int seconds;
    private boolean stopped = false;

    GameCountDown(ServerManager serverManager, int secondsToWait) {
        this.serverManager = serverManager;
        this.secondsToWait = secondsToWait;
        seconds = secondsToWait;
    }

    /**
     * Sets countdown as stopped and restore the seconds-left value.
     */

    void stopCount() {
        stopped = true;
        seconds = secondsToWait;
    }

    /**
     * Restores countdown without sets it as stopped.
     */

    void restore() {
        stopped = false;
        seconds = secondsToWait;
    }

    int getTimeLeft() {
        return seconds;
    }

    /**
     * @return true if countdown is still running
     */

    boolean isRunning() {
        return seconds < secondsToWait;
    }

    /**
     * Starts countdown and lets it running until it reaches 0 or it is stopped.
     * If it is stopped, it means that maximum players number is reached, then game can start.
     */

    @Override
    public void run() {
        while (seconds > 0 && !stopped) {
            seconds--;
            System.out.println(seconds);
            try {
                sleep(MILLIS_IN_SECOND);
            } catch (InterruptedException e) {
                break;
            }
        }
        if (!stopped)
            serverManager.checkAllConnections();
        stopped = false;
    }
}
