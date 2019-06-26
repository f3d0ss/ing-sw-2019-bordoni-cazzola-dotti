package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.TargetingPlayerState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.SimpleCommandMessage;

/**
 * This command represent the action of use a newton
 */
public class UseNewtonCommand implements Command {
    private Player player;
    private TargetingPlayerState currentState;
    private Player targetPlayer;
    private Square targetSquare;
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
     * @return true if the command is undoable
     */
    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public CommandMessage createCommandMessage() {
        return new SimpleCommandMessage(CommandType.USE_NEWTON);
    }
}
