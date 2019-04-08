package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;

public class SelectTargetSquareCommand extends WeaponCommand {
    private Square targetSquare;

    public SelectTargetSquareCommand(Weapon weapon, ReadyToShootState currentState, Square targetSquare) {
        super(weapon, currentState);
        this.targetSquare = targetSquare;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
