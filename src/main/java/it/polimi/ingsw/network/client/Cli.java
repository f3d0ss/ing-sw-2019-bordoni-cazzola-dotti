package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.view.ModelView;
import it.polimi.ingsw.view.cli.CliManager;
import it.polimi.ingsw.view.commandmessage.*;

import java.util.*;

/**
 * This class manage Command Line Interface as User Interface.
 */

public class Cli implements Ui {

    private static final int FIRST_CHOICE_NUMBER = 1;
    private final Scanner stdin = new Scanner(System.in);
    private final CliManager cliManager = new CliManager();
    private boolean initializationDone = false;

    /**
     * Prints a message coming from server.
     *
     * @param toBeShown        is a string represent a question or a statement
     * @param possibleAnswers  is the list of the possibile answers that user can choose
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

    @Override
    public void run() {
    }

    /**
     * Updates the game displayed on command line.
     *
     * @param modelView is the view-side information box from which take the current game's parameters
     */

    public void refreshView(ModelView modelView) {
        cliManager.displayAll(modelView);
    }

    public void setViewInitializationDone(ModelView modelView) {
        initializationDone = true;
        refreshView(modelView);
    }

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
        List<String> possibleAnswers = new ArrayList<>();
        commands.forEach(c -> possibleAnswers.add(c.getType().getString() + getParameter(c)));
        if(undo)
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
        if (command.getType() == CommandType.RESPAWN
                || command.getType() == CommandType.SELECT_POWER_UP
                || command.getType() == CommandType.SELECT_POWER_UP_PAYMENT
                || command.getType() == CommandType.SELECT_SCOPE
                || command.getType() == CommandType.USE_TAGBACK_GRENADE)
            return ((PowerUpCommandMessage) command).getPowerUpID().powerUpName() + " " + ((PowerUpCommandMessage) command).getColor().colorName();
        if (command.getType() == CommandType.SELECT_AGGREGATE_ACTION)
            return ((AggregateActionCommandMessage) command).getAggregateActionID().toString();
        if (command.getType() == CommandType.SELECT_AMMO_PAYMENT)
            return ((ColorCommandMessage) command).getColor().colorName();
        if (command.getType() == CommandType.SELECT_BUYING_WEAPON
                || command.getType() == CommandType.SELECT_DISCARD_WEAPON
                || command.getType() == CommandType.SELECT_RELOADING_WEAPON
                || command.getType() == CommandType.SELECT_WEAPON)
            return ((WeaponCommandMessage) command).getWeapon();
        if (command.getType() == CommandType.SELECT_TARGET_PLAYER)
            return ((PlayerCommandMessage) command).getPlayerId().playerIdName();
        if (command.getType() == CommandType.SELECT_TARGET_SQUARE
                || command.getType() == CommandType.MOVE
                || command.getType() == CommandType.USE_TELEPORT)
            return (cliManager.getVerticalCoordinateName(((SquareCommandMessage) command).getRow()) + cliManager.getHorizontalCoordinateName(((SquareCommandMessage) command).getCol()));
        if (command.getType() == CommandType.SELECT_WEAPON_MODE)
            return ((WeaponModeCommandMessage) command).getWeaponMode();
        if (command.getType() == CommandType.SHOOT) {
            ShootCommandMessage shootCommandMessage = (ShootCommandMessage) command;
            List<EffectCommandMessage> effectCommandMessageList = shootCommandMessage.getEffectCommandMessageList();
            String shootString = "\n";
            for (int i = 0; i < effectCommandMessageList.size(); i++) {
                EffectCommandMessage effectCommandMessage = effectCommandMessageList.get(i);
                shootString = new StringBuilder().append(shootString).append(effectCommandMessage.getPlayer().playerIdName()).append(" ").append(effectCommandMessage.getDamage()).append(" danno").toString();
                if (effectCommandMessage.getMarks() > 0)
                    shootString = new StringBuilder().append(shootString).append(" + ").append(effectCommandMessage.getMarks()).append(" marchi").toString();
                if (effectCommandMessage.getCol() != null)
                    shootString = new StringBuilder().append(shootString).append("+ spostalo in ").append(cliManager.getHorizontalCoordinateName(effectCommandMessage.getRow())).append(cliManager.getVerticalCoordinateName(effectCommandMessage.getCol())).toString();
                if (effectCommandMessageList.size() > i + 1)
                    shootString = new StringBuilder().append(shootString).append("\n").toString();
            }
            return shootString;
        }
        return "";
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
        System.out.println("Digita qualcosa per uscire:");
        stdin.nextLine();
        System.exit(0);
    }
}
