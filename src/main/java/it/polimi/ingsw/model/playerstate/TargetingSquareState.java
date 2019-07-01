package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Square;

public interface TargetingSquareState {
    /**
     * Adds a target square
     *
     * @param square target to add
     */
    void addTargetSquare(Square square);

    /**
     * Removes a target square
     *
     * @param square target to remove
     */
    void removeTargetSquare(Square square);
}
