package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.TargetingPlayerState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.PlayerCommandMessage;

/**
 * This command represent the action of select a player as target
 */
public class SelectTargetPlayerCommand implements WeaponCommand {
    private final TargetingPlayerState currentState;
    private final Player targetPlayer;

    /**
     * This constructor create a command for select a player as target
     *
     * @param currentState is the current state
     * @param targetPlayer is the player selected as target
     */
    public SelectTargetPlayerCommand(TargetingPlayerState currentState, Player targetPlayer) {
        this.currentState = currentState;
        this.targetPlayer = targetPlayer;
    }

    /**
     * This method add the target to the current state
     */
    @Override
    public void execute() {
        currentState.addTargetPlayer(targetPlayer);
    }

    /**
     * This remove the target from current state
     */
    @Override
    public void undo() {
        currentState.removeTargetPlayer(targetPlayer);
    }

    /**
     * @return true if the command is undoable
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
        return new PlayerCommandMessage(CommandType.SELECT_TARGET_PLAYER, targetPlayer.getId());
    }
}
