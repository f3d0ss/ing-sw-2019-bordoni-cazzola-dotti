package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.*;

public class SelectReloadingWeaponCommand implements Command {
    private Player player;
    private PlayerState currentState;
    private PlayerState nextState;

    /**
     * This constructor is called from ChoosingWeaponState and set the next state to the PendingPaymentReloadBeforeShotState
     *
     * @param player       is the player doing the turn
     * @param weapon       is the weapon selected for the reloading
     * @param currentState is the current state of the player
     */
    public SelectReloadingWeaponCommand(Player player, Weapon weapon, ChoosingWeaponState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new PendingPaymentReloadBeforeShotState(currentState.getSelectedAggregateAction(), weapon);
    }

    /**
     * This constructor is called from ManageTurnState and set the next state to the PendingPaymentReloadWeaponState
     *
     * @param player       is the player doing the turn
     * @param weapon       is the weapon selected for the reloading
     * @param currentState is the current state of the player
     */
    public SelectReloadingWeaponCommand(Player player, Weapon weapon, ManageTurnState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new PendingPaymentReloadWeaponState(weapon);
    }

    /**
     * This method actualize the change of the state
     */
    @Override
    public void execute() {
        player.changeState(nextState);
    }

    /**
     * This method restore to previous state
     */
    @Override
    public void undo() {
        player.changeState(currentState);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
