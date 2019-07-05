package it.polimi.ingsw.network;

import java.util.List;

/**
 * This class represents a message used to communicate on a network.
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

    /**
     * Gets the list of possible answers from which user can choose.
     * It could be null if the answer is open (for example a string).
     *
     * @return the list of possible answers
     */
    public List<String> getPossibleAnswer() {
        return possibleAnswers;
    }

    /**
     * Gets the string used to compose the message. It could be an empty string.
     *
     * @return the string used to compose the message
     */
    public String getStringInQuestion() {
        return stringInQuestion;
    }

    /**
     * Initializes fields for json serialization.
     */
    public void preSerialization() {
        jType = getClass().getSimpleName();
    }
}
