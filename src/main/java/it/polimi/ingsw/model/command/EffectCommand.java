package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.model.exception.IllegalUndoException;

import java.util.List;

public class EffectCommand implements Command {
    private Player player;
    private int damage;
    private int marks;
    private List<MoveCommand> moves;
    private PlayerId shooter;

    public EffectCommand(Player player, int damage, int marks, List<MoveCommand> moves, PlayerId shooter) {
        this.player = player;
        this.damage = damage;
        this.marks = marks;
        this.moves = moves;
        this.shooter = shooter;
    }

    /**
     * This method execute the single effect of the shoot
     */
    @Override
    public void execute() {
        player.addDamage(damage, shooter);
        player.addMarks(marks, shooter);
        moves.forEach(MoveCommand::execute);
    }

    /**
     * This method throw an exception because after a shoot you can't go back
     */
    @Override
    public void undo() {
        throw new IllegalUndoException();
    }

    public Player getPlayer() {
        return player;
    }

    public boolean hasDamage() {
        return damage > 0;
    }
}
