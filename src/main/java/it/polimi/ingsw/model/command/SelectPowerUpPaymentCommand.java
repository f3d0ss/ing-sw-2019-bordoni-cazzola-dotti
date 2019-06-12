package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.playerstate.PendingPaymentState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.PowerUpCommandMessage;

/**
 * This command represent the action of select a power up for a payment
 */
public class SelectPowerUpPaymentCommand extends SelectPaymentCommand {
    private PowerUp powerUp;

    /**
     * This constructor create a command for select a power up for a payment
     *
     * @param player       is the player who select the power up
     * @param currentState is the current state
     * @param powerUp      is the power up selected
     */
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

    /**
     * @return true if the command is undoable
     */
    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public CommandMessage createCommandMessage() {
        return new PowerUpCommandMessage(CommandType.SELECT_POWER_UP_PAYMENT, powerUp.getType(), powerUp.getColor());
    }
}
