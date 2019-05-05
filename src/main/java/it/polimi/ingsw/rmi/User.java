package it.polimi.ingsw.rmi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class User {

    public static final int SLEEPTIME = 2000;

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        String answer;
        Client client;
        answer = askQuestion("Quale tecnologia vuoi usare?", stdin, new ArrayList<String>() {{
            add("Socket");
            add("RMI");
        }});
        if(answer.equals("Socket"))
            client = new SocketClient();
        else
            client = new RmiClient();
        new Thread(client).start();
        System.out.println("Connessione stabilita. Digitare quit per uscire");
        while (true) {
            while (!client.isMessageArrived()) {
                try {
                    sleep(SLEEPTIME);
                } catch (InterruptedException e) {
                }
            }
            System.out.println("Server: " + client.getMessage());
            System.out.printf("Tu: ");
            answer = stdin.nextLine();
            client.sendAnswer(answer);
            if (answer.equals("quit"))
                break;
        }
        System.out.println("Disconnessione.");
        client.stopClient();
    }

    public static String askQuestion(String question, Scanner in, List<String> possibleAnswers) {
        int answer;
        System.out.println(question);
        for (int i = 0; i < possibleAnswers.size(); i++)
            System.out.println((i + 1) + ". " + possibleAnswers.get(i));
        answer = in.nextInt();
        while(answer > possibleAnswers.size()){
            System.out.println("Invalid answer, try again");
            answer = in.nextInt();
        }
        return possibleAnswers.get(answer);
    }
}
