package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.utils.Parser;

import java.io.IOException;
import java.rmi.NotBoundException;

public class Client implements Runnable {

    private String type;
    private Ui ui;
    protected Parser parser = new Parser();

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
        Message fromServer = parser.deserialize(gsonCoded, Message.class);
        return ui.showMessage(String.format(fromServer.type.getQuestion(), fromServer.getStringInQuestion()), fromServer.getPossibleAnswer(), fromServer.type.requiresAnswer());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static boolean isValidIp(String input) {
        return input.matches("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    }
}
