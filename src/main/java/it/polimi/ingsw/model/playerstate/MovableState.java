package it.polimi.ingsw.model.playerstate;

public interface MovableState extends PlayerState{
    void useMoves();
    void resetMoves();
}
