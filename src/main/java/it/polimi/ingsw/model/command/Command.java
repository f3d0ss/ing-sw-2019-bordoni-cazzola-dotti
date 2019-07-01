package it.polimi.ingsw.model.command;

import it.polimi.ingsw.view.commandmessage.CommandMessage;

/**
 * This interface must be implemented by every command.
 * The command pattern is a behavioral design pattern.
 * This pattern encapsulates in an object all the data required for performing a given action (command).
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
     * Gets if it is possible to undo the command
     *
     * @return True if it is possible to undo the command
     */
    boolean isUndoable();

    /**
     * Create the command message that describes this command
     *
     * @return The command message that describes this command
     */
    CommandMessage createCommandMessage();
}