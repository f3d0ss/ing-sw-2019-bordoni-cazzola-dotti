package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Weapon;

public abstract class SelectedWeaponState extends AfterSelectedAggregateActionState {
    private final Weapon selectedWeapon;

    SelectedWeaponState(AggregateAction selectedAggregateAction, Weapon selectedWeapon) {
        super(selectedAggregateAction);
        this.selectedWeapon = selectedWeapon;
    }

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }
}
