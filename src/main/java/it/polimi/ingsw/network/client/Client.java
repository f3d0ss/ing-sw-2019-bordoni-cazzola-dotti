package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.Protocol;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.List;
import java.util.Scanner;

public class Client implements Runnable {

    public void run(){}

    public void startClient() throws NotBoundException, IOException{}

    public String manageMessage(String message, Scanner stdin){
        int choice;
        List<String> possibleAnswers;
        Gson gson = new Gson();
        Message fromServer = gson.fromJson(message, Message.class);
        System.out.printf(fromServer.type.getQuestion() + "\n", fromServer.getStringInQuestion());
        possibleAnswers = fromServer.getPossibleAnswer();
        if(fromServer.type == Protocol.LOGIN_FIRST || fromServer.type == Protocol.LOGIN_OTHERS || fromServer.type == Protocol.LOGIN_REPEAT)
            return stdin.nextLine();
        if (possibleAnswers == null)
            return Protocol.ACK.getQuestion();
        for (int i = 0; i < possibleAnswers.size(); i++)
            System.out.println("(" + (i + 1) + ") " + possibleAnswers.get(i));
        choice = stdin.nextInt();
        stdin.nextLine();
        while(choice > possibleAnswers.size() || choice < 1) {
            System.out.println("Risposta non valida. Riprova:");
            choice = stdin.nextInt();
            stdin.nextLine();
        }
        return String.valueOf(choice);
    }
}
