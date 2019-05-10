package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponState;

public class SelectWeaponCommand implements Command {
    private Player player;
    private Weapon weapon;
    private ChoosingWeaponState currentState;

    public SelectWeaponCommand(Player player, Weapon weapon, ChoosingWeaponState currentState) {
        this.player = player;
        this.weapon = weapon;
        this.currentState = currentState;
    }

    /**
     * This method select the weapon for the shoot
     */
    @Override
    public void execute() {
        player.changeState(new ChoosingWeaponOptionState(currentState.getSelectedAggregateAction(), weapon));
    }

    /**
     * This method deselect the weapon
     */
    @Override
    public void undo() {
        player.changeState(currentState);
    }
}
