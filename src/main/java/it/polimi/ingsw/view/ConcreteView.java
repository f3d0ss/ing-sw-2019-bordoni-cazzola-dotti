package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.network.client.Ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcreteView implements ViewInterface {

    private final static int HEIGHT = 3;
    private final static int WIDTH = 4;
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

    @Override
    public void update(MatchView mw) {
        match = mw;
    }

    @Override
    public void update(SquareView sw) {
        int row = sw.getRow();
        int col = sw.getCol();
        board[row][col] = sw;
        ui.showGame(board, match, me, enemies);
    }

    @Override
    public void update(PlayerView pw) {
        if (pw.getId() == myId)
            me = pw;
        else
            enemies.put(pw.getId(), pw);
    }

    @Override
    public int sendCommands(List<Command> commands, boolean undo) {
        return 0;
    }

    public List<WeaponView> getWeaponsOnSpawn(Color color) {
        switch (color){
            //TODO: selct the right weapons
        }
        return null;
    }
}
