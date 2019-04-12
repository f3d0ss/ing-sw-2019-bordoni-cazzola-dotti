package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.DiscardingWeaponState;

public class SelectDiscardedWeaponCommand implements Command {

    private Player player;
    private DiscardingWeaponState currentState;
    private Weapon weaponToDiscard;

    public SelectDiscardedWeaponCommand(Player player, DiscardingWeaponState currentState, Weapon weaponToDiscard) {
        this.player = player;
        this.currentState = currentState;
        this.weaponToDiscard = weaponToDiscard;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
