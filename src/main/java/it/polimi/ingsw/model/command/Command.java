package it.polimi.ingsw.model.command;

public interface Command {
    void execute();

    void undo();
}