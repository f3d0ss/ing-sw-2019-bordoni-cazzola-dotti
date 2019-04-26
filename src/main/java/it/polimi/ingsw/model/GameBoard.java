package it.polimi.ingsw.model;

import java.util.*;
import java.util.stream.Collectors;

public class GameBoard {

    private static final int ROWS = 3;
    private static final int COLUMNS = 4;
    private Square[][] board = new Square[ROWS][COLUMNS];
    private Map<Color, SpawnSquare> spawns;
    private List<TurretSquare> turrets;

    //hard coded GameBoard only for player's movements test

    public GameBoard(int boardNumber) {
        turrets = new ArrayList<>();
        switch (boardNumber) {
            case 1:
                spawns = new HashMap<Color, SpawnSquare>() {{
                    put(Color.BLUE, new SpawnSquare(Connection.MAP_BORDER, Connection.MAP_BORDER, Connection.DOOR, Connection.SAME_ROOM, 0, 2, Color.BLUE));
                    put(Color.RED, new SpawnSquare(Connection.DOOR, Connection.SAME_ROOM, Connection.MAP_BORDER, Connection.MAP_BORDER, 1, 0, Color.RED));
                    put(Color.YELLOW, new SpawnSquare(Connection.SAME_ROOM, Connection.MAP_BORDER, Connection.MAP_BORDER, Connection.DOOR, 2, 3, Color.YELLOW));
                }};
                board[0][0] = new TurretSquare(Connection.MAP_BORDER, Connection.SAME_ROOM, Connection.DOOR, Connection.MAP_BORDER, 0, 0, null);
                turrets.add((TurretSquare) board[0][0]);
                board[0][1] = new TurretSquare(Connection.MAP_BORDER, Connection.SAME_ROOM, Connection.WALL, Connection.SAME_ROOM, 0, 1, null);
                turrets.add((TurretSquare) board[0][1]);
                board[0][2] = spawns.get(Color.BLUE);
                board[0][3] = null;
                board[1][0] = spawns.get(Color.RED);
                board[1][1] = new TurretSquare(Connection.WALL, Connection.SAME_ROOM, Connection.DOOR, Connection.SAME_ROOM, 1, 1, null);
                turrets.add((TurretSquare) board[1][1]);
                board[1][2] = new TurretSquare(Connection.DOOR, Connection.DOOR, Connection.WALL, Connection.SAME_ROOM, 1, 2, null);
                turrets.add((TurretSquare) board[1][2]);
                board[1][3] = new TurretSquare(Connection.MAP_BORDER, Connection.MAP_BORDER, Connection.SAME_ROOM, Connection.DOOR, 1, 3, null);
                turrets.add((TurretSquare) board[1][3]);
                board[2][0] = null;
                board[2][1] = new TurretSquare(Connection.DOOR, Connection.SAME_ROOM, Connection.MAP_BORDER, Connection.MAP_BORDER, 2, 1, null);
                turrets.add((TurretSquare) board[2][1]);
                board[2][2] = new TurretSquare(Connection.WALL, Connection.DOOR, Connection.MAP_BORDER, Connection.SAME_ROOM, 2, 2, null);
                turrets.add((TurretSquare) board[2][2]);
                board[2][3] = spawns.get(Color.YELLOW);
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
                /*spawnToColor = new HashMap<SpawnSquare, Color>() {{
                    for (Color c : Color.values()) {
                        put(colorToSpawn.get(c), c);
                    }
                }};*/
        }
    }

    /**
     * @param row is the row's value, first is 0
     * @param col is the column's value, first is 0
     * @return the square placed in specified coordinates
     * @author Bordoni
     */

    public Square getSquare(int row, int col) {
        return board[row][col];
    }

    /**
     * @param color is the color of the spawn square requested
     * @return the uniqe spawn square of that color
     * @author Bordoni
     */

    public SpawnSquare getSpawn(Color color) {
        return spawns.get(color);
    }

    public List<TurretSquare> getTurrets() {
        return turrets;
    }

    public int getHeight() {
        return ROWS;
    }

    public int getWidth() {
        return COLUMNS;
    }

    /**
     * @param current is the starting position
     * @param dir     is the direction detected
     * @return the square near to current in dir direction
     * @author Bordoni
     */

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

    /**
     * @param list     is the list passed by caller, needed for recursion
     * @param position the square which room is detected
     * @author Bordoni
     */

    private void getSameRoomSquares(List<Square> list, Square position) {
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

    public List<Square> getRoomSquares(Square square) {
        List<Square> list = new ArrayList<>();
        getSameRoomSquares(list, square);
        return list;
    }

    /**
     * @param position       starting position
     * @param maxRange       maximum distance of returned squares
     * @param minRange       minimum distance of returned squares
     * @param onlyWithPlayer excluded squares where there are no players
     * @return give the list of visible squares according to input parameters
     * @author supernivem
     */

    public List<Square> getVisibleSquares(Square position, int maxRange, int minRange, boolean onlyWithPlayer) {
        Square adjacent;
        List<Square> list = new ArrayList<>();
        List<Square> out = new ArrayList<>();
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
            if (!s.getHostedPlayers().isEmpty())
                out.add(s);
        return out;
    }

    /**
     * @param position    is the position from where get straight direction squares
     * @param maxRange    maximum distance of gotten squares
     * @param minRange    minimum distance of gotten squares
     * @param ignoreWalls specify if ignore or consider walls
     * @author supernivem
     */

    public List<Square> getCardinalDirectionSquares(Square position, int maxRange, int minRange, boolean ignoreWalls) {
        Square adjacent;
        List<Square> list = new ArrayList<>();
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

    public List<Player> getPlayersOnCardinalDirectionSquares(Player shooter, int maxRange, int minRange, boolean ignoreWalls) {
        List<Player> players = new ArrayList<>();
        getCardinalDirectionSquares(shooter.getPosition(), maxRange, minRange, ignoreWalls).stream().map(square -> square.getHostedPlayers(shooter)).forEach(players::addAll);
        return players;
    }

    /**
     * @param list        is a list passed by caller, needed to allow recursion
     * @param position    is the position from where get straight direction squares
     * @param maxRange    maximum distance of got squares
     * @param ignoreWalls specify if ignore or consider walls
     * @param dir         is the direction in which get squares
     * @author supernivem
     */

    private void getStraightSquares(List<Square> list, Square position, int maxRange, boolean ignoreWalls, CardinalDirection dir) {
        Square next;
        if (maxRange > 0 && position.getConnection(dir).isAccessible(ignoreWalls)) {
            next = this.getAdjacentSquare(position, dir);
            list.add(next);
            getStraightSquares(list, next, maxRange--, ignoreWalls, dir);
        }
    }

    //TODO: update player

    /**
     * @param position starting position
     * @param maxMoves number of steps allowed
     * @return the list of squares reachable in at most maxMoves steps
     * @author supernivem
     */

    public List<Square> getReachableSquare(Square position, int maxMoves) {
        List<Square> list = new ArrayList<>();
        getReachableSquare(position, list, maxMoves);
        return list;
    }

    /**
     * @param position
     * @param maxMoves
     * @param player
     * @return the list of squares reachable in at most maxMoves steps with at least another player on
     */
    public List<Square> getReachableSquaresWithOtherPlayers(Square position, int maxMoves, Player player) {
        List<Square> reachableSquares = getReachableSquare(position, maxMoves);
        List<Square> squaresWithOtherPlayers = new ArrayList<>();
        for (Square square : reachableSquares)
            if (square.hasOtherPlayers(player))
                squaresWithOtherPlayers.add(square);
        return squaresWithOtherPlayers;
    }

    /**
     * @param squares
     * @param maxMoves
     * @param player
     * @return
     */
    public List<Square> getReachableSquaresWithOtherPlayers(List<Square> squares, int maxMoves, Player player) {
        List<Square> reachableSquares = new ArrayList<>();
        for (Square square : squares)
            reachableSquares.addAll(getReachableSquaresWithOtherPlayers(square, maxMoves, player));
        return reachableSquares.stream().distinct().collect(Collectors.toList());
    }

    /**
     * @param position starting position
     * @param maxMoves number of steps allowed
     * @param list     is a list passed by caller, needed to allow recursion
     * @author supernivem
     */

    private void getReachableSquare(Square position, List<Square> list, int maxMoves) {
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

    /**
     * @param shooter
     * @param maxDistance
     * @param minDistance
     * @return
     */
    public List<Player> getVisibleTargets(Player shooter, int maxDistance, int minDistance) {
        List<Player> targets = new ArrayList<>();
        getVisibleSquares(shooter.getPosition(), maxDistance, minDistance, true).stream().map(s -> s.getHostedPlayers(shooter)).forEach(targets::addAll);
        return targets;
    }

    /**
     * @param position current position
     * @return a list of accessible direction
     * @author supernivem
     */

    public List<CardinalDirection> getAccessibleDirection(Square position) {
        List<CardinalDirection> dir = new ArrayList<>();
        for (CardinalDirection c : CardinalDirection.values())
            if (position.getConnection(c).isAccessible(false))
                dir.add(c);
        return dir;
    }

    /**
     * This method returns the players on reachable squares
     *
     * @param position
     * @param maxMoves max distance of player
     * @param player   player to exclude
     * @return list of other players on reachable squares
     */
    public List<Player> getOtherPlayersOnReachableSquares(Square position, int maxMoves, Player player) {
        List<Square> reachableSquares = getReachableSquare(position, maxMoves);
        List<Player> players = new ArrayList<>();
        for (Square square : reachableSquares)
            if (square.hasOtherPlayers(player))
                players.addAll(square.getHostedPlayers(player));
        return players.stream().distinct().collect(Collectors.toList());
    }

    /**
     * This method returns the third square reachable in the same direction
     *
     * @param firstSquare
     * @param secondSquare
     * @return
     */
    public Square getThirdSquareInTheSameDirection(Square firstSquare, Square secondSquare) {
        if (firstSquare.getRow() == secondSquare.getRow()) {
            if (firstSquare.getCol() < secondSquare.getCol() && secondSquare.getConnection(CardinalDirection.WEST).isAccessible(false))
                return board[firstSquare.getRow()][secondSquare.getCol() + 1];
            if (secondSquare.getConnection(CardinalDirection.EAST).isAccessible(false))
                return board[firstSquare.getRow()][secondSquare.getCol() - 1];
        }
        if (firstSquare.getCol() == secondSquare.getCol()) {
            if (firstSquare.getRow() < secondSquare.getRow() && secondSquare.getConnection(CardinalDirection.SOUTH).isAccessible(false))
                return board[secondSquare.getRow() + 1][firstSquare.getCol()];
            if (secondSquare.getConnection(CardinalDirection.NORTH).isAccessible(false))
                return board[secondSquare.getRow() - 1][firstSquare.getCol()];
        }
        return null;
    }

    /**
     * This method returns all other players in the same direction (based on shooter position and on targetPlayersToExclude players position)
     *
     * @param shooter
     * @param targetPlayersToExclude
     * @param maxTargetDistance
     * @param minTargetDistance
     * @param ignoreWalls
     * @return
     */
    public List<Player> getPlayersInTheSameDirection(Player shooter, List<Player> targetPlayersToExclude, int maxTargetDistance, int minTargetDistance, boolean ignoreWalls) {
        List<Player> players = new ArrayList<>();
        CardinalDirection direction = getCardinalDirection(shooter.getPosition(), targetPlayersToExclude.get(0).getPosition());
        List<Square> squaresInADirectionIgnoringWalls = getSquaresInADirection(shooter.getPosition(), direction, maxTargetDistance, minTargetDistance, ignoreWalls);
        List<Player> playersToExclude = new ArrayList<>(targetPlayersToExclude);
        playersToExclude.add(shooter);
        for (Square square : squaresInADirectionIgnoringWalls) {
            players.addAll(square.getHostedPlayers(playersToExclude));
        }
        return players;
    }

    private CardinalDirection getCardinalDirection(Square origin, Square target) {
        if (origin.getRow() == target.getRow()) {
            if (origin.getCol() <= target.getCol())
                return CardinalDirection.WEST;
            return CardinalDirection.EAST;
        }
        if (origin.getCol() == target.getCol()) {
            if (origin.getRow() <= target.getRow())
                return CardinalDirection.SOUTH;
            return CardinalDirection.NORTH;
        }

        return null;
    }

    private List<Square> getSquaresInADirection(Square origin, CardinalDirection direction, int maxTargetDistance, int minTargetDistance, boolean ignoreWalls) {
        List<Square> squares = new ArrayList<>();
        Square square = origin;
        if (minTargetDistance > 0) {
            for (int i = 0; i < minTargetDistance; i++)
                if (square.getConnection(direction).isAccessible(ignoreWalls))
                    square = getAdjacentSquare(square, direction);
        } else
            squares.add(origin);
        int cont = minTargetDistance;
        while (square.getConnection(direction).isAccessible(ignoreWalls) && cont < maxTargetDistance) {
            square = getAdjacentSquare(square, direction);
            squares.add(square);
            cont++;
        }
        return squares;
    }
}
