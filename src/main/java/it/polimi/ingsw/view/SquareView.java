package it.polimi.ingsw.view;

import it.polimi.ingsw.model.CardinalDirection;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Connection;
import it.polimi.ingsw.model.PlayerId;

import java.util.List;
import java.util.Map;

/**
 * This class represent an {@link it.polimi.ingsw.model.Square} on view side.
 */
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

    /**
     * Gets the row of the square.
     *
     * @return the number of the square's row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column of the square.
     *
     * @return the number of the square's column
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets the connection on a given direction.
     *
     * @param dir is the direction
     * @return the connection on that direction
     */
    public Connection getConnection(CardinalDirection dir) {
        return connection.get(dir);
    }

    /**
     * Gets the color of the square.
     *
     * @return the color of the square. If could be null if the square has no color,
     * for example if it is a turret square
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the list of the players hosted on this square.
     *
     * @return the list of the players hosted
     */
    public List<PlayerId> getHostedPlayers() {
        return hostedPlayers;
    }
}
