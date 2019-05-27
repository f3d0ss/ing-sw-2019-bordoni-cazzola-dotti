package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.Square;

/**
 * This class represents a command that make a player Discard one of his powerup cards with no effect
 * and place his figure on the spawnpoint indicated by it. He is alive again.
 */
public class SelectPowerUpToDiscardCommand implements Command {

    private Player player;
    private PowerUp powerUp;
    private Square oldPosition;

    public SelectPowerUpToDiscardCommand(Player player, PowerUp powerUp) {
        this.player = player;
        this.powerUp = powerUp;
    }

    @Override
    public void execute() {
        oldPosition = player.getPosition();
        player.discardPowerUp(powerUp);
        player.respawn(powerUp.getColor());
    }

    @Override
    public void undo() {
        player.refund(powerUp);
        player.move(oldPosition);
    }
}
