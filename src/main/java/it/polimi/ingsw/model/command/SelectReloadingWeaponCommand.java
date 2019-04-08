package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponState;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.PlayerState;

public class SelectReloadingWeaponCommand implements Command{
    private Player player;
    private Weapon weapon;
    private PlayerState currentState;

    public SelectReloadingWeaponCommand(Player player, Weapon weapon, ChoosingWeaponState currentState) {
        this.player = player;
        this.weapon = weapon;
        this.currentState = currentState;
    }

    public SelectReloadingWeaponCommand(Player player, Weapon weapon, ManageTurnState currentState) {
        this.player = player;
        this.weapon = weapon;
        this.currentState = currentState;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
