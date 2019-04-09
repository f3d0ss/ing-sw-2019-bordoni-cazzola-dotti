package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.SelectedAggregateActionState;

public class BuyCommand extends GrabCommand {
    private Weapon weapon;

    public BuyCommand(Player player, Weapon weapon) {
        super(player);
        this.weapon = weapon;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
