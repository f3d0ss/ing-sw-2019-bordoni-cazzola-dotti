package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.DoneCommand;
import it.polimi.ingsw.model.command.MoveCommand;
import it.polimi.ingsw.model.command.SelectShootActionCommand;

import java.util.ArrayList;
import java.util.List;

public class SelectedAggregateActionState extends AfterSelectedAggregateActionState {
    public SelectedAggregateActionState(AggregateAction selectedAggregateAction) {
        super(selectedAggregateAction);
    }

    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        if(getSelectedAggregateAction().getMoveNumber() > 0)
            player.getAccessibleSquare(getSelectedAggregateAction().getMoveNumber()).forEach(direction -> commands.add(new MoveCommand(player, direction, this)));
        if(getSelectedAggregateAction().isGrab())
            commands.addAll(player.getPosition().getGrabCommands(player, this));
        else if(getSelectedAggregateAction().isShoot())
            commands.add(new SelectShootActionCommand(player, this));
        else
            commands.add(new DoneCommand(player, this));

        return commands;
    }
}
