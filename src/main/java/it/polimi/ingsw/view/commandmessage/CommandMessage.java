package it.polimi.ingsw.view.commandmessage;

public abstract class CommandMessage {
    private CommandType type;

    public CommandMessage(CommandType type) {
        this.type = type;
    }
}
