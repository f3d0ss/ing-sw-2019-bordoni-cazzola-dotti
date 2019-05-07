package it.polimi.ingsw.network.client;

public interface Client extends Runnable {
    boolean isMessageArrived();

    String getMessageFromServer();

    void sendAnswerToServer(String answer);

    void setMessageNotification();
}
