package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.GrabCommand;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;
import it.polimi.ingsw.view.SquareView;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a square of the game board
 */
public abstract class Square {
    private final Map<CardinalDirection, Connection> connection;
    private final int row;
    private final int col;
    private final Color color;
    Match match;
    private List<Player> hostedPlayers;
    @SuppressWarnings("squid:S1068")
    private String type;

    /**
     * @param northConnection is the north connection
     * @param eastConnection  is the east connection
     * @param southConnection is the south connection
     * @param westConnection  is the west connection
     * @param row             is the row of the square
     * @param col             is the column of the square
     * @param color           is the color of the square
     * @param type            is the type of the square used for deserialization
     */
    @SuppressWarnings("squid:S00107")
    Square(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, int row, int col, Color color, String type) {
        this.type = type;
        connection = new EnumMap<>(CardinalDirection.class);
        connection.put(CardinalDirection.NORTH, northConnection);
        connection.put(CardinalDirection.EAST, eastConnection);
        connection.put(CardinalDirection.SOUTH, southConnection);
        connection.put(CardinalDirection.WEST, westConnection);
        this.row = row;
        this.col = col;
        this.hostedPlayers = new ArrayList<>();
        this.color = color;
    }

    protected abstract void update();

    /**
     * Gets connection in a specific direction
     *
     * @param direction Direction to get the connection
     * @return connection in the specified direction
     */
    public Connection getConnection(CardinalDirection direction) {
        return connection.get(direction);
    }

    /**
     * Gets connections
     *
     * @return all the connections of the square
     */
    public Map<CardinalDirection, Connection> getConnections() {
        return connection;
    }

    /**
     * Gets row
     *
     * @return value of row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets col
     *
     * @return value of col
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets color
     *
     * @return value of color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the match
     *
     * @param match match to set
     */
    public void setMatch(Match match) {
        this.match = match;
    }

    /**
     * Adds a player to the square
     *
     * @param player player to add
     */
    public void addPlayer(Player player) {
        this.hostedPlayers.add(player);
        update();
    }

    /**
     * This method adds a player without notifying observers
     *
     * @param player Player to add
     */
    public void untracedAddPlayer(Player player) {
        this.hostedPlayers.add(player);
    }

    /**
     * Removes a player
     *
     * @param player player to remove
     */
    public void removePlayer(Player player) {
        this.hostedPlayers.remove(player);
        update();
    }

    /**
     * This method removes a player without notifying observers
     *
     * @param player Player to remove
     */
    public void untracedRemovePlayer(Player player) {
        this.hostedPlayers.remove(player);
    }

    /**
     * Gets hosted players
     *
     * @return list of the player hosted
     */
    public List<Player> getHostedPlayers() {
        return hostedPlayers;
    }

    /**
     * Gets the possible Grab Commands
     *
     * @param player player that wants to grab
     * @param state  state of the player
     * @return list of the possible commands
     */
    public abstract List<GrabCommand> getGrabCommands(Player player, SelectedAggregateActionState state);

    /**
     * @return view of the square
     */
    protected abstract SquareView getSquareView();

    /**
     * This method returns true if the square hosts at least another player
     *
     * @param player player to exclude
     * @return true if there is at least another player
     */
    public boolean hasOtherPlayers(Player player) {
        List<Player> players = new ArrayList<>(hostedPlayers);
        players.remove(player);
        return !players.isEmpty();
    }

    /**
     * This method returns all players on the square but {@code player}
     *
     * @param player player not to return
     * @return list of other players
     */
    public List<Player> getHostedPlayers(Player player) {
        List<Player> players = new ArrayList<>(hostedPlayers);
        if (getHostedPlayers().contains(player)) players.remove(player);
        return players;
    }

    /**
     * This method returns all players on the square but {@code playersToExclude}
     *
     * @param playersToExclude list of players to exclude
     * @return list of other players
     */
    public List<Player> getHostedPlayers(List<Player> playersToExclude) {
        List<Player> players = new ArrayList<>(hostedPlayers);
        for (Player p : playersToExclude)
            players.remove(p);
        return players;
    }
}
