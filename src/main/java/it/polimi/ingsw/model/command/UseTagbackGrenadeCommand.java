package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TagbackGrenade;
import it.polimi.ingsw.model.exception.IllegalUndoException;
import it.polimi.ingsw.model.playerstate.IdleState;
import it.polimi.ingsw.model.playerstate.ShootedState;

public class UseTagbackGrenadeCommand implements Command {
    private ShootedState currentState;
    private TagbackGrenade grenade;
    private Player player;

    public UseTagbackGrenadeCommand(Player player, ShootedState currentState, TagbackGrenade grenade) {
        this.currentState = currentState;
        this.grenade = grenade;
        this.player = player;
    }

    @Override
    public void execute() {
        player.pay(grenade);
        player.getLastShooter().addMarks(TagbackGrenade.MARKS, player.getId());
        player.changeState(new IdleState());
    }

    @Override
    public void undo() {
        throw new IllegalUndoException();
    }
}
