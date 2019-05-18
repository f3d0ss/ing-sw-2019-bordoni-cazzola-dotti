package it.polimi.ingsw.view;

import it.polimi.ingsw.model.CardinalDirection;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Connection;

import java.util.Map;

public class TurretSquareView extends SquareView {
    private final AmmoTileView ammoTile;

    public TurretSquareView(int row, int col, Map<CardinalDirection, Connection> connection, Color color, AmmoTileView ammoTile) {
        super(row, col, connection, color);
        this.ammoTile = ammoTile;
    }
}
