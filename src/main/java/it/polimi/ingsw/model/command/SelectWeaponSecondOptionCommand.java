package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponOptionState;

public class SelectWeaponSecondOptionCommand extends SelectWeaponOptionCommand {
    public SelectWeaponSecondOptionCommand(Player player, ChoosingWeaponOptionState currentState) {
        super(player, currentState);
    }

    @Override
    public void execute() {
        currentState.getSelectedWeapon().setSelectedSecondOptionalFireMode(true);
        player.changeState(new PendingPaymentWeaponOptionState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), currentState.getSelectedWeapon().getSecondOptionalModeCost()));
    }

    @Override
    public void undo() {
        currentState.getSelectedWeapon().setSelectedSecondOptionalFireMode(false);
        player.changeState(currentState);
    }
}
