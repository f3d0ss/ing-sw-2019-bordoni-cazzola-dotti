package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.MatchController;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.server.ServerManager;
import it.polimi.ingsw.view.commandmessage.CommandMessage;

import java.util.List;

public class VirtualView implements ViewInterface {

    private ServerManager serverManager;
    private int playerId;
    private MatchController controller;

    public VirtualView(ServerManager serverManager, int playerId) {
        this.serverManager = serverManager;
        this.playerId = playerId;
    }

    public void setController(MatchController controller) {
        this.controller = controller;
    }

    @Override
    public void update(MatchView mw) {
        if(serverManager.sendMessageAndWaitForAnswer(playerId, new MatchViewTransfer(mw)).equals(Protocol.ERR)) {
            if(!serverManager.isAwayFromKeyboardOrDisconnected(playerId))
                controller.disconnect(serverManager.getNickname(playerId));
        }
    }

    @Override
    public void update(SquareView sw) {
        if(serverManager.sendMessageAndWaitForAnswer(playerId, new SquareViewTransfer(sw)).equals(Protocol.ERR)) {
            if(!serverManager.isAwayFromKeyboardOrDisconnected(playerId))
                controller.disconnect(serverManager.getNickname(playerId));
        }
    }

    @Override
    public void update(PlayerView pw) {
        if(serverManager.sendMessageAndWaitForAnswer(playerId, new PlayerViewTransfer(pw)).equals(Protocol.ERR)) {
            if(!serverManager.isAwayFromKeyboardOrDisconnected(playerId))
                controller.disconnect(serverManager.getNickname(playerId));
        }
    }

    @Override
    public void setViewInitializationDone(){
        serverManager.sendMessageAndWaitForAnswer(playerId, new Message(Protocol.INITIALIZATION_DONE, "", null));
    }

    @Override
    public int sendCommands(List<CommandMessage> commands, boolean undo) {
        int answer = 1;//TODO: is the default choice the first one?
        String choice = serverManager.sendMessageAndWaitForAnswer(playerId, new CommandViewTransfer(commands, undo));
        if (choice.equals(Protocol.ERR)) {
            System.out.println("VV ERRORE");
            if(!serverManager.isAwayFromKeyboardOrDisconnected(playerId))
                controller.disconnect(serverManager.getNickname(playerId));
            System.out.println("VV DISCONNESSO");
            return -1;//TODO maybe throw exception
        }
        try {
            //useless
            answer = Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            answer = -1;
        }
        return answer;
    }
}