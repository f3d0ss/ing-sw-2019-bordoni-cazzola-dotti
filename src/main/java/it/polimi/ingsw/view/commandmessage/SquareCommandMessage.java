package it.polimi.ingsw.view.commandmessage;

public class SquareCommandMessage extends CommandMessage {
    private final int row;
    private final int col;

    public SquareCommandMessage(CommandType type, int row, int col) {
        super(type);
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
