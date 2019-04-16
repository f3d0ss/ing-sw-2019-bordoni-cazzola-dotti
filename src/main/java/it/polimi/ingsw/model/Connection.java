package it.polimi.ingsw.model;

public enum Connection {
    MAP_BORDER, DOOR, WALL, SAME_ROOM;

    public boolean isAccessible(boolean ignoreWall) {
        if (this == SAME_ROOM || this == DOOR)
            return true;
        if (ignoreWall && this == WALL)
            return true;
        return false;
    }
}
