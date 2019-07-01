package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Weapon;

/**
 * States when the player is using a weapon
 */
public abstract class SelectedWeaponState extends AfterSelectedAggregateActionState {
    private final Weapon selectedWeapon;

    SelectedWeaponState(AggregateAction selectedAggregateAction, Weapon selectedWeapon) {
        super(selectedAggregateAction);
        this.selectedWeapon = selectedWeapon;
    }

    /**
     * Gets the currently selected weapon
     *
     * @return the currently selected weapon
     */
    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }
}
