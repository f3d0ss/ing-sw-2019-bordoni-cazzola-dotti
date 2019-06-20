package it.polimi.ingsw.view;

import it.polimi.ingsw.model.CardinalDirection;
import it.polimi.ingsw.model.Connection;
import it.polimi.ingsw.model.PlayerId;

import java.util.List;
import java.util.Map;

public class TurretSquareView extends SquareView {
    private final AmmoTileView ammoTile;

    public TurretSquareView(int row, int col, Map<CardinalDirection, Connection> connection, AmmoTileView ammoTile, List<PlayerId> hostedPlayers) {
        super(row, col, connection, null, hostedPlayers);
        this.ammoTile = ammoTile;
    }

    public AmmoTileView getAmmoTile() {
        return ammoTile;
    }
}
