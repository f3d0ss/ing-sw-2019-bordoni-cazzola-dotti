package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.UseTeleportCommand;

import java.util.ArrayList;
import java.util.List;

public class SelectedTeleporterState implements PlayerState {

    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        player.getMatch().getBoard().getSquareList().forEach(square -> commands.add(new UseTeleportCommand(player, this, square)));
        return commands;
    }
}
