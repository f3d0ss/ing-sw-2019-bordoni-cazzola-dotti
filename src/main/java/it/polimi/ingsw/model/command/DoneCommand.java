package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.*;

public class DoneCommand implements Command {
    private Player player;
    private PlayerState currentState;
    private PlayerState nextState;
    private boolean undoable;


    public DoneCommand(Player player, ManageTurnState currentState) {
        this.currentState = currentState;
        this.player = player;
        nextState = new IdleState();
        undoable = false;
    }

    public DoneCommand(Player player, ShootedState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new IdleState();
        undoable = false;
    }

    public DoneCommand(Player player, ChoosingWeaponOptionState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new ReadyToShootState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon());
        undoable = true;
    }

    public DoneCommand(Player player, ReadyToShootState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new ManageTurnState();
        undoable = false;
    }

    public DoneCommand(Player player, ScopeState currentState) {
        this.player = player;
        this.currentState = currentState;
        if (currentState.getSelectedWeapon().hasExtraMove() || currentState.getSelectedWeapon().hasDamageToDo())
            nextState = new ReadyToShootState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon());
        else
            nextState = new ManageTurnState();
        undoable = false;
    }


    @Override
    public void execute() {
        player.changeState(nextState);
    }

    @Override
    public void undo() {
        player.changeState(currentState);
    }

    @Override
    public boolean isUndoable() {
        return undoable;
    }
}
