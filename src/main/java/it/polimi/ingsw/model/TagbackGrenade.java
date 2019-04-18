package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.playerstate.TargetingPlayerState;

import java.util.List;

public class TagbackGrenade extends PowerUp {
    public TagbackGrenade(String name, Color color) {
        super(name, color);
    }

    @Override
    public List<Command> getPossibleCommands(GameBoard gameboard, Player player, TargetingPlayerState currentState) {
        return null;
    }

    @Override
    public boolean isScope() {
        return false;
    }

    @Override
    public boolean isTagBackGrenade() {
        return true;
    }
}
