package it.polimi.ingsw.model;

public class AggregateAction {
    private int moveNumber;
    private boolean grab;
    private boolean shoot;
    private boolean reload;

    public AggregateAction(int moveNumber, boolean grab, boolean shoot, boolean reload) {
        this.moveNumber = moveNumber;
        this.grab = grab;
        this.shoot = shoot;
        this.reload = reload;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void decrementMoveNumbers() {
        this.moveNumber--;
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

}
