package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;

public class ChoosingWeaponState extends AfterSelectedAggregateActionState {
    public ChoosingWeaponState(AggregateAction selectedAggregateAction) {
        super(selectedAggregateAction);
    }
}
