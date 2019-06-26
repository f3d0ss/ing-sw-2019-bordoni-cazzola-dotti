package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.GrabCommand;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;
import it.polimi.ingsw.view.SquareView;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class Square {
    private Map<CardinalDirection, Connection> connection;
    private int row;
    private int col;
    private Color color;
    private List<Player> hostedPlayers;
    private String type;
    protected Match match;

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
    public Square(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, int row, int col, Color color, String type) {
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

    public Connection getConnection(CardinalDirection direction) {
        return connection.get(direction);
    }

    protected Map<CardinalDirection, Connection> getConnections() {
        return connection;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Color getColor() {
        return color;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void addPlayer(Player player) {
        this.hostedPlayers.add(player);
        update();
    }

    public void untracedAddPlayer(Player player) {
        this.hostedPlayers.add(player);
    }

    public void removePlayer(Player player) {
        this.hostedPlayers.remove(player);
        update();
    }

    public boolean untracedRemovePlayer(Player player) {
        return this.hostedPlayers.remove(player);
    }

    public List<Player> getHostedPlayers() {
        return hostedPlayers;
    }

    public abstract List<GrabCommand> getGrabCommands(Player player, SelectedAggregateActionState state);

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
     * This method returns all players on the square but
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
     * This method returns all players on the square but
     *
     * @param playersToExclude
     * @return list of other players
     */
    public List<Player> getHostedPlayers(List<Player> playersToExclude) {
        List<Player> players = new ArrayList<>(hostedPlayers);
        for (Player p : playersToExclude)
            players.remove(p);
        return players;
    }
}
