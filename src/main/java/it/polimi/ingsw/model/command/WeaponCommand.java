package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;

public abstract class WeaponCommand implements Command{
    protected ReadyToShootState currentState;

    public WeaponCommand(ReadyToShootState currentState) {
        this.currentState = currentState;
    }
}
