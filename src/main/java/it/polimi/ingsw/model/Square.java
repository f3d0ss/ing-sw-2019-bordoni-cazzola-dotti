package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.GrabCommand;

import java.util.ArrayList;
import java.util.List;

abstract public class Square {
    private Connection northConnection;
    private Connection eastConnection;
    private Connection southConnection;
    private Connection westConnection;
    private int row;
    private int col;
    private ArrayList<Player> hostedPlayers;

    public Square(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, int row, int col, ArrayList<Player> hostedPlayers) {
        this.northConnection = northConnection;
        this.eastConnection = eastConnection;
        this.southConnection = southConnection;
        this.westConnection = westConnection;
        this.row = row;
        this.col = col;
        this.hostedPlayers = hostedPlayers;
    }

    public Connection getNorthConnection() {
        return northConnection;
    }

    public void setNorthConnection(Connection northConnection) {
        this.northConnection = northConnection;
    }

    public Connection getEastConnection() {
        return eastConnection;
    }

    public void setEastConnection(Connection eastConnection) {
        this.eastConnection = eastConnection;
    }

    public Connection getSouthConnection() {
        return southConnection;
    }

    public void setSouthConnection(Connection southConnection) {
        this.southConnection = southConnection;
    }

    public Connection getWestConnection() {
        return westConnection;
    }

    public void setWestConnection(Connection westConnection) {
        this.westConnection = westConnection;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public abstract List<GrabCommand> getGrabCommands(Player player);
}
