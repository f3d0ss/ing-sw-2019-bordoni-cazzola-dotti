package it.polimi.ingsw.model.command;

import it.polimi.ingsw.view.commandmessage.CommandMessage;

/**
 * The command pattern is a behavioral design pattern and is part of the GoF‘s formal list of design patterns.
 * This pattern encapsulates in an object all the data required for performing a given action (command)
 * This interface must be implemented by every command
 */
public interface Command {
    /**
     * Executes the command
     */
    void execute();

    /**
     * Backward execution of {@link #execute()} It restores the previous state.
     */
    void undo();

    /**
     * @return True if it is possible to {@link #undo()} the command
     */
    boolean isUndoable();

    /**
     * @return The command message that describes the command
     */
    CommandMessage createCommandMessage();
}