package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.PendingPaymentState;

public class SelectAmmoPaymentCommand extends SelectPaymentCommand {
    private Color color;

    public SelectAmmoPaymentCommand(Player player, PendingPaymentState currentState, Color color) {
        super(player, currentState);
        this.color = color;
    }

    @Override
    public void execute() {
        currentState.addPendingAmmo(color);
    }

    @Override
    public void undo() {
        currentState.removePendingAmmo(color);
    }
}
