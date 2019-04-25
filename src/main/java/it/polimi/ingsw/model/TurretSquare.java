package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.GrabCommand;
import it.polimi.ingsw.model.command.GrabTileCommand;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

import java.util.ArrayList;
import java.util.List;

public class TurretSquare extends Square {
    private AmmoTile ammoTile;

    public TurretSquare(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, int row, int col, AmmoTile ammoTile) {
        super(northConnection, eastConnection, southConnection, westConnection, row, col);
        this.ammoTile = ammoTile;
    }

    @Override
    public List<GrabCommand> getGrabCommands(Player player, SelectedAggregateActionState state) {
        ArrayList<GrabCommand> commands = new ArrayList<>();
        commands.add(new GrabTileCommand(player, ammoTile, this));
        return commands;
    }

    public void setAmmoTile(AmmoTile ammoTile) {
        if (this.ammoTile != null)
            throw new IllegalStateException();
        this.ammoTile = ammoTile;
    }

    public AmmoTile getAmmoTile() {
        return ammoTile;
    }

    public void remove(AmmoTile ammoTile){
        if (this.ammoTile != ammoTile)
            throw new IllegalStateException();
        this.ammoTile = null;
    }
}
