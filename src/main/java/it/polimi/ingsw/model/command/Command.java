package it.polimi.ingsw.model.command;

public interface Command {
    public void execute();
    public void undo();
}