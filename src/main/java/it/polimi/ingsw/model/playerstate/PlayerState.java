package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.command.Command;

import java.util.List;

/**
 * This interface must be implemented by every PlayerState. A Player's behavior is based on its PlayerState.
 */
public interface PlayerState {
    /**
     * Gets the possible commands for the player in this state
     *
     * @param player Player that is in this state
     * @return List of the possible commands in this state
     */
    List<Command> getPossibleCommands(Player player);
}
