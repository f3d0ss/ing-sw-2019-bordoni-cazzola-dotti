package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

public class SelectAggregateActionCommand implements Command {
    private Player player;
    private AggregateAction aggregateAction;
    private ManageTurnState currentState;

    public SelectAggregateActionCommand(Player player, AggregateAction aggregateAction, ManageTurnState currentState) {
        this.player = player;
        this.aggregateAction = aggregateAction;
        this.currentState = currentState;
    }

    /**
     * This method select the aggregate action
     */
    @Override
    public void execute() {
        player.selectAggregateAction();
        player.changeState(new SelectedAggregateActionState(aggregateAction));
    }

    /**
     * This method deselect the aggregate action
     */
    @Override
    public void undo() {
        player.deselectAggregateAction();
        player.changeState(currentState);
    }
}
