package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.command.Command;

import java.util.List;

public class PendingPaymentScopeState extends SelectedWeaponState implements PendingPaymentState{
    public PendingPaymentScopeState(AggregateAction selectedAggregateAction, Weapon selectedWeapon) {
        super(selectedAggregateAction, selectedWeapon);
    }

    @Override
    public void addPendingAmmo(Color color) {

    }

    @Override
    public void addPendingCard(PowerUp powerUp) {

    }

    @Override
    public List<Command> getPossibleCommands(Player player) {
        return null;
    }
}
