package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.AmmoTile;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurretSquare;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.SimpleCommandMessage;

/**
 * This command represent the action of grab a tile
 */
public class GrabTileCommand extends GrabCommand {
    /**
     * This represent the ammo tile to be grabbed
     */
    private final AmmoTile ammoTile;
    /**
     * This is the square where the grab start
     */
    private final TurretSquare square;

    /**
     * @param player   is the player who do the action
     * @param ammoTile is the tail that the player grab
     * @param square   is the square where the player grab the tail
     */
    public GrabTileCommand(Player player, AmmoTile ammoTile, TurretSquare square) {
        super(player);
        this.ammoTile = ammoTile;
        this.square = square;
    }

    /**
     * This method actualize the grab action giving the ammo tile to the player and removing it from the board
     */
    @Override
    public void execute() {
        player.addAmmoTile(ammoTile);
        square.remove(ammoTile);
        player.getMatch().discard(ammoTile);
        player.changeState(new ManageTurnState());
    }

    /**
     * This throw an exception because the aggregate action is finished and the player can't go back
     */
    @Override
    public void undo() {
        throw new IllegalUndoException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUndoable() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandMessage createCommandMessage() {
        return new SimpleCommandMessage(CommandType.GRAB_TILE);
    }
}
