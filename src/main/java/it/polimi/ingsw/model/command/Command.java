package it.polimi.ingsw.model.command;

import it.polimi.ingsw.view.commandmessage.CommandMessage;

public interface Command {
    void execute();

    void undo();

    boolean isUndoable();

    CommandMessage createCommandMessage();
}