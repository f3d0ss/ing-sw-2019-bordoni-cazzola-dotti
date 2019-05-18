package it.polimi.ingsw.view;

import it.polimi.ingsw.model.command.Command;

import java.util.List;

public interface ViewInterface {
    void update(WeaponView wp);
    void update(SquareView sw);
    void update(PlayerView pw);
    int sendCommands(List<Command> commands);
}
