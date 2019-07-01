package it.polimi.ingsw.model;

/**
 * Contains all possible connections between two {@link Square}s
 */
public enum Connection {
    MAP_BORDER, DOOR, WALL, SAME_ROOM;

    public boolean isAccessible(boolean ignoreWall) {
        if (this == SAME_ROOM || this == DOOR)
            return true;
        if (this == MAP_BORDER)
            return false;
        return ignoreWall && this == WALL;
    }

    public boolean isDoor() {
        return this == DOOR;
    }
}
