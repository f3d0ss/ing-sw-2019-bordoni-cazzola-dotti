package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.SelectTargetPlayerCommand;
import it.polimi.ingsw.model.command.UseNewtonCommand;

import java.util.ArrayList;
import java.util.List;

public class SelectedNewtonState implements PlayerState, TargetingPlayerState {
    private Player selectedPlayer = null;

    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        if (selectedPlayer == null)
            player.getMatch().getCurrentPlayers().stream().filter(otherPlayer -> otherPlayer != player && otherPlayer.getPosition() != null).forEach(otherPlayer -> commands.add(new SelectTargetPlayerCommand(this, otherPlayer)));
        else
            selectedPlayer.getMatch().getBoard().getCardinalDirectionSquares(selectedPlayer.getPosition(), 2, 1, false).forEach(square -> commands.add(new UseNewtonCommand(player, this, square, selectedPlayer)));
        return commands;
    }

    @Override
    public void addTargetPlayer(Player player) {
        if (selectedPlayer != null)
            throw new IllegalStateException();
        selectedPlayer = player;
    }

    @Override
    public void removeTargetPlayer(Player player) {
        if (selectedPlayer != player)
            throw new IllegalStateException();
        selectedPlayer = null;
    }

}
