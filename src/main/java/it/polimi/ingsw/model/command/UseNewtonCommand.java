package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.playerstate.SelectedPowerUpState;

import java.util.List;

public class UseNewtonCommand implements Command {
    private List<MoveCommand> moves;
    private SelectedPowerUpState currentState;

    public UseNewtonCommand(List<MoveCommand> moves, SelectedPowerUpState currentState) {
        this.moves = moves;
        this.currentState = currentState;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
