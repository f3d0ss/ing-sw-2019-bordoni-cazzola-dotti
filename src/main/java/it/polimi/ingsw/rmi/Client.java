package it.polimi.ingsw.rmi;

public interface Client extends Runnable {
    @Override
    void run();
    boolean isMessageArrived();
    void setMessageNotification();
}
