package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.model.command.Command;

import java.util.ArrayList;
import java.util.List;

public class SelectedPowerUpState implements PlayerState, TargetingPlayerState{
    private PowerUp selectedPowerUp;

    public SelectedPowerUpState(PowerUp selectedPowerUp) {
        this.selectedPowerUp = selectedPowerUp;
    }

    @Override
    public List<Command> getPossibleCommands(Player player) {
        return new ArrayList<>(selectedPowerUp.getPossibleCommands(player.getMatch().getBoard(), player, this));
    }

    public void addTargetPlayer(Player targetPlayer) {
        selectedPowerUp.addTargetPlayer(targetPlayer);
    }

    public void removeTargetPlayer(Player targetPlayer) {
        selectedPowerUp.removeTargetPlayer(targetPlayer);
    }

}
