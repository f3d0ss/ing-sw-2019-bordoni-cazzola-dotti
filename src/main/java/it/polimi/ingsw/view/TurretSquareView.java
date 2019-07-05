package it.polimi.ingsw.view;

import it.polimi.ingsw.model.CardinalDirection;
import it.polimi.ingsw.model.Connection;
import it.polimi.ingsw.model.PlayerId;

import java.util.List;
import java.util.Map;

/**
 * This class represent an {@link it.polimi.ingsw.model.TurretSquare} on view side.
 */
public class TurretSquareView extends SquareView {
    private final AmmoTileView ammoTile;

    public TurretSquareView(int row, int col, Map<CardinalDirection, Connection> connection, AmmoTileView ammoTile, List<PlayerId> hostedPlayers) {
        super(row, col, connection, null, hostedPlayers, "turret");
        this.ammoTile = ammoTile;
    }

    /**
     * Gets the ammotile laying on the square.
     *
     * @return the ammotile laying on the square
     */
    public AmmoTileView getAmmoTile() {
        return ammoTile;
    }
}
