package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.TagbackGrenade;
import it.polimi.ingsw.model.playerstate.ShootedState;

public class UseTagbackGrenadeCommand implements Command {
    private ShootedState currentState;
    private TagbackGrenade grenade;

    public UseTagbackGrenadeCommand(ShootedState currentState, TagbackGrenade grenade) {
        this.currentState = currentState;
        this.grenade = grenade;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
