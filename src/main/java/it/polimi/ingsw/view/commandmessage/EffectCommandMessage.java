package it.polimi.ingsw.view.commandmessage;

import it.polimi.ingsw.model.PlayerId;

public class EffectCommandMessage {
    private PlayerId player;
    private int damage;
    private int marks;
    private Integer row;
    private Integer col;

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

    public EffectCommandMessage(PlayerId player, int damage, int marks, Integer row, Integer col) {
        this.player = player;
        this.damage = damage;
        this.marks = marks;
        this.row = row;
        this.col = col;
    }
}
