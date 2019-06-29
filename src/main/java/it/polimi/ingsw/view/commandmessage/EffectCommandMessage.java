package it.polimi.ingsw.view.commandmessage;

import it.polimi.ingsw.model.PlayerId;

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

    public PlayerId getPlayer() {
        return player;
    }

    public int getDamage() {
        return damage;
    }

    public int getMarks() {
        return marks;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getCol() {
        return col;
    }
}
