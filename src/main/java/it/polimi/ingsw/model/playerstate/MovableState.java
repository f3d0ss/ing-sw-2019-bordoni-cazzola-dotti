package it.polimi.ingsw.model.playerstate;

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
