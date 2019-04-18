package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.playerstate.PendingPaymentState;

public class SelectPowerUpPaymentCommand extends SelectPaymentCommand {
    private PowerUp powerUp;

    public SelectPowerUpPaymentCommand(Player player, PendingPaymentState currentState, PowerUp powerUp) {
        super(player, currentState);
        this.powerUp = powerUp;
    }

    /**
     * This method add a powerUp to the pending payment
     */
    @Override
    public void execute() {
        currentState.addPendingCard(powerUp);
    }

    /**
     * This method remove a powerUp from the pending payment
     */
    @Override
    public void undo() {
        currentState.removePendingCard(powerUp);
    }
}
