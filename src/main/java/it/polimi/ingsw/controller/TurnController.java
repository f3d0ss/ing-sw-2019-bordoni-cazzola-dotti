package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.SelectPowerUpToDiscardCommand;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.view.ViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class manages a single turn
 */
public class TurnController {

    private static final int FIRST_TURN_POWERUP = 2;
    private Match match;
    private Player currentPlayer;
    private Map<PlayerId, ViewInterface> virtualViews;

    public TurnController(Match match, Player player, Map<PlayerId, ViewInterface> virtualViews) {
        this.match = match;
        this.currentPlayer = player;
        this.virtualViews = virtualViews;
    }

    private void runTurn() {
        currentPlayer.changeState(new ManageTurnState());
        currentPlayer.initialize();
        List<Command> possibleCommands = currentPlayer.getPossibleCommands();
        while (!possibleCommands.isEmpty()) {
            //send cmd
            int numberOfCommandPicked = virtualViews.get(currentPlayer.getId()).sendCommands(possibleCommands);
            //get n of cmd to exec
            //exec cmd
            possibleCommands.get(numberOfCommandPicked).execute();
            //get new commands
            possibleCommands = currentPlayer.getPossibleCommands();
        }
    }

    private void firstTurn() {
        //TODO playerstate for respawn
        //draw 2 pu
        for (int i = 0; i < FIRST_TURN_POWERUP; i++)
            currentPlayer.drawPowerUpForRespawn();
        //choose 1 to keep or to discard
        List<Command> commands = new ArrayList<>();
        currentPlayer.getPowerUps()
                .forEach(powerUp -> commands.add(new SelectPowerUpToDiscardCommand(currentPlayer, powerUp)));
        int selectedCommand = virtualViews.get(currentPlayer.getId()).sendCommands(commands);
        commands.get(selectedCommand).execute();
        //reveal and discard the other (the color of this one is where u spawn)

        //then is a normal turn

    }
}
