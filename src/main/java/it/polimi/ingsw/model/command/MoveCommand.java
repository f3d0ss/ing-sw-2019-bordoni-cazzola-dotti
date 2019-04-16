package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.playerstate.AfterSelectedAggregateActionState;

public class MoveCommand implements Command {
    private Player player;
    private Square newPosition;
    private AfterSelectedAggregateActionState currentState;

    public MoveCommand(Player player, Square newPosition, AfterSelectedAggregateActionState currentState) {
        this.player = player;
        this.newPosition = newPosition;
        this.currentState = currentState;
    }

    @Override
    public void execute() {
        player.getPosition().removePlayer(player);
        player.move(newPosition);
        player.getPosition().addPlayer(player);
    }

    @Override
    public void undo() {

    }
}
