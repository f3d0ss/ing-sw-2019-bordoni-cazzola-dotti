package it.polimi.ingsw.network;

import it.polimi.ingsw.view.commandmessage.CommandMessage;

import java.util.List;

public class CommandViewTransfer extends Message {

    private List<CommandMessage> attachedCommand;
    private boolean undo;

    public CommandViewTransfer(List<CommandMessage> commands, boolean undo) {
        super(Protocol.SEND_COMMANDS, "", null, null);
        attachedCommand = commands;
        this.undo = undo;
    }

    public List<CommandMessage> getAttachment() {
        return attachedCommand;
    }

    public boolean isUndo() {
        return undo;
    }
}
