package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.playerstate.TargetingSquareState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.SquareCommandMessage;

/**
 * This command represent the action of select a square as target
 */
public class SelectTargetSquareCommand implements WeaponCommand {
    private final Square targetSquare;
    private final TargetingSquareState currentState;

    /**
     * This constructor create a command for select a square as target
     *
     * @param currentState is the current state
     * @param targetSquare is the square selected as target
     */
    public SelectTargetSquareCommand(TargetingSquareState currentState, Square targetSquare) {
        this.currentState = currentState;
        this.targetSquare = targetSquare;
    }

    /**
     * This method add the target to the current state
     */
    @Override
    public void execute() {
        currentState.addTargetSquare(targetSquare);
    }

    /**
     * This method remove the target from the current state
     */
    @Override
    public void undo() {
        currentState.removeTargetSquare(targetSquare);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUndoable() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandMessage createCommandMessage() {
        return new SquareCommandMessage(CommandType.SELECT_TARGET_SQUARE, targetSquare.getRow(), targetSquare.getCol());
    }
}
