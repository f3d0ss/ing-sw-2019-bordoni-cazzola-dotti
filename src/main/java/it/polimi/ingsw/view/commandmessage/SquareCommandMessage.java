package it.polimi.ingsw.view.commandmessage;

/**
 * This class wraps a square's coordinates during transfer from server to client.
 */
public class SquareCommandMessage extends CommandMessage {
    private final int row;
    private final int col;

    public SquareCommandMessage(CommandType type, int row, int col) {
        super(type);
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column.
     *
     * @return the column
     */
    public int getCol() {
        return col;
    }
}
