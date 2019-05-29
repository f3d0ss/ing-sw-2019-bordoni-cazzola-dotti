package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.PendingPaymentState;

/**
 * This command represent the action of select an ammo for a payment
 */
public class SelectAmmoPaymentCommand extends SelectPaymentCommand {
    private Color color;

    /**
     * This constructor create a command for select an ammo for a payment
     * @param player is the player who select the ammo
     * @param currentState is the current state
     * @param color is the color of the ammo selected
     */
    public SelectAmmoPaymentCommand(Player player, PendingPaymentState currentState, Color color) {
        super(player, currentState);
        this.color = color;
    }

    /**
     * This method add an ammo to the pending payment
     */
    @Override
    public void execute() {
        currentState.addPendingAmmo(color);
    }

    /**
     * This method remove an ammo from the pending payment
     */
    @Override
    public void undo() {
        currentState.removePendingAmmo(color);
    }

    /**
     * @return true if the command is undoable
     */
    @Override
    public boolean isUndoable() {
        return true;
    }
}
