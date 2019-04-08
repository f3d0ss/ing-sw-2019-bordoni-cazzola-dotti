package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponOptionState;

public class PayWeaponOptionCommand implements Command {
    private Player player;
    private PendingPaymentWeaponOptionState currentState;

    public PayWeaponOptionCommand(Player player, PendingPaymentWeaponOptionState currentState) {
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
