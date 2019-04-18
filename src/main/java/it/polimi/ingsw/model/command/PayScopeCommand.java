package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.PendingPaymentScopeState;
import it.polimi.ingsw.model.playerstate.SelectScopeTargetState;

public class PayScopeCommand implements Command {
    private Player player;
    private PendingPaymentScopeState currentState;

    public PayScopeCommand(Player player, PendingPaymentScopeState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    @Override
    public void execute() {
        currentState.getPendingAmmoPayment().forEach((color, amount) -> player.pay(color, amount));
        currentState.getPendingCardPayment().forEach(powerUp -> player.pay(powerUp));
        player.changeState(new SelectScopeTargetState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), currentState.getShootedPlayers()));
    }

    @Override
    public void undo() {
        currentState.getPendingAmmoPayment().forEach((color, amount) -> player.refund(color, amount));
        currentState.getPendingCardPayment().forEach(powerUp -> player.refund(powerUp));
        player.changeState(currentState);
    }
}
