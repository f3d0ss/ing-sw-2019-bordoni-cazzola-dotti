package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PlayerId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelView {
    public final static int HEIGHT = 3;
    public final static int WIDTH = 4;

    private MatchView match;
    private PlayerView me;
    private PlayerId myId;
    private Map<PlayerId, PlayerView> enemies = new HashMap<>();
    private Map<Color, List<WeaponView>> weaponsOnSpawn = new HashMap<>();
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

    public PlayerId getMyId() {
        return myId;
    }

    public void setMyId(PlayerId myId) {
        this.myId = myId;
    }

    public Map<PlayerId, PlayerView> getEnemies() {
        return enemies;
    }

    public void setEnemie(PlayerId playerId, PlayerView enemie) {
        this.enemies.put(playerId, enemie);
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
}
