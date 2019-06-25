package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.view.ViewInterface;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class manages a single turn
 */
class TurnController {

    private final Player currentPlayer;
    private final List<Player> otherPlayers;
    private final Map<PlayerId, ViewInterface> virtualViews;
    private final Deque<Command> commandStack = new ArrayDeque<>();

    TurnController(Player player, List<Player> players, Map<PlayerId, ViewInterface> virtualViews) {
        this.currentPlayer = player;
        this.otherPlayers = new ArrayList<>(players);
        otherPlayers.remove(player);
        this.virtualViews = virtualViews;
    }

    void runTurn() {
        currentPlayer.initialize();
        List<Command> possibleCommands = currentPlayer.getPossibleCommands();
        while (possibleCommands != null) {
            int numberOfCommandPicked = virtualViews.get(currentPlayer.getId())
                    .sendCommands(possibleCommands.stream()
                            .map(Command::createCommandMessage)
                            .collect(Collectors.toList()), !commandStack.isEmpty());
            if (numberOfCommandPicked == -1)
                break;
            if (numberOfCommandPicked == possibleCommands.size())
                commandStack.pop().undo();
            else {
                Command commandToExecute = possibleCommands.get(numberOfCommandPicked);
                commandToExecute.execute();
                handleOtherPlayersCommands();
                //add to stack if undoable
                if (commandToExecute.isUndoable())
                    commandStack.push(commandToExecute);
                else
                    commandStack.clear();
            }
            //get new commands
            possibleCommands = currentPlayer.getPossibleCommands();
        }
    }

    private void handleOtherPlayersCommands() {
        for (Player p : otherPlayers) {
            if (!p.isDisconnected()) {
                List<Command> commands = p.getPossibleCommands();
                while (commands != null && !commands.isEmpty()) {
                    int i = virtualViews.get(p.getId()).sendCommands(commands.stream()
                            .map(Command::createCommandMessage)
                            .collect(Collectors.toList()), false);
                    if (i == -1)
                        break;
                    commands.get(i).execute();
                }
            }
        }
    }
}
