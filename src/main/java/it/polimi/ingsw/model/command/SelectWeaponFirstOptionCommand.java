package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponOptionState;

public class SelectWeaponFirstOptionCommand extends SelectWeaponOptionCommand {
    public SelectWeaponFirstOptionCommand(Player player, ChoosingWeaponOptionState currentState) {
        super(player, currentState);
    }

    @Override
    public void execute() {
        currentState.getSelectedWeapon().setSelectedFirstOptionalFireMode(true);
        player.changeState(new PendingPaymentWeaponOptionState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), currentState.getSelectedWeapon().getFirstOptionalModeCost()));
    }

    @Override
    public void undo() {

    }
}
