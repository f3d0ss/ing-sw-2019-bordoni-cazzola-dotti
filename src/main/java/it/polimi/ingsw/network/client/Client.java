package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.Message;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.List;

public class Client implements Runnable {

    private String ip;
    private String type;
    private Ui ui;

    public Client(Ui ui) {
        this.ui = ui;
    }

    public void setUi(Ui ui){
        this.ui = ui;
    }

    public void run() {
    }

    public void startClient() throws NotBoundException, IOException {
    }

    public String manageMessage(String gsonCoded) {
        Gson gson = new Gson();
        Message fromServer = gson.fromJson(gsonCoded, Message.class);
        String mainMessage = fromServer.type.getQuestion();
        String subMessage = fromServer.getStringInQuestion();
        String completeMessage = String.format(mainMessage, subMessage);
        List<String> possibleAnswers = fromServer.getPossibleAnswer();
        return ui.showMessage(completeMessage, possibleAnswers);
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
