package it.polimi.ingsw.view.commandmessage;

import java.io.Serializable;

public abstract class CommandMessage implements Serializable {
    private CommandType type;
    private String jsonType = getClass().getSimpleName();

    public CommandMessage(CommandType type) {
        this.type = type;
    }

    public CommandType getType() {
        return type;
    }
    /**
     * This method must be called before serialize this object
     */
    public void preSerialization() {
        jsonType = getClass().getSimpleName();
    }
}
