package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.playerstate.ShootedState;
import it.polimi.ingsw.view.commandmessage.EffectCommandMessage;

import java.util.Objects;

/**
 * This command represent an effect that a weapon can have on a single player
 */
public class EffectCommand {
    /**
     * This is the player getting the effect
     */
    private final Player player;
    /**
     * This represent the damage
     */
    private final int damage;
    /**
     * This represent the marks
     */
    private final int marks;
    /**
     * This represent the square where the player will be sent
     */
    private final Square arrivalSquare;
    /**
     * This is the ID of the shooter
     */
    private final PlayerId shooter;

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
        if (shooter.equals(player.getId()) && (marks != 0 || damage != 0)) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EffectCommand that = (EffectCommand) o;
        return damage == that.damage &&
                marks == that.marks &&
                player.getId().equals(that.player.getId()) &&
                arrivalSquare.getRow() == that.arrivalSquare.getRow() &&
                arrivalSquare.getCol() == that.arrivalSquare.getCol() &&
                shooter.equals(that.shooter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(player, damage, marks, arrivalSquare, shooter);
    }

    /**
     * This method execute the single effect of the shoot
     */
    public void execute() {
        player.addDamage(damage, shooter);
        player.addMarks(marks, shooter);
        player.move(arrivalSquare);
        if (hasDamage())
            player.changeState(new ShootedState());
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

    /**
     * Create the command message that describes the command
     *
     * @return The command message that describes the command
     */
    EffectCommandMessage createCommandMessage() {
        if (arrivalSquare == player.getPosition())
            return new EffectCommandMessage(player.getId(), damage, marks, null, null);
        return new EffectCommandMessage(player.getId(), damage, marks, arrivalSquare.getRow(), arrivalSquare.getCol());
    }
}
