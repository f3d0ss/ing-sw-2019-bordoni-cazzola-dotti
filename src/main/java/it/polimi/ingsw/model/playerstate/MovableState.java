package it.polimi.ingsw.model.playerstate;

/**
 * States when the player can move
 */

public interface MovableState extends PlayerState {
    /**
     * Moves the player
     */
    void useMoves();

    /**
     * Resets the player's move
     */
    void resetMoves();
}
