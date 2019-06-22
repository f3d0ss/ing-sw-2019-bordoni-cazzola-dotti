package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.network.client.Ui;
import it.polimi.ingsw.view.commandmessage.CommandMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcreteView implements ViewInterface {

    public final static int HEIGHT = 3;
    public final static int WIDTH = 4;
    private SquareView[][] board = new SquareView[HEIGHT][WIDTH];
    private Ui ui;
    private MatchView match;
    private PlayerView me;
    private PlayerId myId;
    private Map<PlayerId, PlayerView> enemies = new HashMap<>();

    public ConcreteView(Ui ui) {
        this.ui = ui;
    }

    public void setMyId(PlayerId myId) {
        this.myId = myId;
    }

    public Map<Color, List<WeaponView>> getWeaponsOnSpawn() {
        Map<Color, List<WeaponView>> weaponsOnSpawn = new HashMap<>();
        weaponsOnSpawn.put(Color.BLUE, ((SpawnSquareView)board[0][2]).getWeapons());
        weaponsOnSpawn.put(Color.RED, ((SpawnSquareView)board[1][0]).getWeapons());
        weaponsOnSpawn.put(Color.YELLOW, ((SpawnSquareView)board[2][3]).getWeapons());
        return weaponsOnSpawn;
    }

    public SquareView getSquare(int i, int j){
        return board[i][j];
    }

    public Map<PlayerId, PlayerView> getEnemies() {
        return enemies;
    }

    public MatchView getMatch() {
        return match;
    }

    public PlayerView getMe() {
        return me;
    }

    @Override
    public void update(MatchView mw) {
        match = mw;
    }

    @Override
    public void update(SquareView sw) {
        int row = sw.getRow();
        int col = sw.getCol();
        board[row][col] = sw;
        if (sw.getColor() != null)
            match.updateSpawn(sw.getColor(), ((SpawnSquareView) sw).getWeapons());
        ui.refreshView(this);
    }

    @Override
    public void update(PlayerView pw) {
        if (pw.getId() == myId)
            me = pw;
        else
            enemies.put(pw.getId(), pw);
    }

    @Override
    public int sendCommands(List<CommandMessage> commands, boolean undo) {
        return ui.manageCommandChoice(commands, undo);
    }
}
