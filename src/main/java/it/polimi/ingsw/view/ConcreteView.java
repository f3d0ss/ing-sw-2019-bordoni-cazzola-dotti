package it.polimi.ingsw.view;

import it.polimi.ingsw.view.commandmessage.CommandMessage;

import java.util.List;

/**
 * This class represents the client-side view.
 */
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

    /**
     * Updates the MatchView instance.
     *
     * @param mw is the instance of MatchView to be updated
     */
    @Override
    public void update(MatchView mw) {
        modelView.setMatch(mw);
        if (ui.isViewInitializationDone())
            ui.refreshView(mw);
        if (mw.getLeaderBoard() != null)
            ui.showLeaderBoard(mw.getLeaderBoard());
    }

    /**
     * Updates the SquareView instance.
     *
     * @param sw is the instance of SquareView to be updated
     */
    @Override
    public void update(SquareView sw) {
        int row = sw.getRow();
        int col = sw.getCol();
        modelView.setSquareBoard(row, col, sw);
        if (sw.getColor() != null)
            modelView.setWeaponsOnSpawn(sw.getColor(), ((SpawnSquareView) sw).getWeapons());
        if (ui.isViewInitializationDone())
            ui.refreshView(sw);
    }

    /**
     * Updates the PlayerView instance.
     *
     * @param pw is the instance of PlayerView to be updated
     */
    @Override
    public void update(PlayerView pw) {
        modelView.setPlayerView(pw);
        if (ui.isViewInitializationDone())
            ui.refreshView(pw);
    }

    /**
     * Marks view initialization as done.
     */
    @Override
    public void setViewInitializationDone() {
        ui.setViewInitializationDone(modelView);
    }

    /**
     * Sends received commands to User Interface.
     *
     * @param commands is the list of commands
     * @param undo tells if undo command has to be added to the possible choices
     * @return the user's answer
     */
    @Override
    public int sendCommands(List<CommandMessage> commands, boolean undo) {
        return ui.manageCommandChoice(commands, undo);
    }

    /**
     * Gets the name of horizontal coordinate, as a literal
     *
     * @param row is the number of the row
     * @return a string containing the letter
     */
    public static String getHorizontalCoordinateName(int row) {
        return "" + (char) (row + ASCII_A_CODE);
    }

    /**
     * Gets the name of vertical coordinate, as a numerical
     *
     * @param column is the number of the column
     * @return a string containing the number
     */
    public static String getVerticalCoordinateName(int column) {
        return String.valueOf(column + FIRST_ROW_NUMBER);
    }
}
