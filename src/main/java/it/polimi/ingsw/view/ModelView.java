package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PlayerId;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent the entire Model on view side.
 */
public class ModelView {
    public static final int HEIGHT = 3;
    public static final int WIDTH = 4;

    private MatchView match;
    private PlayerView me;
    private Map<PlayerId, PlayerView> enemies = new EnumMap<>(PlayerId.class);
    private Map<Color, List<WeaponView>> weaponsOnSpawn = new EnumMap<>(Color.class);
    private SquareView[][] board = new SquareView[HEIGHT][WIDTH];

    /**
     * Gets the last updated instance of match view.
     *
     * @return the match view
     */
    public MatchView getMatch() {
        return match;
    }

    /**
     * Sets the last updated instance of match view.
     *
     * @param match is the up-to-date instance
     */
    public void setMatch(MatchView match) {
        this.match = match;
    }

    /**
     * Gets the actual client's player.
     *
     * @return the actual client's player
     */
    public PlayerView getMe() {
        return me;
    }

    /**
     * Sets the actual client's player.
     *
     * @param me is the actual client's player
     */
    public void setMe(PlayerView me) {
        this.me = me;
    }

    /**
     * Gets the list of client's enemies.
     *
     * @return the list of client's enemies
     */
    public Map<PlayerId, PlayerView> getEnemies() {
        return enemies;
    }

    /**
     * Adds an enemy of client.
     *
     * @param playerId is the id of enemy
     * @param enemie is the player view of the enemy
     */
    public void setEnemie(PlayerId playerId, PlayerView enemie) {
        this.enemies.put(playerId, enemie);
    }

    /**
     * Gets the list of weapons laying on a given spawn.
     *
     * @param color is the color of the spawn
     * @return is the list of weapons laying on the spawn with that color
     */
    public List<WeaponView> getWeaponsOnSpawn(Color color) {
        return weaponsOnSpawn.get(color);
    }

    /**
     * Sets the list of weapons on a given spawn.
     *
     * @param color is the color of the spawn
     * @param weaponsOnSpawn is the list of weapons that are adding
     */
    public void setWeaponsOnSpawn(Color color, List<WeaponView> weaponsOnSpawn) {
        this.weaponsOnSpawn.put(color, weaponsOnSpawn);
    }

    /**
     * Gets the entire board.
     *
     * @return the entire board
     */
    public SquareView[][] getBoard() {
        return board;
    }

    /**
     * Sets one square of the board.
     *
     * @param row is the row number (starting from 0) of the square
     * @param col is the column number (starting from 0) of the square
     * @param squareView is the square to be set
     */
    public void setSquareBoard(int row, int col, SquareView squareView) {
        this.board[row][col] = squareView;
    }

    /**
     * Gets one square of the board.
     *
     * @param row is the row number (starting from 0) of the square
     * @param col is the column number (starting from 0) of the square
     * @return the square in that coordinates
     */
    public SquareView getSquareBoard(int row, int col) {
        return board[row][col];
    }

    /**
     * Gets the player given its id
     *
     * @param key is the id of the player
     * @return the player view
     */
    public PlayerView getPlayerView(PlayerId key) {
        if(me.getId().playerId().equals(key.playerId()))
            return me;
        else
            return enemies.get(key);
    }

    /**
     * Sets the player view, distinguish if is the client's player or a client's enemy.
     *
     * @param pw is the player view to be update
     */
    public void setPlayerView(PlayerView pw) {
        if (pw.isMe())
            setMe(pw);
        else
            setEnemie(pw.getId(), pw);
    }
}
