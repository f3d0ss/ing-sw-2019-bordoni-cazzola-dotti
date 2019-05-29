package it.polimi.ingsw.view;

import it.polimi.ingsw.model.command.Command;

import java.util.List;

public class VirtualView implements ViewInterface {
    @Override
    public void update(WeaponView wp) {

    }

    @Override
    public void update(SquareView sw) {

    }

    @Override
    public void update(PlayerView pw) {

    }

    @Override
    public int sendCommands(List<Command> commands, boolean undo) {
        return 0;
    }
}
