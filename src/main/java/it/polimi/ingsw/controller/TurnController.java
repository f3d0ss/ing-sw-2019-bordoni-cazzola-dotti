package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.view.ViewInterface;

import java.util.*;

/**
 * This class manages a single turn
 */
class TurnController {

    private Match match;
    private Player currentPlayer;
    private Map<PlayerId, ViewInterface> virtualViews;
    private Deque<Command> commandStack = new ArrayDeque<>();

    TurnController(Match match, Player player, Map<PlayerId, ViewInterface> virtualViews) {
        this.match = match;
        this.currentPlayer = player;
        this.virtualViews = virtualViews;
    }

    private void runTurn() {
        currentPlayer.initialize();
        List<Command> possibleCommands = currentPlayer.getPossibleCommands();
        while (possibleCommands != null) {
            //send cmd
            int numberOfCommandPicked = virtualViews.get(currentPlayer.getId()).sendCommands(possibleCommands);
            //get n of cmd to exec
            //exec cmd
            Command executedCommand = possibleCommands.get(numberOfCommandPicked);
            executedCommand.execute();
            //add to stack if undoable
            //TODO if execCmd is undoable
            commandStack.push(executedCommand);
            //else (not undoable)
            commandStack = new ArrayDeque<>();
            //get new commands
            possibleCommands = currentPlayer.getPossibleCommands();
        }
    }

    private void firstTurn() {
        List<Command> commands = new ArrayList<>();
        commands.addAll(currentPlayer.getSpawnCommands());
        int selectedCommand = virtualViews.get(currentPlayer.getId()).sendCommands(commands);
        commands.get(selectedCommand).execute();
    }
}
