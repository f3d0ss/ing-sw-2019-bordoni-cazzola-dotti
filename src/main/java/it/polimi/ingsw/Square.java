package it.polimi.ingsw;

import java.util.ArrayList;

abstract public class Square {
    private Connection northConnection;
    private Connection eastConnection;
    private Connection southConnection;
    private Connection westConnection;
    private int row;
    private int col;
    private ArrayList<Player> hostedPlayers = new ArrayList<Player>();

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

//    public List<GrabCommand> getGrabCommands(player Player){
//  }
}
