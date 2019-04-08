package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;

import java.util.List;

public class ShootCommand extends WeaponCommand {
    private List<EffectCommand> effects;
    private Player player;

    public ShootCommand(Weapon weapon, ReadyToShootState currentState, List<EffectCommand> effects, Player player) {
        super(weapon, currentState);
        this.effects = effects;
        this.player = player;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
