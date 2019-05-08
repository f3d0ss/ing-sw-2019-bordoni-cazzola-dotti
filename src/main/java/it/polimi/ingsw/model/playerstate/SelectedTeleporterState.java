package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.UseTeleportCommand;

import java.util.ArrayList;
import java.util.List;

public class SelectedTeleporterState implements PlayerState {
    private PowerUp selectedTeleporter;

    public SelectedTeleporterState(PowerUp selectedTeleporter) {
        this.selectedTeleporter = selectedTeleporter;
    }

    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        player.getMatch().getBoard().getReachableSquare(player.getPosition(), Integer.MAX_VALUE).forEach(square -> commands.add(new UseTeleportCommand(player, this, square))); //TODO: use method in getAllSquare() (to be implemented)
        return commands;
    }
}
