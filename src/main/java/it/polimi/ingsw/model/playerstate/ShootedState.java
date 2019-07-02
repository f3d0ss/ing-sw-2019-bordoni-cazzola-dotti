package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.DoneCommand;
import it.polimi.ingsw.model.command.UseTagbackGrenadeCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * State when the player has just been shot
 */
public class ShootedState implements PlayerState {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        player.getTagbackGrenades().forEach(tagbackGrenade -> commands.add(new UseTagbackGrenadeCommand(player, tagbackGrenade)));
        DoneCommand doneCommand = new DoneCommand(player, this);
        if (!player.getPowerUps().isEmpty()) {
            commands.add(doneCommand);
            return commands;
        }
        doneCommand.execute();
        return new ArrayList<>();
    }
}
