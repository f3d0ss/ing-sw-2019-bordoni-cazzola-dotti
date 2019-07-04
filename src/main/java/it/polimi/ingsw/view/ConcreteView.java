package it.polimi.ingsw.view;

import it.polimi.ingsw.network.client.Ui;
import it.polimi.ingsw.view.commandmessage.CommandMessage;

import java.util.List;

public class ConcreteView implements ViewInterface {


    private static final int ASCII_A_CODE = 65;
    private static final int FIRST_ROW_NUMBER = 1;
    private Ui ui;
    private ModelView modelView;

    public ConcreteView(Ui ui) {
        this.ui = ui;
        ui.setViewInitializationUndone();
        modelView = new ModelView();
    }

    @Override
    public void update(MatchView mw) {
        modelView.setMatch(mw);
        if (ui.isViewInitializationDone())
            ui.refreshView(modelView);
        if (mw.getLeaderBoard() != null)
            ui.showLeaderBoard(mw.getLeaderBoard());
    }

    @Override
    public void update(SquareView sw) {
        int row = sw.getRow();
        int col = sw.getCol();
        modelView.setSquareBoard(row, col, sw);
        if (sw.getColor() != null)
            modelView.setWeaponsOnSpawn(sw.getColor(), ((SpawnSquareView) sw).getWeapons());
        if (ui.isViewInitializationDone())
            ui.refreshView(modelView);
    }

    @Override
    public void update(PlayerView pw) {
        if (pw.isMe())
            modelView.setMe(pw);
        else
            modelView.setEnemie(pw.getId(), pw);
        if (ui.isViewInitializationDone())
            ui.refreshView(modelView);
    }

    @Override
    public void setViewInitializationDone() {
        ui.setViewInitializationDone(modelView);
    }

    @Override
    public int sendCommands(List<CommandMessage> commands, boolean undo) {
        if (ui.isViewInitializationDone())
            ui.refreshView(modelView);
        return ui.manageCommandChoice(commands, undo);
    }

    public static String getHorizontalCoordinateName(int row) {
        return "" + (char) (row + ASCII_A_CODE);
    }

    public static String getVerticalCoordinateName(int column) {
        return String.valueOf(column + FIRST_ROW_NUMBER);
    }
}
