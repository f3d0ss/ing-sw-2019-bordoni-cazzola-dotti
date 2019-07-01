package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.PendingPaymentScopeState;
import it.polimi.ingsw.model.playerstate.SelectScopeTargetState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.SimpleCommandMessage;

/**
 * This command actualize the payment for the scope
 */
public class PayScopeCommand implements Command {
    private final Player player;
    private final PendingPaymentScopeState currentState;

    /**
     * This constructor create a command for pay the scope
     *
     * @param player       is the player who use the scope
     * @param currentState is the current state
     */
    public PayScopeCommand(Player player, PendingPaymentScopeState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    /**
     * This method actualize the payment
     */
    @Override
    public void execute() {
        player.changeState(new SelectScopeTargetState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), currentState.getShootedPlayers()));
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
