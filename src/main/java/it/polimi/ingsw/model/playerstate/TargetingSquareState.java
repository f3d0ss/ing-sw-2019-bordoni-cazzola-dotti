package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Square;

public interface TargetingSquareState {
    void addTargetSquare(Square player);

    void removeTargetSquare(Square player);
}
