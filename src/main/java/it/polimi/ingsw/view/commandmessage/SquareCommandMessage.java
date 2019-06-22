package it.polimi.ingsw.view.commandmessage;

public class SquareCommandMessage extends CommandMessage {
    private int row;
    private int col;
    private String jsonType = getClass().getSimpleName();

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
