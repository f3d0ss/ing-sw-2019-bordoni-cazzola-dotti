package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.command.Command;

import java.util.List;

/**
 * A Player changes its behavior based on its player state.
 * This interface must be implemented by every PlayerState
 */
public interface PlayerState {
    /**
     * @param player Player that is in this state
     * @return List of possible commands
     */
    List<Command> getPossibleCommands(Player player);
}
