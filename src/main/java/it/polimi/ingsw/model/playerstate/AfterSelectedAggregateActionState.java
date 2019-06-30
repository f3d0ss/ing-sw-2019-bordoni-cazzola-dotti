package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;

public abstract class AfterSelectedAggregateActionState implements PlayerState {
    private final AggregateAction selectedAggregateAction;

    AfterSelectedAggregateActionState(AggregateAction selectedAggregateAction) {
        this.selectedAggregateAction = selectedAggregateAction;
    }

    public AggregateAction getSelectedAggregateAction() {
        return selectedAggregateAction;
    }
}
