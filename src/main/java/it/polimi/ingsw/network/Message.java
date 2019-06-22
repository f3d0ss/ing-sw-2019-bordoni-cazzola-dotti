package it.polimi.ingsw.network;

import java.util.List;

public class Message {

    public Protocol type;
    private String stringInQuestion;
    private List<String> possibleAnswers;
    private String jType = getClass().getSimpleName();
    //private Object attachment;//TODO: is attachment necessary?

    public Message(Protocol type, String stringInQuestion, List<String> possibleAnswers, Object attachment) {
        this.type = type;
        this.stringInQuestion = stringInQuestion;
        this.possibleAnswers = possibleAnswers;
        // this.attachment = attachment;
    }

    public List<String> getPossibleAnswer() {
        return possibleAnswers;
    }

    public String getStringInQuestion() {
        return stringInQuestion;
    }

    public void preSerialization() {
        jType = getClass().getSimpleName();
    }
}
