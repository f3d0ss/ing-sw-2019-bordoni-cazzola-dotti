package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.GrabCommand;
import it.polimi.ingsw.model.command.GrabTileCommand;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;
import it.polimi.ingsw.view.AmmoTileView;
import it.polimi.ingsw.view.SquareView;
import it.polimi.ingsw.view.TurretSquareView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a non-spawn location
 */
public class TurretSquare extends Square {
    private AmmoTile ammoTile;

    public TurretSquare(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, int row, int col) {
        super(northConnection, eastConnection, southConnection, westConnection, row, col, null, "turret");
    }

    @Override
    protected void update() {
        match.getAllVirtualViews().forEach(viewInterface -> viewInterface.update(getSquareView()));
    }

    @Override
    public List<GrabCommand> getGrabCommands(Player player, SelectedAggregateActionState state) {
        ArrayList<GrabCommand> commands = new ArrayList<>();
        if (ammoTile != null)
            commands.add(new GrabTileCommand(player, ammoTile, this));
        return commands;
    }

    @Override
    protected SquareView getSquareView() {
        AmmoTileView ammoTileView;
        if (ammoTile == null)
            ammoTileView = new AmmoTileView(0, null);
        else
            ammoTileView = new AmmoTileView(ammoTile.getPowerUp(), ammoTile.getAmmo());
        return new TurretSquareView(getRow(), getCol(), getConnections(), ammoTileView, getHostedPlayers().stream().map(Player::getId).collect(Collectors.toList()));
    }

    /**
     * Gets ammo tile on the square
     *
     * @return ammotile contained
     */
    public AmmoTile getAmmoTile() {
        return ammoTile;
    }

    /**
     * Adds ammotile to the square
     *
     * @param ammoTile ammotile to add
     */
    public void setAmmoTile(AmmoTile ammoTile) {
        if (this.ammoTile != null)
            throw new IllegalStateException();
        this.ammoTile = ammoTile;
        update();
    }

    /**
     * Removes an ammotile from the square
     *
     * @param ammoTile ammotile to remove
     */
    public void remove(AmmoTile ammoTile) {
        if (this.ammoTile != ammoTile)
            throw new IllegalStateException();
        this.ammoTile = null;
        update();
    }
}
