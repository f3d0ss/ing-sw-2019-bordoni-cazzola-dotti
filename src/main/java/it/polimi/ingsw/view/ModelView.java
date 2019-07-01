package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PlayerId;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ModelView {
    public static final int HEIGHT = 3;
    public static final int WIDTH = 4;

    private MatchView match;
    private PlayerView me;
    private Map<PlayerId, PlayerView> enemies = new EnumMap<>(PlayerId.class);
    private Map<Color, List<WeaponView>> weaponsOnSpawn = new EnumMap<>(Color.class);
    private SquareView[][] board = new SquareView[HEIGHT][WIDTH];

    public MatchView getMatch() {
        return match;
    }

    public void setMatch(MatchView match) {
        this.match = match;
    }

    public PlayerView getMe() {
        return me;
    }

    public void setMe(PlayerView me) {
        this.me = me;
    }

    public Map<PlayerId, PlayerView> getEnemies() {
        return enemies;
    }

    public void setEnemie(PlayerId playerId, PlayerView enemie) {
        this.enemies.put(playerId, enemie);
    }

    public Map<Color, List<WeaponView>> getWeaponsOnSpawn() {
        return weaponsOnSpawn;
    }

    public List<WeaponView> getWeaponsOnSpawn(Color color) {
        return weaponsOnSpawn.get(color);
    }

    public void setWeaponsOnSpawn(Color color, List<WeaponView> weaponsOnSpawn) {
        this.weaponsOnSpawn.put(color, weaponsOnSpawn);
    }

    public SquareView[][] getBoard() {
        return board;
    }

    public void setSquareBoard(int row, int col, SquareView squareView) {
        this.board[row][col] = squareView;
    }

    public SquareView getSquareBoard(int row, int col) {
        return board[row][col];
    }

    public PlayerView getPlayerView(PlayerId key) {
        if(me.getId().playerId().equals(key.playerId()))
            return me;
        else
            return enemies.get(key);
    }
}
