package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.*;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.SimpleCommandMessage;

/**
 * This command change the player state from currentState to nextState
 */
public class DoneCommand implements Command {
    private Player player;
    private PlayerState currentState;
    private PlayerState nextState;
    private boolean undoable;

    /**
     * This constructor create a command that change the player state from ManageTurnState to IdleState
     *
     * @param player       is the current player
     * @param currentState is the current state
     */
    public DoneCommand(Player player, ManageTurnState currentState) {
        this.currentState = currentState;
        this.player = player;
        nextState = new IdleState();
        undoable = false;
    }

    /**
     * This constructor create a command that change the player state from ShootedState to IdleState
     *
     * @param player       is the current player
     * @param currentState is the current state
     */
    public DoneCommand(Player player, ShootedState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new IdleState();
        undoable = false;
    }

    /**
     * This constructor create a command that change the player state from ChoosingWeaponOptionState to ReadyToShootState
     *
     * @param player       is the current player
     * @param currentState is the current state
     */
    public DoneCommand(Player player, ChoosingWeaponOptionState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new ReadyToShootState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon());
        undoable = true;
    }

    /**
     * This constructor create a command that change the player state from ReadyToShootState to ManageTurnState
     *
     * @param player       is the current player
     * @param currentState is the current state
     */
    public DoneCommand(Player player, ReadyToShootState currentState) {
        this.player = player;
        this.currentState = currentState;
        nextState = new ManageTurnState();
        undoable = false;
    }

    /**
     * This constructor create a command that change the player state from ScopeState to ReadyToShootState if the player
     * can shoot again or do an extra move, otherwise change the state to ManageTurnState
     *
     * @param player       is the current player
     * @param currentState is the current state
     */
    public DoneCommand(Player player, ScopeState currentState) {
        this.player = player;
        this.currentState = currentState;
        if (currentState.getSelectedWeapon().hasExtraMove() || currentState.getSelectedWeapon().hasDamageToDo())
            nextState = new ReadyToShootState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon());
        else
            nextState = new ManageTurnState();
        undoable = false;
    }

    /**
     * This method execute the command
     */
    @Override
    public void execute() {
        player.changeState(nextState);
    }

    /**
     * This method undo the command
     */
    @Override
    public void undo() {
        player.changeState(currentState);
    }

    /**
     * @return true if the command is undoable
     */
    @Override
    public boolean isUndoable() {
        return undoable;
    }

    @Override
    public CommandMessage createCommandMessage() {
        return new SimpleCommandMessage(CommandType.DONE);
    }
}
