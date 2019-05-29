package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.playerstate.TargetingSquareState;

public class SelectTargetSquareCommand implements WeaponCommand {
    private Square targetSquare;
    private TargetingSquareState currentState;

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

    @Override
    public boolean isUndoable() {
        return true;
    }
}
