package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.TargetingPlayerState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.SquareCommandMessage;

/**
 * This command represent the action of use a newton
 */
public class UseNewtonCommand implements Command {
    /**
     * This is the player is the player doing the command
     */
    private final Player player;
    /**
     * This is the current state of the player
     */
    private final TargetingPlayerState currentState;
    /**
     * This is the player selected as target for the newton
     */
    private final Player targetPlayer;
    /**
     * This is the square where target player will be moved
     */
    private final Square targetSquare;
    /**
     * This is the square where the target player is before the command is executed
     */
    private Square oldSquare;

    /**
     * This constructor create a command for use a newton
     *
     * @param player       is the player who use the newton
     * @param currentState is the current state
     * @param targetSquare is the square where the target player will be moved
     * @param targetPlayer is the target player
     */
    public UseNewtonCommand(Player player, TargetingPlayerState currentState, Square targetSquare, Player targetPlayer) {
        this.player = player;
        this.currentState = currentState;
        this.targetPlayer = targetPlayer;
        this.targetSquare = targetSquare;
    }

    /**
     * This method execute the effect of the Newton
     */
    @Override
    public void execute() {
        oldSquare = targetPlayer.getPosition();
        targetPlayer.move(targetSquare);
        player.changeState(new ManageTurnState());
    }

    /**
     * This method undo the effect of the Newton
     */
    @Override
    public void undo() {
        targetPlayer.move(oldSquare);
        player.changeState(currentState);
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
        return new SquareCommandMessage(CommandType.USE_NEWTON, targetSquare.getRow(), targetSquare.getCol());
    }
}
