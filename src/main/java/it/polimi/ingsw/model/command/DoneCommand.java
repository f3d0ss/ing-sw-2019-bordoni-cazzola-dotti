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
    /**
     * This is the player is the player doing the command
     */
    private final Player player;
    /**
     * This is the current state of the player
     */
    private final PlayerState currentState;
    /**
     * This is the future state of the player
     */
    private final PlayerState nextState;
    /**
     * This is true if the command is undoable
     */
    private final boolean undoable;
    /**
     * This is true if the command end the turn
     */
    private boolean endTurn = false;

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
        this.endTurn = true;
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
        if (endTurn)
            return new SimpleCommandMessage(CommandType.END_TURN);
        return new SimpleCommandMessage(CommandType.DONE);
    }
}