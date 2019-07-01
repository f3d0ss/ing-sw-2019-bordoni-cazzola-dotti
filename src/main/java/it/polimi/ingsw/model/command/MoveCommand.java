package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.playerstate.*;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.SquareCommandMessage;

/**
 * This command represents a move action
 */
public class MoveCommand implements Command {
    private final Player player;
    private final Square newPosition;
    private final MovableState currentState;
    private final PlayerState nextState;
    private final boolean undoable;
    private Square oldSquare;

    /**
     * This constructor create a command that move the player and change the state in the next state
     *
     * @param player       is the player who move
     * @param newPosition  is the new position
     * @param currentState is the current state
     */
    public MoveCommand(Player player, Square newPosition, SelectedAggregateActionState currentState) {
        this.player = player;
        this.newPosition = newPosition;
        this.currentState = currentState;
        if (currentState.getSelectedAggregateAction().isShoot() || currentState.getSelectedAggregateAction().isGrab()) {
            nextState = currentState;
            undoable = true;
        } else {
            nextState = new ManageTurnState();
            undoable = false;
        }
    }

    /**
     * This constructor create a command that move the player and change the state in the next state
     *
     * @param player       is the player who move
     * @param newPosition  is the new position
     * @param currentState is the current state
     */
    public MoveCommand(Player player, Square newPosition, ReadyToShootState currentState) {
        this.player = player;
        this.newPosition = newPosition;
        this.currentState = currentState;
        if (!currentState.getSelectedWeapon().hasDamageToDo()) {
            nextState = new ManageTurnState();
            undoable = false;
        } else {
            nextState = currentState;
            undoable = true;
        }
    }

    /**
     * This method move the player
     */
    @Override
    public void execute() {
        oldSquare = player.getPosition();
        player.move(newPosition);
        currentState.useMoves();
        player.changeState(nextState);
    }

    /**
     * This method move the player in the old position
     */
    @Override
    public void undo() {
        player.move(oldSquare);
        currentState.resetMoves();
        player.changeState(currentState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUndoable() {
        return undoable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandMessage createCommandMessage() {
        return new SquareCommandMessage(CommandType.MOVE, newPosition.getRow(), newPosition.getCol());
    }
}
