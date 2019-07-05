package it.polimi.ingsw.view.commandmessage;

import it.polimi.ingsw.model.PlayerId;

/**
 * This class represents a command containing a weapon's effect.
 */
public class EffectCommandMessage {
    private final PlayerId player;
    private final int damage;
    private final int marks;
    private final Integer row;
    private final Integer col;

    public EffectCommandMessage(PlayerId player, int damage, int marks, Integer row, Integer col) {
        this.player = player;
        this.damage = damage;
        this.marks = marks;
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the player damages.
     *
     * @return the player damages
     */
    public PlayerId getPlayer() {
        return player;
    }

    /**
     * Gets the amount of damages.
     *
     * @return the amount of damages
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Gets the amount of marks.
     *
     * @return the amount of marks
     */
    public int getMarks() {
        return marks;
    }

    /**
     * Gets the row of the target square.
     *
     * @return the row of the target square
     */
    public Integer getRow() {
        return row;
    }

    /**
     * Gets the column of the target square.
     *
     * @return the column of the target square
     */
    public Integer getCol() {
        return col;
    }
}
