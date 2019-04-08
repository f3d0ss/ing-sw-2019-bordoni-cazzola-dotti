package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;

public class SelectTargetPlayerCommand extends WeaponCommand {
    private Player targetPlayer;

    public SelectTargetPlayerCommand(Weapon weapon, ReadyToShootState currentState, Player targetPlayer) {
        super(weapon, currentState);
        this.targetPlayer = targetPlayer;
    }

    @Override
    public void execute() {
    }

    @Override
    public void undo() {

    }
}
