package it.polimi.ingsw.network;

import java.util.List;

/**
 * This class represents a message that is used to communicate on a network
 */
public class Message {

    public final Protocol type;
    private final String stringInQuestion;
    private final List<String> possibleAnswers;
    @SuppressWarnings("squid:S1068")
    private String jType = getClass().getSimpleName();

    public Message(Protocol type, String stringInQuestion, List<String> possibleAnswers) {
        this.type = type;
        this.stringInQuestion = stringInQuestion;
        this.possibleAnswers = possibleAnswers;
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
