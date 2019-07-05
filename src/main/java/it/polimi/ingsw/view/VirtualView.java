package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.MatchController;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.server.ServerManager;
import it.polimi.ingsw.view.commandmessage.CommandMessage;

import java.util.List;

/**
 * This class represents the server-side view of a client.
 */
public class VirtualView implements ViewInterface {

    private final ServerManager serverManager;
    private final int playerId;
    private MatchController controller;

    public VirtualView(ServerManager serverManager, int playerId) {
        this.serverManager = serverManager;
        this.playerId = playerId;
    }

    /**
     * Sets the controller of the match.
     *
     * @param controller is the match's controller
     */
    public void setController(MatchController controller) {
        this.controller = controller;
    }

    /**
     * Makes server known if a match is over.
     */
    public void setGameOver() {
        serverManager.removeGame(playerId);
    }

    /**
     * Updates the MatchView instance sending a suitable message.
     * If communication gets error, disconnects the client.
     *
     * @param mw is the instance of SquareView to be updated
     */
    @Override
    public void update(MatchView mw) {
        if (!serverManager.isAwayFromKeyboardOrDisconnected(playerId) && serverManager.isListening(playerId)
                && serverManager.sendMessageAndWaitForAnswer(playerId, new MatchViewTransfer(mw)).equals(Protocol.ERR))
            controller.disconnect(serverManager.getNickname(playerId));
    }

    /**
     * Updates the SquareView instance sending a suitable message.
     * If communication gets error, disconnects the client.
     *
     * @param sw is the instance of SquareView to be updated
     */
    @Override
    public void update(SquareView sw) {
        if (!serverManager.isAwayFromKeyboardOrDisconnected(playerId) && serverManager.isListening(playerId)
                && serverManager.sendMessageAndWaitForAnswer(playerId, new SquareViewTransfer(sw)).equals(Protocol.ERR))
            controller.disconnect(serverManager.getNickname(playerId));
    }

    /**
     * Updates the PlayerView instance sending a suitable message.
     * If communication gets error, disconnects the client.
     *
     * @param pw is the instance of PlayerView to be updated
     */
    @Override
    public void update(PlayerView pw) {
        if (!serverManager.isAwayFromKeyboardOrDisconnected(playerId) && serverManager.isListening(playerId)
                && serverManager.sendMessageAndWaitForAnswer(playerId, new PlayerViewTransfer(pw)).equals(Protocol.ERR))
            controller.disconnect(serverManager.getNickname(playerId));
    }

    /**
     * Sends a message in order to make client to mark view initialization as done.
     */
    @Override
    public void setViewInitializationDone() {
        serverManager.sendMessageAndWaitForAnswer(playerId, new Message(Protocol.INITIALIZATION_DONE, "", null));
    }

    /**
     * Sends received commands to User Interface through network.
     *
     * @param commands is the list of commands
     * @param undo     tells if undo command has to be added to the possible choices
     * @return the user's answer
     */
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