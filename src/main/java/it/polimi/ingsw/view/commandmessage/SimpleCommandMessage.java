package it.polimi.ingsw.view.commandmessage;

public class SimpleCommandMessage extends CommandMessage {
    private String jsonType = getClass().getSimpleName();

    public SimpleCommandMessage(CommandType type) {
        super(type);
    }
}
