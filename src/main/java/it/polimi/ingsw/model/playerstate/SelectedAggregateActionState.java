package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;

public class SelectedAggregateActionState extends AfterSelectedAggregateActionState {
    public SelectedAggregateActionState(AggregateAction selectedAggregateAction) {
        super(selectedAggregateAction);
    }
}
