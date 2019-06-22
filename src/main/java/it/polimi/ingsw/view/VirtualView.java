package it.polimi.ingsw.view;

import it.polimi.ingsw.network.CommandViewTransfer;
import it.polimi.ingsw.network.PlayerViewTransfer;
import it.polimi.ingsw.network.Protocol;
import it.polimi.ingsw.network.SquareViewTransfer;
import it.polimi.ingsw.network.server.ServerManager;
import it.polimi.ingsw.view.commandmessage.CommandMessage;

import java.util.List;

public class VirtualView implements ViewInterface {

    private ServerManager serverManager;
    private int playerId;

    public VirtualView(ServerManager serverManager, int playerId) {
        this.serverManager = serverManager;
        this.playerId = playerId;
    }

    @Override
    public void update(MatchView mw) {
    //TODO: convert into match
    }

    @Override
    public void update(SquareView sw) {
        if(serverManager.sendMessageAndWaitForAnswer(playerId, new SquareViewTransfer(sw)).equals(Protocol.ERR)) {
            //TODO: notify controller about player's disconnection
        }
    }

    @Override
    public void update(PlayerView pw) {
        if(serverManager.sendMessageAndWaitForAnswer(playerId, new PlayerViewTransfer(pw)).equals(Protocol.ERR)) {
            //TODO: notify controller about player's disconnection
        }
    }

    @Override
    public int sendCommands(List<CommandMessage> commands, boolean undo) {
        int answer;
        String choice = serverManager.sendMessageAndWaitForAnswer(playerId, new CommandViewTransfer(commands, undo));
        if(choice.equals(Protocol.ERR)) {
            //TODO: notify controller about player's disconnection
            return 1;//TODO: is the default choice the first one?
        }
        try{
            answer = Integer.parseInt(choice);
        }catch (NumberFormatException e){
            answer = 1;
        }
        return answer;
    }
}
