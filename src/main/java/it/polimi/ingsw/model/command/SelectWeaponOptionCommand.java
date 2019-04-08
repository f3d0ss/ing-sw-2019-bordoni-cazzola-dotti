package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;

public abstract class SelectWeaponOptionCommand implements Command{
    protected Player player;
    protected ChoosingWeaponOptionState currentState;

    public SelectWeaponOptionCommand(Player player, ChoosingWeaponOptionState currentState) {
        this.player = player;
        this.currentState = currentState;
    }
}
