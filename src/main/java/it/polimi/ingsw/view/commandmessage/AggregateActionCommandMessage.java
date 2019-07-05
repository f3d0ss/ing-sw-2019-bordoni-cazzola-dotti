package it.polimi.ingsw.view.commandmessage;

import it.polimi.ingsw.model.AggregateActionID;

/**
 * This class wraps an instance of {@link AggregateActionID} during transfer from server to client.
 */
public class AggregateActionCommandMessage extends CommandMessage {
    private final AggregateActionID aggregateActionID;

    public AggregateActionCommandMessage(CommandType type, AggregateActionID aggregateActionID) {
        super(type);
        this.aggregateActionID = aggregateActionID;
    }

    /**
     * Gets the AggregateActionID contained in attachment to the message.
     *
     * @return the instance of PlayerView in attachment
     */
    public AggregateActionID getAggregateActionID() {
        return aggregateActionID;
    }
}
