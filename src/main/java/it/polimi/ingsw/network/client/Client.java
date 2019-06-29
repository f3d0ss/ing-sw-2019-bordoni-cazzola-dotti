package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.*;
import it.polimi.ingsw.utils.Parser;
import it.polimi.ingsw.view.ConcreteView;

/**
 * This class represent a client regardless of the type of connection (Rmi or Socket)
 */

public class Client implements Runnable {

    private String type;
    private Ui ui;
    protected Parser parser = new Parser();
    protected ConcreteView view;
    protected String ip;
    protected int port;
    protected String portString;
    protected boolean keepAlive = true;

    public Client(Ui ui) {
        this.ui = ui;
    }

    public void setUi(Ui ui) {
        this.ui = ui;
    }

    public void run() {
    }

    /**
     * Recognizes the type of the message coming from server.
     *
     * @param gsonCoded is the gson-coded string that wraps the message during transfer
     * @return the answer to be sent to server
     */

    public String manageMessage(String gsonCoded) {
        Message fromServer = parser.deserialize(gsonCoded, Message.class);
        if (fromServer.type == Protocol.UPDATE_MATCH) {
            view.update(((MatchViewTransfer) fromServer).getAttachment());
            return Protocol.ACK;
        }
        if (fromServer.type == Protocol.UPDATE_PLAYER) {
            view.update(((PlayerViewTransfer) fromServer).getAttachment());
            return Protocol.ACK;
        }
        if (fromServer.type == Protocol.UPDATE_SQUARE) {
            view.update(((SquareViewTransfer) fromServer).getAttachment());
            return Protocol.ACK;
        }
        if (fromServer.type == Protocol.INITIALIZATION_DONE) {
            view.setViewInitializationDone();
            return Protocol.ACK;
        }
        if (fromServer.type == Protocol.ARE_YOU_ALIVE) {
            return Protocol.ACK;
        }
        if (fromServer.type == Protocol.SEND_COMMANDS) {
            CommandViewTransfer commandViewTransfer = (CommandViewTransfer) fromServer;
            return String.valueOf(view.sendCommands(commandViewTransfer.getAttachment(), commandViewTransfer.isUndo()));
        }
        if (fromServer.type == Protocol.ARE_YOU_READY || fromServer.type == Protocol.WELCOME_BACK)
            view = new ConcreteView(ui);
        return ui.showMessage(String.format(fromServer.type.getQuestion(), fromServer.getStringInQuestion()), fromServer.getPossibleAnswer(), fromServer.type.requiresAnswer());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Checks if a string is a valid ip.
     *
     * @param input is the string to be checked
     * @return the result of check
     */

    protected static boolean isValidIp(String input) {
        return input.matches("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    }

    /**
     * Checks if a string contains a valid port number.
     *
     * @param port is the string to be checked
     * @return the port converted to integer if the result of check is positive, -1 otherwise
     */

    protected static int isValidPort(String port) {
        int number;
        try {
            number = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            return -1;
        }
        if (number < 0 || number > 50000)
            return -1;
        return number;
    }

    /**
     * Manages when user types ip and port number, checking if they are valid values.
     */

    protected void manageIpAndPortInsertion() {
        ip = manageMessage(parser.serialize(new Message(Protocol.INSERT_IP, "", null)));
        while (!isValidIp(ip)) {
            ip = manageMessage(parser.serialize(new Message(Protocol.INSERT_IP_AGAIN, "", null)));
        }
        portString = manageMessage(parser.serialize(new Message(Protocol.INSERT_PORT, "", null)));
        port = isValidPort(portString);
        while (port < 0) {
            portString = manageMessage(parser.serialize(new Message(Protocol.INSERT_PORT_AGAIN, "", null)));
            port = isValidPort(portString);
        }
    }
}
