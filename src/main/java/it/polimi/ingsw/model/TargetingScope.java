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

}