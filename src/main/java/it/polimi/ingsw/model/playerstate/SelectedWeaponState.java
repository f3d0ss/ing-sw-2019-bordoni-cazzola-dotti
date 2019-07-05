package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Weapon;

/**
 * States when the player is using a weapon
 */
public abstract class SelectedWeaponState extends AfterSelectedAggregateActionState {
    /**
     * This is the weapon selected in the action
     */
    private final Weapon selectedWeapon;

    /**
     * This constructor create the state of the player where he had selected a weapon
     *
     * @param selectedAggregateAction This is the aggregate action the player is executing
     * @param selectedWeapon          This is the weapon selected in the action
     */
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
