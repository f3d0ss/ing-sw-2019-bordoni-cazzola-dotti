package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.AmmoTile;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

public class GrabTileCommand extends GrabCommand {
    private AmmoTile ammoTile;

    public GrabTileCommand(Player player, AmmoTile ammoTile) {
        super(player);
        this.ammoTile = ammoTile;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
