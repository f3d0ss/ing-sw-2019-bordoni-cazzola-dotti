package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;

public abstract class WeaponCommand implements Command{
    protected Weapon weapon;
    protected ReadyToShootState currentState;

    public WeaponCommand(Weapon weapon, ReadyToShootState currentState) {
        this.weapon = weapon;
        this.currentState = currentState;
    }
}
