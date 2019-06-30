package it.polimi.ingsw.model;

/**
 * This class represents an aggregate action.
 */
public class AggregateAction {
    private final int moveNumber;
    private final boolean grab;
    private final boolean shoot;
    private final boolean reload;
    private boolean moved;

    public AggregateAction(int moveNumber, boolean grab, boolean shoot, boolean reload) {
        this.moveNumber = moveNumber;
        this.grab = grab;
        this.shoot = shoot;
        this.reload = reload;
        moved = false;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void useMovements() {
        moved = true;
    }

    public void resetMoves() {
        moved = false;
    }

    public boolean isGrab() {
        return grab;
    }

    public boolean isShoot() {
        return shoot;
    }

    public boolean isReload() {
        return reload;
    }

    public boolean hasMoved() {
        return moved;
    }
}
