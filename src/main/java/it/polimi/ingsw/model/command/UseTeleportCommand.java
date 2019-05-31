package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.SelectedTeleporterState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.SimpleCommandMessage;

/**
 * This command represent the action of use a teleport
 */
public class UseTeleportCommand implements Command {
    private Player player;
    private SelectedTeleporterState currentState;
    private Square oldSquare;
    private Square newPosition;

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
        oldSquare.removePlayer(player);
        player.move(newPosition);
        player.getPosition().addPlayer(player);
        player.changeState(new ManageTurnState());
    }

    /**
     * This method undo the effect of the Teleport
     */
    @Override
    public void undo() {
        player.getPosition().removePlayer(player);
        player.move(oldSquare);
        player.getPosition().addPlayer(player);
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
        return new SimpleCommandMessage(CommandType.USE_TELEPORT);
    }
}
