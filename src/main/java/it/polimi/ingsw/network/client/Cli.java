package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Protocol;

import java.util.List;
import java.util.Scanner;

public class Cli implements Ui {

    private Scanner stdin = new Scanner(System.in);

    public String showMessage(String toBeShown, List<String> possibleAnswers, boolean isAnswerRequired) {
        System.out.println(toBeShown);
        if (!isAnswerRequired)
            return Protocol.ACK;
        else if (possibleAnswers == null)
            return stdin.nextLine();
        int choice;
        for (int i = 0; i < possibleAnswers.size(); i++)
            System.out.println("(" + (i + 1) + ") " + possibleAnswers.get(i));
        choice = stdin.nextInt();
        stdin.nextLine();
        while (choice > possibleAnswers.size() || choice < 1) {
            System.out.println("Risposta non valida. Riprova:");
            choice = stdin.nextInt();
            stdin.nextLine();
        }
        return possibleAnswers.get(choice - 1);
    }

    @Override
    public void run() {

    }
}
