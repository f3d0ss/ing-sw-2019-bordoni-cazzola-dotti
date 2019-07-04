package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;

/**
 * State of the player after selecting an {@link AggregateAction}
 */
public abstract class AfterSelectedAggregateActionState implements PlayerState {
    private final AggregateAction selectedAggregateAction;

    /**
     * This constructor create the state of the player after selecting an aggregate action
     *
     * @param selectedAggregateAction This is the aggregate action selected
     */
    AfterSelectedAggregateActionState(AggregateAction selectedAggregateAction) {
        this.selectedAggregateAction = selectedAggregateAction;
    }

    /**
     * Gets the selected aggregate action of the player
     *
     * @return the selected aggregate action of the player
     */
    public AggregateAction getSelectedAggregateAction() {
        return selectedAggregateAction;
    }
}
