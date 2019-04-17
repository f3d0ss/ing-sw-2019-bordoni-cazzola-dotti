package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;

public class SelectTargetSquareCommand extends WeaponCommand {
    private Square targetSquare;

    public SelectTargetSquareCommand(ReadyToShootState currentState, Square targetSquare) {
        super(currentState);
        this.targetSquare = targetSquare;
    }

    @Override
    public void execute() {
        currentState.getSelectedWeapon().addTargetSquare(targetSquare);
    }

    @Override
    public void undo() {
        currentState.getSelectedWeapon().removeTargetSquare(targetSquare);
    }
}
