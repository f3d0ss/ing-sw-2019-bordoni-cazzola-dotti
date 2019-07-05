package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.playerstate.PendingPaymentScopeState;
import it.polimi.ingsw.model.playerstate.ScopeState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.PowerUpCommandMessage;

/**
 * This command represent the action of select a scope
 */
public class SelectScopeCommand implements Command {
    /**
     * This is the player doing the command
     */
    private final Player player;
    /**
     * This is the current state of the player
     */
    private final ScopeState currentState;
    /**
     * This is the scope selected
     */
    private final PowerUp powerUp;

    /**
     * This constructor create a command for select a scope
     *
     * @param player       is the player who select the scope
     * @param currentState is the current state
     * @param powerUp      is the scope selected
     */
    public SelectScopeCommand(Player player, ScopeState currentState, PowerUp powerUp) {
        this.player = player;
        this.currentState = currentState;
        this.powerUp = powerUp;
    }

    /**
     * This method actualize the selection of the scope to use
     */
    @Override
    public void execute() {
        player.pay(powerUp);
        player.changeState(new PendingPaymentScopeState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), currentState.getShootedPlayer()));
    }

    /**
     * This method restore the powerUp and the state for the deselect
     */
    @Override
    public void undo() {
        player.refund(powerUp);
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
        return new PowerUpCommandMessage(CommandType.SELECT_SCOPE, powerUp.getType(), powerUp.getColor());
    }
}
