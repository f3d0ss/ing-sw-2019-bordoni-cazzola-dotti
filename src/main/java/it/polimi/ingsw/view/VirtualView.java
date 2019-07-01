package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.MatchController;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.server.ServerManager;
import it.polimi.ingsw.view.commandmessage.CommandMessage;

import java.util.List;

public class VirtualView implements ViewInterface {

    private final ServerManager serverManager;
    private final int playerId;
    private MatchController controller;

    public VirtualView(ServerManager serverManager, int playerId) {
        this.serverManager = serverManager;
        this.playerId = playerId;
    }

    public void setController(MatchController controller) {
        this.controller = controller;
    }

    public void setGameOver() {
        serverManager.removeGame(playerId);
    }

    @Override
    public void update(MatchView mw) {
        if (!serverManager.isAwayFromKeyboardOrDisconnected(playerId) && serverManager.isListening(playerId))
            if (serverManager.sendMessageAndWaitForAnswer(playerId, new MatchViewTransfer(mw)).equals(Protocol.ERR))
                controller.disconnect(serverManager.getNickname(playerId));
    }

    @Override
    public void update(SquareView sw) {
        if (!serverManager.isAwayFromKeyboardOrDisconnected(playerId) && serverManager.isListening(playerId))
            if (serverManager.sendMessageAndWaitForAnswer(playerId, new SquareViewTransfer(sw)).equals(Protocol.ERR))
                controller.disconnect(serverManager.getNickname(playerId));
    }

    @Override
    public void update(PlayerView pw) {
        if (!serverManager.isAwayFromKeyboardOrDisconnected(playerId) && serverManager.isListening(playerId))
            if (serverManager.sendMessageAndWaitForAnswer(playerId, new PlayerViewTransfer(pw)).equals(Protocol.ERR))
                controller.disconnect(serverManager.getNickname(playerId));
    }

    @Override
    public void setViewInitializationDone() {
        serverManager.sendMessageAndWaitForAnswer(playerId, new Message(Protocol.INITIALIZATION_DONE, "", null));
    }

    @Override
    public int sendCommands(List<CommandMessage> commands, boolean undo) {
        int answer = -1;
        String choice = serverManager.sendMessageAndWaitForAnswer(playerId, new CommandViewTransfer(commands, undo));
        if (choice.equals(Protocol.ERR)) {
            controller.disconnect(serverManager.getNickname(playerId));
            return answer;
        }
        try {
            answer = Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            answer = -1;
        }
        return answer;
    }
}