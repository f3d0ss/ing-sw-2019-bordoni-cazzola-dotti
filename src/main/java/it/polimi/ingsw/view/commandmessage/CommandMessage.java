package it.polimi.ingsw.view.commandmessage;

public abstract class CommandMessage {
    private CommandType type;
    private String jsonType = getClass().getSimpleName();

    public CommandMessage(CommandType type) {
        this.type = type;
    }

    /**
     * This method must be called before serialize this object
     */
    public void preSerialization() {
        jsonType = getClass().getSimpleName();
    }
}
