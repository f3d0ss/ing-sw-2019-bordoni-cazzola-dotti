package it.polimi.ingsw.network;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    public String question;
    private List<String> possibleAnswers;
    private int toBeShown;

    public Message(String question, List<String> possibleAnswers, int toBeShown) {
        this.question = question;
        this.possibleAnswers = possibleAnswers;
        this.toBeShown = toBeShown;
    }

    public boolean requiresAnswer(){
        return possibleAnswers == null;
    }
}
