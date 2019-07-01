package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.command.Command;

import java.util.List;

/**
 * State of the player when it's not its turn and it has not been shot
 */
public class IdleState implements PlayerState {
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("squid:S1168")
    @Override
    public List<Command> getPossibleCommands(Player player) {
        return null;
    }
}
