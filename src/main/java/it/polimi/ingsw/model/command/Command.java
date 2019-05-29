package it.polimi.ingsw.model.command;

public interface Command {
    /**
     * This method execute the command
     */
    void execute();

    /**
     * This method undo the command
     */
    void undo();

    /**
     * @return true if the command is undoable
     */
    boolean isUndoable();
}