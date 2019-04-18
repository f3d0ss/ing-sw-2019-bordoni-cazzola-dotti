package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.DiscardingWeaponState;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponState;

public class SelectDiscardedWeaponCommand implements Command {

    private Player player;
    private DiscardingWeaponState currentState;
    private Weapon weaponToDiscard;
    private boolean wasLoaded;

    public SelectDiscardedWeaponCommand(Player player, DiscardingWeaponState currentState, Weapon weaponToDiscard) {
        this.player = player;
        this.currentState = currentState;
        this.weaponToDiscard = weaponToDiscard;
    }

    /**
     * This method discard a weapon and put it in the spawn
     */
    @Override
    public void execute() {
        wasLoaded = weaponToDiscard.isLoaded();
        weaponToDiscard.reload();
        player.removeWeapon(weaponToDiscard);
        currentState.getSpawn().addWeapon(weaponToDiscard);
        player.changeState(new PendingPaymentWeaponState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon()));
    }

    /**
     * This method restore the weapon discarded
     */
    @Override
    public void undo() {
        if (!wasLoaded)
            weaponToDiscard.unload();
        player.addWeapon(weaponToDiscard);
        currentState.getSpawn().removeWeapon(weaponToDiscard);
        player.changeState(currentState);
    }
}
