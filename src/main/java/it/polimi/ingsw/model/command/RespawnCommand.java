package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.exception.IllegalUndoException;

public class RespawnCommand implements Command {
    private Player player;
    private PowerUp powerUp;

    public RespawnCommand(Player player, PowerUp powerUp) {
        this.player = player;
        this.powerUp = powerUp;
    }

    @Override
    public void execute() {
        player.pay(powerUp);
        player.respawn(powerUp.getColor());
    }

    @Override
    public void undo() {
        throw new IllegalUndoException();
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
