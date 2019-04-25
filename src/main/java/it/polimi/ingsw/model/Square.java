package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.GrabCommand;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

import java.util.ArrayList;
import java.util.List;

public abstract class Square {
    private Connection northConnection;
    private Connection eastConnection;
    private Connection southConnection;
    private Connection westConnection;
    private int row;
    private int col;
    private ArrayList<Player> hostedPlayers;

    public Square(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, int row, int col) {
        this.northConnection = northConnection;
        this.eastConnection = eastConnection;
        this.southConnection = southConnection;
        this.westConnection = westConnection;
        this.row = row;
        this.col = col;
        this.hostedPlayers = new ArrayList<>();
    }

    public Connection getConnection(CardinalDirection direction) {
        switch (direction) {
            case NORTH:
                return northConnection;
            case EAST:
                return eastConnection;
            case SOUTH:
                return southConnection;
            case WEST:
                return westConnection;
        }
        return null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
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
            if (players.contains(p))
                players.remove(p);
        return players;
    }
}
