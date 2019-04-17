package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
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
        currentState.getPendingAmmoPayment().forEach((color, amount) -> player.pay(color, amount));
        currentState.getPendingCardPayment().forEach(powerUp -> player.pay(powerUp));
        player.addWeapon(currentState.getSelectedWeapon());
        player.changeState(new ManageTurnState());
    }

    @Override
    public void undo() {
        currentState.getPendingAmmoPayment().forEach((color, amount) -> player.refund(color, amount));
        currentState.getPendingCardPayment().forEach(powerUp -> player.refund(powerUp));
        player.removeWeapon(currentState.getSelectedWeapon());
        player.changeState(currentState);
    }
}
