
package it.polimi.ingsw.model;
public class AggregateAction {
    private final int moveNumber;
    private boolean grab;
    private boolean shoot;
    private boolean reload;
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
