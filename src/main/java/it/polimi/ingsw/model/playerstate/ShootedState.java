package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TagbackGrenade;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.DoneCommand;
import it.polimi.ingsw.model.command.UseTagbackGrenadeCommand;

import java.util.ArrayList;
import java.util.List;

public class ShootedState implements PlayerState {

    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        player.getPowerUps().forEach(powerUp -> {
            if (powerUp.isTagBackGrenade()) {
                commands.add(new UseTagbackGrenadeCommand(player, this, (TagbackGrenade) powerUp));
            }
        });
        commands.add(new DoneCommand(player, this));
        return commands;
    }
}
