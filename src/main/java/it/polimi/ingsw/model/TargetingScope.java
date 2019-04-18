package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.playerstate.TargetingPlayerState;

import java.util.List;

public class TargetingScope extends PowerUp {
    public static final int DAMAGE = 1;
    private Player targetPlayer;

    public TargetingScope(String name, Color color) {
        super(name, color);
    }

    @Override
    public List<Command> getPossibleCommands(GameBoard gameboard, Player player, TargetingPlayerState currentState) {
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

    @Override
    public void addTargetPlayer(Player targetPlayer) {
        if (this.targetPlayer == null)
            throw new IllegalStateException();
        this.targetPlayer = targetPlayer;
    }

    @Override
    public void removeTargetPlayer(Player targetPlayer) {
        this.targetPlayer = null;
    }

}