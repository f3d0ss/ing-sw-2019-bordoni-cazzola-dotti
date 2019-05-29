package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.exception.IllegalUndoException;

/**
 * This command represent an effect that a weapon can have on a single player
 */
public class EffectCommand implements Command {
    private Player player;
    private int damage;
    private int marks;
    private Square arrivalSquare;
    private PlayerId shooter;

    /**
     * @param player        is the player affected
     * @param damage        is the damage
     * @param marks         is the number of marks
     * @param arrivalSquare is the square where the player is moved
     * @param shooter       is the player who shoot
     */
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

    /**
     * @return true if the command is undoable
     */
    @Override
    public boolean isUndoable() {
        return false;
    }

    /**
     * This method return the player
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * This method return true if the effect has damage
     *
     * @return true if the effect has damage
     */
    public boolean hasDamage() {
        return damage > 0;
    }
}
