package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.CardinalDirection;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.AfterSelectedAggregateActionState;

public class MoveCommand implements Command {
    private Player player;
    private CardinalDirection direction;
    private AfterSelectedAggregateActionState currentState;

    public MoveCommand(Player player, CardinalDirection direction, AfterSelectedAggregateActionState currentState) {
        this.player = player;
        this.direction = direction;
        this.currentState = currentState;
    }

    @Override
    public void execute() {
        player.getPosition().removePlayer(player);
        player.move(direction);
        player.getPosition().addPlayer(player);
    }

    @Override
    public void undo() {

    }
}
