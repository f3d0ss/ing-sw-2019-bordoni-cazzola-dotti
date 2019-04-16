package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.*;

public class DoneCommand implements Command {
    private Player player;
    private PlayerState currentState;
    private PlayerState nextState;

    public DoneCommand(Player player, ManageTurnState currentState) {
        this.currentState = currentState;
        this.player = player;
        nextState = new IdleState();
    }

    public DoneCommand(Player player, ShootedState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new IdleState();
    }

    public DoneCommand(Player player, SelectedAggregateActionState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new ManageTurnState();
    }

    public DoneCommand(Player player, ChoosingWeaponOptionState currentState) {
        this.player = player;
        this.currentState = currentState;
        if (currentState.getSelectedWeapon().hasExtraMove())
            nextState = new ExtraMoveState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon());
        else
            nextState = new ReadyToShootState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon());
    }

    public DoneCommand(Player player, ExtraMoveState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new ReadyToShootState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon());
    }

    public DoneCommand(Player player, ScopeState currentState) {
        this.player = player;
        this.currentState = currentState;
        if (currentState.getSelectedWeapon().hasExtraMove())
            nextState = new AfterShotState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon());
        else
            nextState = new ManageTurnState();
    }

    public DoneCommand(Player player, AfterShotState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new ManageTurnState();
    }
    /*create one constructor for each possible done in chart*/

    @Override
    public void execute() {
        player.changeState(nextState);
    }

    @Override
    public void undo() {
        player.changeState(currentState);
    }
}
