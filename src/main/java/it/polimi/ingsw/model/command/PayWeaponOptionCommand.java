package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponOptionState;

public class PayWeaponOptionCommand implements Command {
    private Player player;
    private PendingPaymentWeaponOptionState currentState;

    public PayWeaponOptionCommand(Player player, PendingPaymentWeaponOptionState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    /**
     * This method actualize the payment
     */
    @Override
    public void execute() {
        currentState.getPendingAmmoPayment().forEach((color, amount) -> player.pay(color, amount));
        currentState.getPendingCardPayment().forEach(powerUp -> player.pay(powerUp));
        player.changeState(new ChoosingWeaponOptionState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon()));
    }

    /**
     * This method refund the player
     */
    @Override
    public void undo() {
        currentState.getPendingAmmoPayment().forEach((color, amount) -> player.refund(color, amount));
        currentState.getPendingCardPayment().forEach(powerUp -> player.refund(powerUp));
        player.changeState(currentState);
    }
}
