package it.polimi.ingsw.view.commandmessage;

/**
 * A command message is a subset of the command that contains only the necessary information for its display to the clients
 */
public abstract class CommandMessage {

    private final CommandType type;
    @SuppressWarnings("squid:S1068")
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
