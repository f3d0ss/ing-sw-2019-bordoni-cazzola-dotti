package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;

public class SelectTargetPlayerCommand extends WeaponCommand {
    private Player targetPlayer;

    public SelectTargetPlayerCommand(ReadyToShootState currentState, Player targetPlayer) {
        super(currentState);
        this.targetPlayer = targetPlayer;
    }

    @Override
    public void execute() {
        currentState.getSelectedWeapon().addTargetPlayer(targetPlayer);
    }

    @Override
    public void undo() {
        currentState.getSelectedWeapon().removeTargetPlayer(targetPlayer);
    }
}
