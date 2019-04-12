package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.command.Command;

import java.util.List;

public class ShootedState implements PlayerState{

    @Override
    public List<Command> getPossibleCommands(Player player) {
        return null;
    }
}
