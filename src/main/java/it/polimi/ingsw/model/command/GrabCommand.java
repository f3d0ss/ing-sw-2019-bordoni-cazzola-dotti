package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;

/**
 * This class represent a grab action
 */
public abstract class GrabCommand implements Command {
    final Player player;

    /**
     * @param player is the player who do the action
     */
    GrabCommand(Player player) {
        this.player = player;
    }

}
