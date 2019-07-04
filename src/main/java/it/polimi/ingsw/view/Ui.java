package it.polimi.ingsw.view;

import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.view.commandmessage.CommandMessage;

import java.util.List;
import java.util.Map;

/**
 * This interface joins methods for User Interface regardless of chosen technology.
 */

public interface Ui {
    String showMessage(String toBeShown, List<String> possibleAnswers, boolean isAnswerRequired);

    void refreshView(ModelView modelView);

    int manageCommandChoice(List<CommandMessage> commands, boolean undo);

    void setViewInitializationDone(ModelView modelView);

    boolean isViewInitializationDone();

    void showLeaderBoard(Map<PlayerId, Long> leaderBoard);

    void run();

    void refreshView(PlayerView pw);

    void refreshView(SquareView sw);

    void refreshView(MatchView mw);
}
