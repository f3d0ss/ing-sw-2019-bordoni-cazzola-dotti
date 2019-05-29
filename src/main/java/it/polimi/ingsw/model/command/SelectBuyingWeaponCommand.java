package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SpawnSquare;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.DiscardingWeaponState;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponState;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

public class SelectBuyingWeaponCommand extends GrabCommand {
    private final Weapon weapon;
    private final SelectedAggregateActionState currentState;
    private final SpawnSquare spawn;

    public SelectBuyingWeaponCommand(Player player, SelectedAggregateActionState currentState, Weapon weapon, SpawnSquare spawn) {
        super(player);
        this.weapon = weapon;
        this.currentState = currentState;
        this.spawn = spawn;
    }

    /**
     * This method remove the weapon from the Spawn and if the player is full put him in the state where he can choose the weapon he want drop otherwise in PendingPaymentWeaponState
     */
    @Override
    public void execute() {
        spawn.removeWeapon(weapon);
        if (player.getWeapons().size() == Player.MAX_WEAPONS) {
            player.changeState(new DiscardingWeaponState(currentState.getSelectedAggregateAction(), weapon, spawn));
        } else {
            player.changeState(new PendingPaymentWeaponState(currentState.getSelectedAggregateAction(), weapon));
        }
    }

    /**
     * This method restore the previous state
     */
    @Override
    public void undo() {
        spawn.addWeapon(weapon);
        player.changeState(currentState);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
