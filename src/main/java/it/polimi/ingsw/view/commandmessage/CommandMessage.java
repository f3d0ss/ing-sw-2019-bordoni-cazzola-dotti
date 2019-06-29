package it.polimi.ingsw.view.commandmessage;

public abstract class CommandMessage {
    private final CommandType type;
    private String jsonType = getClass().getSimpleName();

    CommandMessage(CommandType type) {
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
