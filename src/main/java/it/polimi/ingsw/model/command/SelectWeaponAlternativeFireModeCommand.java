package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;
import it.polimi.ingsw.model.playerstate.PendingPaymentWeaponOptionState;

public class SelectWeaponAlternativeFireModeCommand extends SelectWeaponOptionCommand {
    public SelectWeaponAlternativeFireModeCommand(Player player, ChoosingWeaponOptionState currentState) {
        super(player, currentState);
    }

    @Override
    public void execute() {
        currentState.getSelectedWeapon().setSelectedAlternativeFireMode(true);
        player.changeState(new PendingPaymentWeaponOptionState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), currentState.getSelectedWeapon().getAlternativeFireModeCost()));
    }

    @Override
    public void undo() {
        currentState.getSelectedWeapon().setSelectedAlternativeFireMode(false);
        player.changeState(currentState);
    }
}