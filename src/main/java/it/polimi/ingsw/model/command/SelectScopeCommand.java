package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TargetingScope;
import it.polimi.ingsw.model.playerstate.PendingPaymentScopeState;
import it.polimi.ingsw.model.playerstate.ScopeState;

public class SelectScopeCommand implements Command {
    private Player player;
    private ScopeState currentState;
    private TargetingScope powerUp;

    public SelectScopeCommand(Player player, ScopeState currentState, TargetingScope powerUp) {
        this.player = player;
        this.currentState = currentState;
        this.powerUp = powerUp;
    }

    @Override
    public void execute() {
        player.pay(powerUp);
        player.changeState(new PendingPaymentScopeState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), currentState.getShootedPlayer()));
    }

    @Override
    public void undo() {
        player.refund(powerUp);
        player.changeState(currentState);
    }
}
