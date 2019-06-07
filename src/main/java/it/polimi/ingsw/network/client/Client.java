package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.utils.Parser;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.List;

public class Client implements Runnable {

    private String type;
    private Ui ui;

    public Client(Ui ui) {
        this.ui = ui;
    }

    public void setUi(Ui ui) {
        this.ui = ui;
    }

    public void run() {
    }

    public void startClient() throws NotBoundException, IOException {
    }

    public String manageMessage(String gsonCoded) {
        Parser parser = new Parser();
        Message fromServer = parser.deserialize(gsonCoded, Message.class);
        String mainMessage = fromServer.type.getQuestion();
        String subMessage = fromServer.getStringInQuestion();
        String completeMessage = String.format(mainMessage, subMessage);
        List<String> possibleAnswers = fromServer.getPossibleAnswer();
        boolean isAnswerRequired = fromServer.type.requiresAnswer();
        return ui.showMessage(completeMessage, possibleAnswers, isAnswerRequired);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
