package it.polimi.ingsw.model;

public enum CardinalDirection {
    NORTH, EAST, WEST, SOUTH;

    public CardinalDirection getOpposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            default:
                return EAST;

        }
    }
}
