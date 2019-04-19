package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.playerstate.*;

public class MoveCommand implements Command {
    private Player player;
    private Square newPosition;
    private MovableState currentState;
    private Square oldSquare;
    private PlayerState nextState;

    public MoveCommand(Player player, Square newPosition, SelectedAggregateActionState currentState) {
        this.player = player;
        this.newPosition = newPosition;
        this.currentState = currentState;
        if (currentState.getSelectedAggregateAction().isShoot() || currentState.getSelectedAggregateAction().isGrab())
            nextState = currentState;
        else
            nextState = new ManageTurnState();
    }

    public MoveCommand(Player player, Square newPosition, ExtraMoveState currentState) {
        this.player = player;
        this.newPosition = newPosition;
        this.currentState = currentState;
        nextState = new ReadyToShootState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon());
    }

    public MoveCommand(Player player, Square newPosition, AfterShotState currentState) {
        this.player = player;
        this.newPosition = newPosition;
        this.currentState = currentState;
        nextState = new ManageTurnState();
    }

    /**
     * This method move the player
     */
    @Override
    public void execute() {
        oldSquare = player.getPosition();
        oldSquare.removePlayer(player);
        player.move(newPosition);
        player.getPosition().addPlayer(player);
        currentState.useMoves();
        player.changeState(nextState);
    }

    /**
     * This method move the player in the old position
     */
    @Override
    public void undo() {
        player.getPosition().removePlayer(player);
        player.move(oldSquare);
        player.getPosition().addPlayer(player);
        currentState.resetMoves();
        player.changeState(currentState);
    }
}
