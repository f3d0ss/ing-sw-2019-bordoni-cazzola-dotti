package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.view.*;
import it.polimi.ingsw.view.commandmessage.*;

import java.util.*;

/**
 * This class manage Command Line Interface as User Interface.
 */
@SuppressWarnings("squid:S106")
public class Cli implements Ui {

    private static final int FIRST_CHOICE_NUMBER = 1;
    private final Scanner stdin = new Scanner(System.in);
    private final CliManager cliManager = new CliManager();
    private boolean initializationDone = false;
    private ModelView modelView;

    /**
     * Prints a message coming from server.
     *
     * @param toBeShown        is a string represent a question or a statement
     * @param possibleAnswers  is the list of the possible answers that user can choose
     * @param isAnswerRequired specify if an answer is required (the message is a question)
     *                         or is not (the message is a notice)
     * @return the user's answer or a reception acknowledgement
     */

    public String showMessage(String toBeShown, List<String> possibleAnswers, boolean isAnswerRequired) {
        System.out.println(toBeShown);
        if (!isAnswerRequired)
            return Protocol.ACK;
        if (possibleAnswers == null)
            return stdin.nextLine();
        showPossibleAnswers(possibleAnswers);
        return possibleAnswers.get(askChoiceByNumber(possibleAnswers.size()) - FIRST_CHOICE_NUMBER);
    }

    /**
     * Does nothing: implemented only because needed by interface.
     */
    @Override
    public void run() {
        // Do nothing (intentionally-blank override)
    }

    /**
     * Refreshes view after a PlayerView instance update.
     *
     * @param pw is the PlayerView instance to be updated then shown
     */
    @Override
    public void refreshView(PlayerView pw) {
        modelView.setPlayerView(pw);
        refreshView(modelView);
    }

    /**
     * Refreshes view after a SquareView instance update.
     *
     * @param sw is the SquareView instance to be updated then shown
     */
    @Override
    public void refreshView(SquareView sw) {
        modelView.setSquareBoard(sw.getRow(), sw.getCol(), sw);
        refreshView(modelView);
    }

    /**
     * Refreshes view after a MatchView instance update.
     *
     * @param mw is the MatchView instance to be updated then shown
     */
    @Override
    public void refreshView(MatchView mw) {
        modelView.setMatch(mw);
        refreshView(modelView);
    }

    /**
     * Updates the game displayed on command line.
     *
     * @param modelView is the view-side information box from which take the current game's parameters
     */

    public void refreshView(ModelView modelView) {
        cliManager.displayAll(modelView);
    }

    /**
     * Notify that the view initialization is done updating the instance of ModelView,
     * then calls a refresh of entire view.
     *
     * @param modelView is the ModelView instance to be updated
     */
    public void setViewInitializationDone(ModelView modelView) {
        initializationDone = true;
        this.modelView = modelView;
        refreshView(modelView);
    }

    /**
     * Sets that view initialization is not already done, which means that view-side model is not ready.
     */
    public void setViewInitializationUndone() {
        initializationDone = false;
    }

    /**
     * Gets if the view initialization is done.
     *
     * @return true if the view initialization is done
     */
    public boolean isViewInitializationDone() {
        return initializationDone;
    }

    /**
     * Manages multiple options coming from server during game.
     *
     * @param commands is the list of command user can choose from
     * @param undo     specify if user can come back to the last choice
     * @return the number correspond to the user choice
     */

    public int manageCommandChoice(List<CommandMessage> commands, boolean undo) {
        if (isViewInitializationDone())
            refreshView(modelView);
        List<String> possibleAnswers = new ArrayList<>();
        commands.forEach(c -> possibleAnswers.add(c.getType().getString() + getParameter(c)));
        if (undo)
            possibleAnswers.add(CommandType.UNDO.getString());
        System.out.println("Scegli una delle seguenti opzioni:");
        showPossibleAnswers(possibleAnswers);
        return askChoiceByNumber(possibleAnswers.size()) - FIRST_CHOICE_NUMBER;
    }

    /**
     * Prints the possible answers giving them a number that user can digit in order to express his preference.
     *
     * @param possibleAnswers is the list of strings which represent the single possible answer
     */

    private void showPossibleAnswers(List<String> possibleAnswers) {
        for (int i = 0; i < possibleAnswers.size(); i++)
            System.out.println("(" + (i + FIRST_CHOICE_NUMBER) + ") " + possibleAnswers.get(i));
    }

    /**
     * Catches the user answer and checks if it is valid, requesting it again until it is not a valid answer.
     *
     * @param size of the possible answer list
     * @return the number typed by user
     */

    private int askChoiceByNumber(int size) {
        int choice;
        try {
            choice = stdin.nextInt();
        } catch (InputMismatchException e) {
            choice = FIRST_CHOICE_NUMBER - 1;
        }
        stdin.nextLine();
        while (choice >= size + FIRST_CHOICE_NUMBER || choice < FIRST_CHOICE_NUMBER) {
            System.out.println("Risposta non valida. Riprova:");
            try {
                choice = stdin.nextInt();
            } catch (InputMismatchException e) {
                choice = FIRST_CHOICE_NUMBER - 1;
            }
            stdin.nextLine();
        }
        return choice;
    }

    /**
     * Extracts specific parameters contained in the command message making them printable.
     *
     * @param command is the command from which get parameters
     * @return String to show
     */

    private String getParameter(CommandMessage command) {
        switch (command.getType()) {
            case RESPAWN:
            case SELECT_POWER_UP:
            case SELECT_POWER_UP_PAYMENT:
            case SELECT_SCOPE:
            case USE_TAGBACK_GRENADE:
                return ((PowerUpCommandMessage) command).getPowerUpID().powerUpName() + " " + ((PowerUpCommandMessage) command).getColor().colorName();
            case SELECT_AGGREGATE_ACTION:
                return ((AggregateActionCommandMessage) command).getAggregateActionID().toString();
            case SELECT_AMMO_PAYMENT:
                return ((ColorCommandMessage) command).getColor().colorName();
            case SELECT_BUYING_WEAPON:
            case SELECT_DISCARD_WEAPON:
            case SELECT_RELOADING_WEAPON:
            case SELECT_WEAPON:
                return ((WeaponCommandMessage) command).getWeapon();
            case SELECT_TARGET_PLAYER:
                return ((PlayerCommandMessage) command).getPlayerId().playerIdName();
            case SELECT_TARGET_SQUARE:
            case MOVE:
            case USE_TELEPORT:
            case USE_NEWTON:
                return (ConcreteView.getVerticalCoordinateName(((SquareCommandMessage) command).getRow()) + ConcreteView.getHorizontalCoordinateName(((SquareCommandMessage) command).getCol()));
            case SELECT_WEAPON_MODE:
                return ((WeaponModeCommandMessage) command).getWeaponMode();
            case SHOOT:
                return ((ShootCommandMessage) command).printCommand();
            default:
                return "";
        }
    }

    /**
     * Shows leader board at the end of game.
     *
     * @param leaderBoard is the leader board sent by server
     */

    public void showLeaderBoard(Map<PlayerId, Long> leaderBoard) {
        System.out.println("Partita conclusa");
        int i = 1;
        for (Map.Entry<PlayerId, Long> entry : leaderBoard.entrySet()) {
            System.out.println(i + "° classificato: " + entry.getKey().playerIdName() + " con " + entry.getValue() + " punti");
            i++;
        }
        System.exit(0);
    }
}
