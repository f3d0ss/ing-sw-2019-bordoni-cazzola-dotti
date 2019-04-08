package it.polimi.ingsw;

import java.util.Map;

public class GameBoard {

    final int ROWS = 3;
    final int COLUMNS = 4;
    private Square[][] board = new Square[ROWS][COLUMNS];
    private Map<Color,SpawnSquare> spawn;

    public Square getSquare(int row, int col){
        return board[row][col];
    }
}
