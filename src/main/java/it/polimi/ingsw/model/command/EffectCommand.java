package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.exception.IllegalUndoException;

public class EffectCommand implements Command {
    private Player player;
    private int damage;
    private int marks;
    private Square arrivalSquare;
    private PlayerId shooter;

    public EffectCommand(Player player, int damage, int marks, Square arrivalSquare, PlayerId shooter) {
        this.player = player;
        this.damage = damage;
        this.marks = marks;
        this.arrivalSquare = arrivalSquare;
        this.shooter = shooter;
    }

    /**
     * This method execute the single effect of the shoot
     */
    @Override
    public void execute() {
        player.addDamage(damage, shooter);
        player.addMarks(marks, shooter);
        player.getPosition().removePlayer(player);
        player.move(arrivalSquare);
        player.getPosition().addPlayer(player);
    }

    /**
     * This method throw an exception because after a shoot you can't go back
     */
    @Override
    public void undo() {
        throw new IllegalUndoException();
    }

    @Override
    public boolean isUndoable() {
        return false;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean hasDamage() {
        return damage > 0;
    }
}
