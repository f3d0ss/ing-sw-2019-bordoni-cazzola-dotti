package it.polimi.ingsw.network;

import it.polimi.ingsw.view.commandmessage.CommandMessage;

import java.util.List;

/**
 * This class wraps a list of {@link CommandMessage} during transfer from server to client.
 */
public class CommandViewTransfer extends Message {

    private final List<CommandMessage> attachedCommand;
    private final boolean undo;

    public CommandViewTransfer(List<CommandMessage> commands, boolean undo) {
        super(Protocol.SEND_COMMANDS, "", null);
        attachedCommand = commands;
        this.undo = undo;
    }

    /**
     * Gets the list of commands in attachment to the message.
     *
     * @return the list of commands in the message.
     */
    public List<CommandMessage> getAttachment() {
        return attachedCommand;
    }

    /**
     * Returns true if 'undo' is enabled and can be chosen like a command.
     *
     * @return true if there is the 'undo' command
     */
    public boolean isUndo() {
        return undo;
    }
}
