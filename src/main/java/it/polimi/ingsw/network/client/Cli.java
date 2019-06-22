package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.view.ConcreteView;
import it.polimi.ingsw.view.cli.CliManager;
import it.polimi.ingsw.view.commandmessage.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Cli implements Ui {

    private Scanner stdin = new Scanner(System.in);
    private CliManager cliManager = new CliManager();
    private final static int FIRST_CHOICE_NUMBER = 1;

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

    public void refreshView(ConcreteView concreteView){
        cliManager.displayAll(concreteView);
    }

    public int manageCommandChoice(List<CommandMessage> commands, boolean undo){
        List<String> possibleAnswers = new ArrayList<>();
        commands.forEach(c -> possibleAnswers.add(c.getType().getString() + getParameter(c)));
        System.out.println("Scegli una delle seguenti opzioni:");
        showPossibleAnswers(possibleAnswers);
        return askChoiceByNumber(possibleAnswers.size());
    }

    private void showPossibleAnswers(List<String> possibleAnswers){
        for (int i = 0; i < possibleAnswers.size(); i++)
            System.out.println("(" + (i + FIRST_CHOICE_NUMBER) + ") " + possibleAnswers.get(i));
    }

    private int askChoiceByNumber(int size){
        int choice;
        try {
            choice = stdin.nextInt();
            stdin.nextLine();
        } catch (InputMismatchException e) {
            choice = FIRST_CHOICE_NUMBER - 1;
        }
        while (choice >= size + FIRST_CHOICE_NUMBER || choice < FIRST_CHOICE_NUMBER) {
            System.out.println("Risposta non valida. Riprova:");
            try {
                choice = stdin.nextInt();
                stdin.nextLine();
            } catch (InputMismatchException e) {
                choice = FIRST_CHOICE_NUMBER - 1;
            }
        }
        return choice;
    }

    private String getParameter(CommandMessage command){
        if(command.getType() == CommandType.RESPAWN
                || command.getType() == CommandType.SELECT_POWER_UP
                || command.getType() == CommandType.SELECT_POWER_UP_PAYMENT
                || command.getType() == CommandType.SELECT_SCOPE
                || command.getType() == CommandType.USE_TAGBACK_GRENADE)
            return ((PowerUpCommandMessage)command).getPowerUpID().powerUpName() + " " + ((PowerUpCommandMessage)command).getColor().colorName();
        if(command.getType() == CommandType.SELECT_AGGREGATE_ACTION)
            return ((AggregateActionCommandMessage)command).getAggregateActionID().toString();
        if(command.getType() == CommandType.SELECT_AMMO_PAYMENT)
            return ((ColorCommandMessage)command).getColor().colorName();
        if(command.getType() == CommandType.SELECT_BUYING_WEAPON
                || command.getType() == CommandType.SELECT_DISCARD_WEAPON
                || command.getType() == CommandType.SELECT_RELOADING_WEAPON
                || command.getType() == CommandType.SELECT_WEAPON)
            return ((WeaponCommandMessage)command).getWeapon();
        if(command.getType() == CommandType.SELECT_TARGET_PLAYER)
            return ((PlayerCommandMessage)command).getPlayerId().playerIdName();
        if(command.getType() == CommandType.SELECT_TARGET_SQUARE)
            return ((SquareCommandMessage)command).getRow() + " " + ((SquareCommandMessage)command).getCol();
        if(command.getType() == CommandType.SELECT_WEAPON_MODE)
            return ((WeaponModeCommandMessage)command).getWeaponMode();
        return "";
    }
}
