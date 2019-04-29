package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.SelectedTeleporterState;

public class UseTeleportCommand implements Command{
    private Player player;
    private SelectedTeleporterState currentState;
    private Square oldSquare;
    private Square newPosition;

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
}
