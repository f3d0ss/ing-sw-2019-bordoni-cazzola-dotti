package it.polimi.ingsw.view;

import it.polimi.ingsw.model.CardinalDirection;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Connection;

import java.util.Map;

public abstract class SquareView {
    private final int row;
    private final int col;
    private final Map<CardinalDirection, Connection> connection;
    private final Color color;

    public SquareView(int row, int col, Map<CardinalDirection, Connection> connection, Color color) {
        this.row = row;
        this.col = col;
        this.connection = connection;
        this.color = color;
    }
}
