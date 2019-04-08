package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.PendingPaymentReloadWeaponState;

public class PayReloadWeaponCommand implements Command {
    private Player player;
    private PendingPaymentReloadWeaponState currentState;

    public PayReloadWeaponCommand(Player player, PendingPaymentReloadWeaponState currentState) {
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
