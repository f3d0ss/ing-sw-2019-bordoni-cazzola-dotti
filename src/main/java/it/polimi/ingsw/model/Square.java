package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.GrabCommand;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Square {
    private Map<CardinalDirection, Connection> connection;
    private int row;
    private int col;
    private Color color;
    private ArrayList<Player> hostedPlayers;

    public Square(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, int row, int col, Color color) {
        connection = new HashMap<>();
        connection.put(CardinalDirection.NORTH, northConnection);
        connection.put(CardinalDirection.EAST, eastConnection);
        connection.put(CardinalDirection.SOUTH, southConnection);
        connection.put(CardinalDirection.WEST, westConnection);
        this.row = row;
        this.col = col;
        this.hostedPlayers = new ArrayList<>();
        this.color = color;
    }

    public Connection getConnection(CardinalDirection direction) {
        return connection.get(direction);
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

    public void addPlayer(Player player) {
        this.hostedPlayers.add(player);
    }

    public boolean removePlayer(Player player) {
        return this.hostedPlayers.remove(player);
    }

    public List<Player> getHostedPlayers() {
        return hostedPlayers;
    }

    public abstract List<GrabCommand> getGrabCommands(Player player, SelectedAggregateActionState state);

    /**
     * This method returns true if the square hosts at least another player
     *
     * @param player player to exclude
     * @return true if there is at least another player
     */
    public boolean hasOtherPlayers(Player player) {
        ArrayList<Player> players = new ArrayList<>(hostedPlayers);
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
