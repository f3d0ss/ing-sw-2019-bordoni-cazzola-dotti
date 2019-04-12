package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.PendingPaymentReloadBeforeShotState;
import it.polimi.ingsw.model.playerstate.PendingPaymentState;

public class PayReloadBeforeShotCommand implements Command {
    private Player player;
    private PendingPaymentState currentState;

    public PayReloadBeforeShotCommand(Player player, PendingPaymentReloadBeforeShotState currentState) {
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
