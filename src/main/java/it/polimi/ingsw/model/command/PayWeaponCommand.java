package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponState;

/**
 * This command represents the action of buy a weapon
 */
public class PayWeaponCommand implements Command {
    private Player player;
    private PendingPaymentWeaponState currentState;

    /**
     * This constructor create a command for buy a weapon
     * @param player is the player who buy the weapon
     * @param currentState is the current state
     */
    public PayWeaponCommand(Player player, PendingPaymentWeaponState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    /**
     * This method actualize the payment and give the weapon to the player
     */
    @Override
    public void execute() {
        currentState.getPendingAmmoPayment().forEach((color, amount) -> player.pay(color, amount));
        currentState.getPendingCardPayment().forEach(powerUp -> player.pay(powerUp));
        player.addWeapon(currentState.getSelectedWeapon());
        player.changeState(new ManageTurnState());
    }

    /**
     * This method refund the player and remove the weapon from the player
     */
    @Override
    public void undo() {
        currentState.getPendingAmmoPayment().forEach((color, amount) -> player.refund(color, amount));
        currentState.getPendingCardPayment().forEach(powerUp -> player.refund(powerUp));
        player.removeWeapon(currentState.getSelectedWeapon());
        player.changeState(currentState);
    }

    /**
     * @return true if the command is undoable
     */
    @Override
    public boolean isUndoable() {
        return false;
    }
}
