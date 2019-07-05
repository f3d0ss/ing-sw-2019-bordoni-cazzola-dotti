package it.polimi.ingsw.view;

import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.view.commandmessage.CommandMessage;

import java.util.List;
import java.util.Map;

/**
 * This interface joins methods for User Interface regardless of chosen technology.
 */

public interface Ui {

    /**
     * Shows a message coming from server.
     *
     * @param toBeShown        is a string represent a question or a statement
     * @param possibleAnswers  is the list of the possible answers that user can choose
     * @param isAnswerRequired specify if an answer is required (the message is a question)
     *                         or is not (the message is a notice)
     * @return the user's answer or a reception acknowledgement
     */
    String showMessage(String toBeShown, List<String> possibleAnswers, boolean isAnswerRequired);

    /**
     * Manages multiple options coming from server during game.
     *
     * @param commands is the list of command user can choose from
     * @param undo     specify if user can come back to the last choice
     * @return the number correspond to the user choice
     */
    int manageCommandChoice(List<CommandMessage> commands, boolean undo);

    /**
     * Sets that view initialization is not already done, which means that view-side model is not ready.
     */
    void setViewInitializationUndone();

    /**
     * Gets if the view initialization is done.
     *
     * @return true if the view initialization is done
     */
    boolean isViewInitializationDone();

    /**
     * Notify that the view initialization is done updating the instance of ModelView,
     * then calls a refresh of entire view.
     *
     * @param modelView is the ModelView instance to be updated
     */
    void setViewInitializationDone(ModelView modelView);

    /**
     * Shows leader board at the end of game.
     *
     * @param leaderBoard is the leader board sent by server
     */
    void showLeaderBoard(Map<PlayerId, Long> leaderBoard);

    /**
     * Starts the application managing the ui
     */
    void run();

    /**
     * Refreshes view after a PlayerView instance update.
     *
     * @param pw is the PlayerView instance to be updated then shown
     */
    void refreshView(PlayerView pw);

    /**
     * Refreshes view after a SquareView instance update.
     *
     * @param sw is the SquareView instance to be updated then shown
     */
    void refreshView(SquareView sw);

    /**
     * Refreshes view after a MatchView instance update.
     *
     * @param mw is the MatchView instance to be updated then shown
     */
    void refreshView(MatchView mw);
}
