package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Weapon;

public class PendingPaymentReloadBeforeShotState extends SelectedWeaponState {
    public PendingPaymentReloadBeforeShotState(AggregateAction selectedAggregateAction, Weapon selectedWeapon) {
        super(selectedAggregateAction, selectedWeapon);
    }
}
