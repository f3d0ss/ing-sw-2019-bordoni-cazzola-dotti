package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.PendingPaymentScopeState;

public class PayScopeCommand implements Command {
    private Player player;
    private PendingPaymentScopeState currentState;

    public PayScopeCommand(Player player, PendingPaymentScopeState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
