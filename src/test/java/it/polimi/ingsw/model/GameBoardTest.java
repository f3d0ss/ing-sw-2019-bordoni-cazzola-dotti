package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameBoardTest {

    private final static int ROWS = 3;
    private final static int COLUMNS = 4;

    //test the correct search of visible squares - only some cases are tested

    @Test
    public void testGetVisibleSquares() {
        GameBoard board = new GameBoard(1);
        Square square;
        ArrayList<Square> list;
        for(int i = 0; i<ROWS; i++)
            for(int j = 0; j<COLUMNS; j++) {
                square = board.getSquare(i, j);
                if(square!=null) {
                    list = board.getVisibleSquares(square);
                    if (i == 0 && j == 0 || i == 1 && j == 0 || i == 0 && j == 2) {
                        assertEquals(list.contains(board.getSquare(0, 0)), true);
                        assertEquals(list.contains(board.getSquare(0, 1)), true);
                        assertEquals(list.contains(board.getSquare(0, 2)), true);
                        assertEquals(list.contains(board.getSquare(0, 3)), false);
                        assertEquals(list.contains(board.getSquare(1, 0)), true);
                        assertEquals(list.contains(board.getSquare(1, 1)), true);
                        assertEquals(list.contains(board.getSquare(1, 2)), true);
                        assertEquals(list.contains(board.getSquare(1, 3)), false);
                        assertEquals(list.contains(board.getSquare(2, 0)), false);
                        assertEquals(list.contains(board.getSquare(2, 1)), false);
                        assertEquals(list.contains(board.getSquare(2, 2)), false);
                        assertEquals(list.contains(board.getSquare(2, 3)), false);
                        assertEquals(list.size(), 6);
                    }
                    if (i == 1 && j == 1 || i == 2 && j == 1) {
                        assertEquals(list.contains(board.getSquare(0, 0)), false);
                        assertEquals(list.contains(board.getSquare(0, 1)), false);
                        assertEquals(list.contains(board.getSquare(0, 2)), false);
                        assertEquals(list.contains(board.getSquare(0, 3)), false);
                        assertEquals(list.contains(board.getSquare(1, 0)), true);
                        assertEquals(list.contains(board.getSquare(1, 1)), true);
                        assertEquals(list.contains(board.getSquare(1, 2)), true);
                        assertEquals(list.contains(board.getSquare(1, 3)), false);
                        assertEquals(list.contains(board.getSquare(2, 0)), false);
                        assertEquals(list.contains(board.getSquare(2, 1)), true);
                        assertEquals(list.contains(board.getSquare(2, 2)), true);
                        assertEquals(list.contains(board.getSquare(2, 3)), false);
                        assertEquals(list.size(), 5);
                    }
                    if (i == 2 && j == 2 || i == 2 && j == 3) {
                        assertEquals(list.contains(board.getSquare(0, 0)), false);
                        assertEquals(list.contains(board.getSquare(0, 1)), false);
                        assertEquals(list.contains(board.getSquare(0, 2)), false);
                        assertEquals(list.contains(board.getSquare(0, 3)), false);
                        assertEquals(list.contains(board.getSquare(1, 0)), false);
                        assertEquals(list.contains(board.getSquare(1, 1)), false);
                        assertEquals(list.contains(board.getSquare(1, 2)), false);
                        assertEquals(list.contains(board.getSquare(1, 3)), true);
                        assertEquals(list.contains(board.getSquare(2, 0)), false);
                        assertEquals(list.contains(board.getSquare(2, 1)), true);
                        assertEquals(list.contains(board.getSquare(2, 2)), true);
                        assertEquals(list.contains(board.getSquare(2, 3)), true);
                        assertEquals(list.size(), 4);
                    }
                    if (i == 1 && j == 2) {
                        assertEquals(list.contains(board.getSquare(0, 0)), true);
                        assertEquals(list.contains(board.getSquare(0, 1)), true);
                        assertEquals(list.contains(board.getSquare(0, 2)), true);
                        assertEquals(list.contains(board.getSquare(0, 3)), false);
                        assertEquals(list.contains(board.getSquare(1, 0)), true);
                        assertEquals(list.contains(board.getSquare(1, 1)), true);
                        assertEquals(list.contains(board.getSquare(1, 2)), true);
                        assertEquals(list.contains(board.getSquare(1, 3)), true);
                        assertEquals(list.contains(board.getSquare(2, 0)), false);
                        assertEquals(list.contains(board.getSquare(2, 1)), false);
                        assertEquals(list.contains(board.getSquare(2, 2)), false);
                        assertEquals(list.contains(board.getSquare(2, 3)), true);
                        assertEquals(list.size(), 8);
                    }
                }
            }
    }
}