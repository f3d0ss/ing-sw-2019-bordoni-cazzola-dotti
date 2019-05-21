package it.polimi.ingsw.network.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        String answer;
        String ip;
        Client client;
        answer = askQuestion("Quale tecnologia vuoi usare?", stdin, new ArrayList<String>() {{
            add("Socket");
            add("RMI");
        }});
        ip = askQuestion("Digita l'indirizzo ip del server", "string", stdin);
        if (answer.equals("Socket"))
            client = new SocketClient(ip);
        else {
            client = new RmiClient(ip, askQuestion("Quale porta vuoi usare? [serve per l'esecuzione in locale]", 1, stdin));
        }
        new Thread(client).start();
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
        in.nextLine();
        return possibleAnswers.get(answer - 1);
    }

    public static int askQuestion(String question, int retType, Scanner in) {
        int out;
        System.out.println(question);
        out = in.nextInt();
        in.nextLine();
        return out;
    }

    public static String askQuestion(String question, String retType, Scanner in) {
        String out;
        System.out.println(question);
        out = in.nextLine();
        return out;
    }
}
