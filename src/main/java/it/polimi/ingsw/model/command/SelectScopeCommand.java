package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.playerstate.PendingPaymentScopeState;
import it.polimi.ingsw.model.playerstate.ScopeState;

public class SelectScopeCommand implements Command {
    private Player player;
    private ScopeState currentState;
    private PowerUp powerUp;

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

    @Override
    public boolean isUndoable() {
        return true;
    }
}
