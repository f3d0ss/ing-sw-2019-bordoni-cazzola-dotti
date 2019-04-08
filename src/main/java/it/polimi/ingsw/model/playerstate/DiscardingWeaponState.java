package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;

public class DiscardingWeaponState extends AfterSelectedAggregateActionState{
    public DiscardingWeaponState(AggregateAction selectedAggregateAction) {
        super(selectedAggregateAction);
    }
}
