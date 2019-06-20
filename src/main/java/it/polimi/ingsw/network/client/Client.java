package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.PlayerViewTransfer;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.network.SquareViewTransfer;
import it.polimi.ingsw.utils.Parser;
import it.polimi.ingsw.view.ConcreteView;

import java.io.IOException;
import java.rmi.NotBoundException;

public class Client implements Runnable {

    private String type;
    private Ui ui;
    protected Parser parser = new Parser();
    protected ConcreteView view;

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
        if (fromServer.type == Protocol.UPDATE_PLAYER) {
            view.update(((PlayerViewTransfer)fromServer).getAttachment());
            return Protocol.ACK;
        }
        if (fromServer.type == Protocol.UPDATE_SQUARE) {
            view.update(((SquareViewTransfer)fromServer).getAttachment());
            return Protocol.ACK;
        }
        if (fromServer.type == Protocol.ARE_YOU_READY)
            view = new ConcreteView(ui);
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
