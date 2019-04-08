package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;

import java.util.List;

public class EffectCommand implements Command {
    private Player player;
    private int damage;
    private int marks;
    private List<MoveCommand> moves;

    public EffectCommand(Player player, int damage, int marks, List<MoveCommand> moves) {
        this.player = player;
        this.damage = damage;
        this.marks = marks;
        this.moves = moves;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
