package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.Command;

import java.util.List;

public class TargetingScope extends PowerUp {
    public TargetingScope(String name, Color color) {
        super(name, color);
    }

    @Override
    public List<Command> getPossibleCommands(GameBoard gameboard, Player player) {
        return null;
    }

    @Override
    public boolean isScope() {
        return true;
    }

    @Override
    public boolean isTagBackGrenade() {
        return false;
    }
}
