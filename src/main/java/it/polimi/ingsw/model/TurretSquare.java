package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.GrabCommand;
import it.polimi.ingsw.model.command.GrabTileCommand;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;
import it.polimi.ingsw.view.AmmoTileView;
import it.polimi.ingsw.view.TurretSquareView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TurretSquare extends Square {
    private AmmoTile ammoTile;

    public TurretSquare(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, int row, int col) {
        super(northConnection, eastConnection, southConnection, westConnection, row, col, null, "turret");
    }

    @Override
    protected void update() {
        match.getVirtualViews().forEach(viewInterface -> viewInterface.update(new TurretSquareView(getRow(), getCol(), getConnections(), new AmmoTileView(ammoTile.getPowerUp(), ammoTile.getAmmo()), getHostedPlayers().stream().map(Player::getId).collect(Collectors.toList()))));
    }

    @Override
    public List<GrabCommand> getGrabCommands(Player player, SelectedAggregateActionState state) {
        ArrayList<GrabCommand> commands = new ArrayList<>();
        commands.add(new GrabTileCommand(player, ammoTile, this));
        return commands;
    }

    public AmmoTile getAmmoTile() {
        return ammoTile;
    }

    public void setAmmoTile(AmmoTile ammoTile) {
        if (this.ammoTile != null)
            throw new IllegalStateException();
        this.ammoTile = ammoTile;
        update();
    }

    public void remove(AmmoTile ammoTile) {
        if (this.ammoTile != ammoTile)
            throw new IllegalStateException();
        this.ammoTile = null;
        update();
    }
}
