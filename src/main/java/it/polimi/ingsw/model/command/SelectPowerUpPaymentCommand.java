package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.playerstate.PendingPaymentState;

public class SelectPowerUpPaymentCommand extends SelectPaymentCommand {
    private PowerUp powerUp;

    public SelectPowerUpPaymentCommand(Player player, PendingPaymentState currentState, PowerUp powerUp) {
        super(player, currentState);
        this.powerUp = powerUp;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
