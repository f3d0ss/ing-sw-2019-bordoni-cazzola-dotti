package it.polimi.ingsw.view;

import it.polimi.ingsw.model.CardinalDirection;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Connection;
import it.polimi.ingsw.model.PlayerId;

import java.util.List;
import java.util.Map;

public abstract class SquareView {
    private final int row;
    private final int col;
    private final Map<CardinalDirection, Connection> connection;
    private final Color color;
    private final List<PlayerId> hostedPlayers;
    @SuppressWarnings("squid:S0017")
    private final String type;

    SquareView(int row, int col, Map<CardinalDirection, Connection> connection, Color color, List<PlayerId> hostedPlayers, String type) {
        this.row = row;
        this.col = col;
        this.connection = connection;
        this.color = color;
        this.hostedPlayers = hostedPlayers;
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Connection getConnection(CardinalDirection dir) {
        return connection.get(dir);
    }

    public Color getColor() {
        return color;
    }

    public List<PlayerId> getHostedPlayers() {
        return hostedPlayers;
    }
}
