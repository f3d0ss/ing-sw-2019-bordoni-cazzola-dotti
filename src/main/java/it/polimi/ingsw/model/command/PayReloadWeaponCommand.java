package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
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
        currentState.getPendingAmmoPayment().forEach((color, amount) -> player.pay(color, amount));
        currentState.getPendingCardPayment().forEach(powerUp -> player.pay(powerUp));
        currentState.getSelectedReloadingWeapon().reload();
        player.changeState(new ManageTurnState());
    }

    @Override
    public void undo() {
        currentState.getPendingAmmoPayment().forEach((color, amount) -> player.refund(color, amount));
        currentState.getPendingCardPayment().forEach(powerUp -> player.refund(powerUp));
        currentState.getSelectedReloadingWeapon().unload();
        player.changeState(currentState);
    }
}
