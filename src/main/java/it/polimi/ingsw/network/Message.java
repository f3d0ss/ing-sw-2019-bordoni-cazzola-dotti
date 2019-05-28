package it.polimi.ingsw.network;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    public Protocol type;
    private String stringInQuestion;
    private List<String> possibleAnswers;
    private Object attachment;

    public Message(Protocol type, String stringInQuestion, List<String> possibleAnswers, int attachment) {
        this.type = type;
        this.stringInQuestion = stringInQuestion;
        this.possibleAnswers = possibleAnswers;
        this.attachment = attachment;
    }

    public List<String> getPossibleAnswer() {
        return possibleAnswers;
    }

    public String getStringInQuestion() {
        return stringInQuestion;
    }
}
