package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponState;

public class PayWeaponCommand implements Command {
    private Player player;

    public PayWeaponCommand(Player player, PendingPaymentWeaponState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    private PendingPaymentWeaponState currentState;

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
