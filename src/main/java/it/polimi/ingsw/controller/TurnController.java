package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.view.ViewInterface;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

/**
 * This class manages a single turn
 */
class TurnController {

    private Player currentPlayer;
    private Map<PlayerId, ViewInterface> virtualViews;
    private Deque<Command> commandStack = new ArrayDeque<>();

    TurnController(Player player, Map<PlayerId, ViewInterface> virtualViews) {
        this.currentPlayer = player;
        this.virtualViews = virtualViews;
    }

    void runTurn() {
        currentPlayer.initialize();
        List<Command> possibleCommands = currentPlayer.getPossibleCommands();
        while (possibleCommands != null) {
            int numberOfCommandPicked = virtualViews.get(currentPlayer.getId()).sendCommands(possibleCommands, !commandStack.isEmpty());
            //get n of cmd to exec
            //exec cmd
            Command commandToExecute = possibleCommands.get(numberOfCommandPicked);
            commandToExecute.execute();
            //add to stack if undoable
            if (commandToExecute.isUndoable())
                commandStack.push(commandToExecute);
            else
                commandStack.clear();
            //get new commands
            possibleCommands = currentPlayer.getPossibleCommands();
        }
    }
}
