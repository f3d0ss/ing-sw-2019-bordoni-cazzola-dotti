package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.AmmoTile;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurretSquare;
import it.polimi.ingsw.model.exception.IllegalUndoException;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

public class GrabTileCommand extends GrabCommand {
    private AmmoTile ammoTile;
    private TurretSquare square;

    public GrabTileCommand(Player player, AmmoTile ammoTile, TurretSquare square) {
        super(player);
        this.ammoTile = ammoTile;
        this.square = square;
    }

    @Override
    public void execute() {
        player.addAmmoTile(ammoTile);
        player.getMatch().discard(ammoTile);
        square.setAmmoTile(player.getMatch().drawAmmoTileCard());
    }

    @Override
    public void undo() {
        throw new IllegalUndoException();
    }
}
