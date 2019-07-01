package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.command.Command;

import java.util.List;

/**
 * A Player's behavior is based on its PlayerState.
 * This interface must be implemented by every PlayerState
 */
public interface PlayerState {
    /**
     * @param player Player that is in this state
     * @return List of the possible commands in this state
     */
    List<Command> getPossibleCommands(Player player);
}
