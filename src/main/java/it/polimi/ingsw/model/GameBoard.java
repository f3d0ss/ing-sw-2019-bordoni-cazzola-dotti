package it.polimi.ingsw.model;

import java.util.*;

public class GameBoard {

    private final static int ROWS = 3;
    private final static int COLUMNS = 4;
    private Square[][] board = new Square[ROWS][COLUMNS];
    private Map<Color, SpawnSquare> spawn;

    //hard coded GameBoard only for player's movements test

    public GameBoard(int boardNumber) {
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

    public Square getSquare(int row, int col) {
        return board[row][col];
    }

    public SpawnSquare getSpawn(Color color) {
        return spawn.get(color);
    }

    public int getHeight() {
        return ROWS;
    }

    public int getWidth() {
        return COLUMNS;
    }

    private Square getAdjacentSquare(Square current, CardinalDirection dir) {
        switch (dir) {
            case NORTH:
                return this.getSquare(current.getRow() - 1, current.getCol());
            case EAST:
                return this.getSquare(current.getRow(), current.getCol() + 1);
            case SOUTH:
                return this.getSquare(current.getRow() + 1, current.getCol());
            case WEST:
                return this.getSquare(current.getRow(), current.getCol() - 1);
        }
        return null;
    }

    private void getSameRoomSquares(ArrayList<Square> list, Square position) {
        Square adjacent;
        for (CardinalDirection dir : CardinalDirection.values())
            if (position.getConnection(dir) == Connection.SAME_ROOM) {
                adjacent = this.getAdjacentSquare(position, dir);
                if (!list.contains(adjacent)) {
                    list.add(adjacent);
                    this.getSameRoomSquares(list, adjacent);
                }
            }
    }

    public ArrayList<Square> getVisibleSquares(Square position, int maxRange, int minRange, boolean onlyWithPlayer) {
        Square adjacent;
        ArrayList<Square> list = new ArrayList<>();
        ArrayList<Square> out = new ArrayList<>();
        list.add(position);
        if (maxRange > 0) {
            for (CardinalDirection dir : CardinalDirection.values())
                if (position.getConnection(dir).isAccessible(false)) {
                    adjacent = this.getAdjacentSquare(position, dir);
                    if (!list.contains(adjacent)) {
                        list.add(adjacent);
                        if (maxRange > 1)
                            this.getSameRoomSquares(list, adjacent);
                    }
                }
        }
        if (minRange > 0) {
            list.remove(position);
            if (minRange > 1)
                for (CardinalDirection dir : CardinalDirection.values())
                    if (position.getConnection(dir).isAccessible(false))
                        list.remove(getAdjacentSquare(position, dir));
        }
        if (!onlyWithPlayer)
            return list;
        for (Square s : list)
            if (s.getHostedPlayers().size() != 0)
                out.add(s);
        return out;
    }

    public ArrayList<Square> getCardinalDirectionSquares(Square position, int maxRange, int minRange, boolean ignoreWalls) {
        Square adjacent;
        ArrayList<Square> list = new ArrayList<>();
        if (minRange == 0)
            list.add(position);
        if (maxRange > 0) {
            for (CardinalDirection dir : CardinalDirection.values())
                if (position.getConnection(dir).isAccessible(ignoreWalls)) {
                    adjacent = this.getAdjacentSquare(position, dir);
                    list.add(adjacent);
                    if (maxRange > 1)
                        getStraightSquares(list, adjacent, maxRange--, ignoreWalls, dir);
                }
        }
        return list;
    }

    private void getStraightSquares(ArrayList<Square> list, Square position, int maxRange, boolean ignoreWalls, CardinalDirection dir) {
        Square next;
        if (maxRange > 0 && position.getConnection(dir).isAccessible(ignoreWalls)) {
            next = this.getAdjacentSquare(position, dir);
            list.add(next);
            getStraightSquares(list, next, maxRange--, ignoreWalls, dir);
        }
    }

    public void getReachableSquare(Square position, ArrayList<Square> list, int maxMoves) {
        Square adjacent;
        int furtherMove = maxMoves - 1;
        if (!list.contains(position))
            list.add(position);
        if (maxMoves > 0) {
            for (CardinalDirection dir : CardinalDirection.values())
                if (position.getConnection(dir).isAccessible(false)) {
                    adjacent = this.getAdjacentSquare(position, dir);
                    this.getReachableSquare(adjacent, list, furtherMove);
                }
        }
    }

/* to be implemented in weapon
    public ArrayList<Player> getVisibleTarget(Square origin) {
        ArrayList<Player> targets = new ArrayList<>();
        ArrayList<Square> visibles = this.getVisibleSquares(origin);
        for (Square s : visibles) {
            for (Player p : s.getHostedPlayers())
                targets.add(p);
        }
        return targets;
    }*/

    public ArrayList<CardinalDirection> getAccessibleDirection(Square position) {
        ArrayList<CardinalDirection> dir = new ArrayList<>();
        for (CardinalDirection c : CardinalDirection.values())
            if (position.getConnection(c).isAccessible(false))
                dir.add(c);
        return dir;
    }
}
