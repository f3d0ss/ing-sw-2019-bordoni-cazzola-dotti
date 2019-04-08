package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;

public class PayReloadBeforeShotCommand implements Command {
    private Player player;
    private ChoosingWeaponOptionState currentState;

    public PayReloadBeforeShotCommand(Player player, ChoosingWeaponOptionState currentState) {
        this.player = player;
        this.currentState = currentState;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
