package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.DiscardingWeaponState;

public class SelectDiscardedWeaponCommand implements Command {

    private Player player;
    private DiscardingWeaponState currentState;

    public SelectDiscardedWeaponCommand(Player player, DiscardingWeaponState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
