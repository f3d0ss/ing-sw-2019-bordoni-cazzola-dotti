package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.GrabCommand;
import it.polimi.ingsw.model.command.GrabTileCommand;

import java.util.ArrayList;
import java.util.List;

public class TurretSquare extends Square {
    private AmmoTile ammoTile;

    public TurretSquare(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, int row, int col, AmmoTile ammoTile) {
        super(northConnection, eastConnection, southConnection, westConnection, row, col);
        this.ammoTile = ammoTile;
    }

    @Override
    public List<GrabCommand> getGrabCommands(Player player) {
        ArrayList<GrabCommand> temp = new ArrayList<>();
        temp.add(new GrabTileCommand(player, ammoTile));
        return temp;
    }

}
