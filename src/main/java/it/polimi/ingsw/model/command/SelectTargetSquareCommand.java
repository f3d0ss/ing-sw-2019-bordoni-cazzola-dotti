package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.TargetingScope;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;
import it.polimi.ingsw.model.playerstate.TargetingSquareState;

public class SelectTargetSquareCommand implements WeaponCommand {
    private Square targetSquare;
    private TargetingSquareState currentState;

    public SelectTargetSquareCommand(TargetingSquareState currentState, Square targetSquare) {
        this.currentState = currentState;
        this.targetSquare = targetSquare;
    }

    @Override
    public void execute() {
        currentState.addTargetSquare(targetSquare);
    }

    @Override
    public void undo() {
        currentState.removeTargetSquare(targetSquare);
    }
}
