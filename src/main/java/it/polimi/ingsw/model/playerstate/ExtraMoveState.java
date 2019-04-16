package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.AggregateAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.DoneCommand;
import it.polimi.ingsw.model.command.MoveCommand;

import java.util.ArrayList;
import java.util.List;

public class ExtraMoveState extends SelectedWeaponState {
    public ExtraMoveState(AggregateAction selectedAggregateAction, Weapon selectedWeapon) {
        super(selectedAggregateAction, selectedWeapon);
    }

    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        if(!getSelectedWeapon().hasExtraMove())
            throw new IllegalStateException();
        player.getAccessibleSquare(getSelectedWeapon().getExtraMove()).forEach(square -> commands.add(new MoveCommand(player, square, this)));
        commands.add(new DoneCommand(player, this));
        return commands;
    }
}
