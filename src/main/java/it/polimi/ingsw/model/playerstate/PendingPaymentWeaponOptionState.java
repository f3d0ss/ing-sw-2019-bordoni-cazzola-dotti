package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Weapon;


import java.util.Map;

public class PendingPaymentWeaponOptionState extends SelectedWeaponState {
    private Map<Color, Integer> modeCost;
    public PendingPaymentWeaponOptionState(AggregateAction selectedAggregateAction, Weapon selectedWeapon, Map<Color,Integer> firstOptionalModeCost) {
        super(selectedAggregateAction, selectedWeapon);
        this.modeCost = firstOptionalModeCost;
    }

}
