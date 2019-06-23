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
    private Player player;
    private PendingPaymentScopeState currentState;

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
        currentState.getPendingAmmoPayment().forEach((color, amount) -> player.pay(color, amount));
        currentState.getPendingCardPayment().forEach(powerUp -> player.pay(powerUp));
        player.changeState(new SelectScopeTargetState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), currentState.getShootedPlayers()));
    }

    /**
     * This method refund the player
     */
    @Override
    public void undo() {
        currentState.getPendingAmmoPayment().forEach((color, amount) -> player.refund(color, amount));
        currentState.getPendingCardPayment().forEach(powerUp -> player.refund(powerUp));
        player.changeState(currentState);
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
        return new SimpleCommandMessage(CommandType.PAY);
    }
}
