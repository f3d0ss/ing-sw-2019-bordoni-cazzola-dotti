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

    /**
     * @return number of move possible
     */
    public int getMoveNumber() {
        return moveNumber;
    }

    /**
     * Sets that player has moved
     */
    public void useMovements() {
        moved = true;
    }

    /**
     * Reset player moves
     */
    public void resetMoves() {
        moved = false;
    }

    /**
     * @return true if player can grab
     */
    public boolean isGrab() {
        return grab;
    }

    /**
     * @return true if player can shoot
     */
    public boolean isShoot() {
        return shoot;
    }

    /**
     * @return true if player can reload
     */
    public boolean isReload() {
        return reload;
    }

    /**
     * @return true if player has moved
     */
    public boolean hasMoved() {
        return moved;
    }
}
