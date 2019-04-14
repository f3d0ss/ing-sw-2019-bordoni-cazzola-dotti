package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.SelectAggregateActionCommand;

import java.util.ArrayList;
import java.util.List;

public class ManageTurnState implements PlayerState {
    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        player.getPossibleAggregateAction().forEach(aggregateAction -> commands.add(new SelectAggregateActionCommand(player, aggregateAction, this)));
        return commands;
    }
}
