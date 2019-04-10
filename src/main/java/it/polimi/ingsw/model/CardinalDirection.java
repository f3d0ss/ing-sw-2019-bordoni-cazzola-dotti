package it.polimi.ingsw.model;

public enum CardinalDirection {
    NORTH, EAST, WEST, SOUTH;

    public CardinalDirection getOpposite(){
        if(this==NORTH) return SOUTH;
        if(this==EAST) return WEST;
        if(this==SOUTH) return NORTH;
        if(this==WEST) return EAST;
        return null;
    }
}
