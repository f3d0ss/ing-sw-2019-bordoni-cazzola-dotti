package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.view.MatchView;
import it.polimi.ingsw.view.PlayerView;
import it.polimi.ingsw.view.SquareView;
import it.polimi.ingsw.view.cli.CliManager;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
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
        int choice;
        for (int i = 0; i < possibleAnswers.size(); i++)
            System.out.println("(" + (i + FIRST_CHOICE_NUMBER) + ") " + possibleAnswers.get(i));
        try {
            choice = stdin.nextInt();
            stdin.nextLine();
        } catch (InputMismatchException e) {
            choice = FIRST_CHOICE_NUMBER - 1;
        }
        while (choice >= possibleAnswers.size() + FIRST_CHOICE_NUMBER || choice < FIRST_CHOICE_NUMBER) {
            System.out.println("Risposta non valida. Riprova:");
            try {
                choice = stdin.nextInt();
                stdin.nextLine();
            } catch (InputMismatchException e) {
                choice = FIRST_CHOICE_NUMBER - 1;
            }
        }
        return possibleAnswers.get(choice - FIRST_CHOICE_NUMBER);
    }

    @Override
    public void run() {
    }

    public void showGame(SquareView[][] board, MatchView match, PlayerView me, Map<PlayerId, PlayerView> enemies){
        cliManager.displayAll(board, match, me, enemies);
    }
}
