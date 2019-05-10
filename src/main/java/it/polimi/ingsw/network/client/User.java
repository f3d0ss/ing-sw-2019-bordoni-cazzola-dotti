package it.polimi.ingsw.network.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        String answer;
        String message;
        Client client;
        answer = askQuestion("Quale tecnologia vuoi usare?", stdin, new ArrayList<String>() {{
            add("Socket");
            add("RMI");
        }});
        if (answer.equals("Socket"))
            client = new SocketClient();
        else {
            client = new RmiClient(askQuestion("Quale porta vuoi usare?", stdin));
        }
        new Thread(client).start();
        System.out.println("Connessione stabilita. Digitare quit per uscire");
    }

    public static String askQuestion(String question, Scanner in, List<String> possibleAnswers) {
        int answer;
        System.out.println(question);
        for (int i = 0; i < possibleAnswers.size(); i++)
            System.out.println((i + 1) + ". " + possibleAnswers.get(i));
        answer = in.nextInt();
        while (answer > possibleAnswers.size()) {
            System.out.println("Invalid answer, try again");
            answer = in.nextInt();
        }
        return possibleAnswers.get(answer - 1);
    }

    public static int askQuestion(String question, Scanner in) {
        int answer;
        System.out.println(question);
        answer = in.nextInt();
        return answer;
    }
}
