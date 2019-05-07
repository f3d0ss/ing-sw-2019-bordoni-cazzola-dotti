package it.polimi.ingsw.network;

public interface Client extends Runnable {
    boolean isMessageArrived();
    String getMessageFromServer();
    void sendAnswerToServer(String answer);
    void setMessageNotification();
}
