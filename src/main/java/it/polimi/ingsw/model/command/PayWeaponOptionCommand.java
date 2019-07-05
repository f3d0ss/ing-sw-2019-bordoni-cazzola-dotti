package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponOptionState;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.SimpleCommandMessage;

/**
 * This command actualize the payment for the weapon's option selected
 */
public class PayWeaponOptionCommand implements Command {
    /**
     * This is the player doing the command
     */
    private final Player player;
    /**
     * This is the current state of the player
     */
    private final PendingPaymentWeaponOptionState currentState;

    public PayWeaponOptionCommand(Player player, PendingPaymentWeaponOptionState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    /**
     * This method actualize the payment
     */
    @Override
    public void execute() {
        player.changeState(new ReadyToShootState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon()));
    }

    /**
     * This method refund the player
     */
    @Override
    public void undo() {
        player.changeState(currentState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUndoable() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandMessage createCommandMessage() {
        return new SimpleCommandMessage(CommandType.PAY);
    }
}
