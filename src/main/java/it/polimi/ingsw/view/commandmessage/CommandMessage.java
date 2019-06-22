package it.polimi.ingsw.view.commandmessage;

import java.io.Serializable;

public abstract class CommandMessage implements Serializable {
    private CommandType type;

    public CommandMessage(CommandType type) {
        this.type = type;
    }

    public CommandType getType() {
        return type;
    }
}
