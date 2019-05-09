package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TagbackGrenade;
import it.polimi.ingsw.model.exception.IllegalUndoException;
import it.polimi.ingsw.model.playerstate.IdleState;

public class UseTagbackGrenadeCommand implements Command {
    private TagbackGrenade grenade;
    private Player player;

    public UseTagbackGrenadeCommand(Player player, TagbackGrenade grenade) {
        this.grenade = grenade;
        this.player = player;
    }

    /**
     * This method execute the effect of the TagbackGrenade
     */
    @Override
    public void execute() {
        player.pay(grenade);
        player.getLastShooter().addMarks(TagbackGrenade.MARKS, player.getId());
        player.changeState(new IdleState());
    }

    /**
     * This method throw an exception because after a TagbackGrenade you can't go back
     */
    @Override
    public void undo() {
        throw new IllegalUndoException();
    }
}
