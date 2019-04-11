package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {

    private final static int ROWS = 3;
    private final static int COLUMNS = 4;
    private Square[][] board = new Square[ROWS][COLUMNS];
    private Map<Color,SpawnSquare> spawn;

    //hard coded GameBoard only for player's movements test

    public GameBoard(int boardNumber){
        switch (boardNumber) {
            case 1:
                spawn = new HashMap<Color, SpawnSquare>() {{
                    put(Color.BLUE, new SpawnSquare(Connection.MAP_BORDER, Connection.MAP_BORDER, Connection.DOOR, Connection.SAME_ROOM, 0, 2, null));
                    put(Color.RED, new SpawnSquare(Connection.DOOR, Connection.SAME_ROOM, Connection.MAP_BORDER, Connection.MAP_BORDER, 1, 0, null));
                    put(Color.YELLOW, new SpawnSquare(Connection.SAME_ROOM, Connection.MAP_BORDER, Connection.MAP_BORDER, Connection.DOOR, 2, 3, null));
                }};
                board[0][0] = new TurretSquare(Connection.MAP_BORDER, Connection.SAME_ROOM, Connection.DOOR, Connection.MAP_BORDER, 0, 0, null);
                board[0][1] = new TurretSquare(Connection.MAP_BORDER, Connection.SAME_ROOM, Connection.WALL, Connection.SAME_ROOM, 0, 1, null);
                board[0][2] = spawn.get(Color.BLUE);
                board[0][3] = null;
                board[1][0] = spawn.get(Color.RED);
                board[1][1] = new TurretSquare(Connection.WALL, Connection.SAME_ROOM, Connection.DOOR, Connection.SAME_ROOM, 1, 1, null);
                board[1][2] = new TurretSquare(Connection.DOOR, Connection.DOOR, Connection.WALL, Connection.SAME_ROOM, 1, 2, null);
                board[1][3] = new TurretSquare(Connection.MAP_BORDER, Connection.MAP_BORDER, Connection.SAME_ROOM, Connection.DOOR, 1, 3, null);
                board[2][0] = null;
                board[2][1] = new TurretSquare(Connection.DOOR, Connection.SAME_ROOM, Connection.MAP_BORDER, Connection.MAP_BORDER, 2, 1, null);
                board[2][2] = new TurretSquare(Connection.WALL, Connection.DOOR, Connection.MAP_BORDER, Connection.SAME_ROOM, 2, 2, null);
                board[2][3] = spawn.get(Color.YELLOW);
/*            default:
                board[0][0] = new TurretSquare(Connection.MAP_BORDER, Connection.SAME_ROOM, Connection.DOOR, Connection.MAP_BORDER, 0, 0, null);
                board[0][1] = new TurretSquare(Connection.MAP_BORDER, Connection.SAME_ROOM, Connection.WALL, Connection.SAME_ROOM, 0, 1, null);
                board[0][2] = new SpawnSquare(Connection.MAP_BORDER, Connection.DOOR, Connection.DOOR, Connection.SAME_ROOM, 0, 2, null);
                board[0][3] = new SpawnSquare(Connection.MAP_BORDER, Connection.MAP_BORDER, Connection.DOOR, Connection.DOOR, 0, 3, null);
                board[1][0] = new SpawnSquare(Connection.DOOR, Connection.SAME_ROOM, Connection.MAP_BORDER, Connection.MAP_BORDER, 1, 0, null);
                board[1][1] = new TurretSquare(Connection.WALL, Connection.WALL, Connection.DOOR, Connection.SAME_ROOM, 1, 1, null);
                board[1][2] = new TurretSquare(Connection.DOOR, Connection.SAME_ROOM, Connection.SAME_ROOM, Connection.WALL, 1, 2, null);
                board[1][3] = new TurretSquare(Connection.DOOR, Connection.MAP_BORDER, Connection.SAME_ROOM, Connection.SAME_ROOM, 1, 3, null);
                board[2][0] = null;
                board[2][1] = new TurretSquare(Connection.DOOR, Connection.DOOR, Connection.MAP_BORDER, Connection.MAP_BORDER, 2, 1, null);
                board[2][2] = new TurretSquare(Connection.SAME_ROOM, Connection.SAME_ROOM, Connection.MAP_BORDER, Connection.DOOR, 2, 2, null);
                board[2][3] = new SpawnSquare(Connection.SAME_ROOM, Connection.MAP_BORDER, Connection.MAP_BORDER, Connection.SAME_ROOM, 2, 3, null);
*/
//board 3 and 4 missing
        }
    }

    public Square getSquare(int row, int col){
        return board[row][col];
    }
}
