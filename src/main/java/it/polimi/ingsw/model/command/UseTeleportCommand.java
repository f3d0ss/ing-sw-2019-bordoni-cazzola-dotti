package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.SelectedTeleporterState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.SquareCommandMessage;

/**
 * This command represent the action of use a teleport
 */
public class UseTeleportCommand implements Command {
    private final Player player;
    private final SelectedTeleporterState currentState;
    private final Square newPosition;
    private Square oldSquare;

    /**
     * This constructor create a command for use a teleport
     *
     * @param player       is the player who use the teleport
     * @param currentState is the current state
     * @param newPosition  is the position where the player will be moved
     */
    public UseTeleportCommand(Player player, SelectedTeleporterState currentState, Square newPosition) {
        this.player = player;
        this.currentState = currentState;
        this.newPosition = newPosition;
    }

    /**
     * This method execute the effect of the Teleport
     */
    @Override
    public void execute() {
        oldSquare = player.getPosition();
        player.move(newPosition);
        player.changeState(new ManageTurnState());
    }

    /**
     * This method undo the effect of the Teleport
     */
    @Override
    public void undo() {
        player.move(oldSquare);
        player.changeState(currentState);
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
        return new SquareCommandMessage(CommandType.USE_TELEPORT, newPosition.getRow(), newPosition.getCol());
    }
}
