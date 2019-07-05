package it.polimi.ingsw.view;

import it.polimi.ingsw.view.commandmessage.CommandMessage;

import java.util.List;

public interface ViewInterface {

    /**
     * Updates the MatchView instance.
     *
     * @param mw is the instance of MatchView to be updated
     */
    void update(MatchView mw);

    /**
     * Updates the SquareView instance.
     *
     * @param sw is the instance of SquareView to be updated
     */
    void update(SquareView sw);

    /**
     * Updates the PlayerView instance.
     *
     * @param pw is the instance of PlayerView to be updated
     */
    void update(PlayerView pw);

    /**
     * Marks view initialization as done.
     */
    void setViewInitializationDone();

    /**
     * Sends received commands to User Interface.
     *
     * @param commands is the list of commands
     * @param undo     tells if undo command has to be added to the possible choices
     * @return the user's answer
     */
    int sendCommands(List<CommandMessage> commands, boolean undo);
}
