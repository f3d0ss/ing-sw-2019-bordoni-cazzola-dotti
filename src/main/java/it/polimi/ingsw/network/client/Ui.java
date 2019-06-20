package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.view.MatchView;
import it.polimi.ingsw.view.PlayerView;
import it.polimi.ingsw.view.SquareView;

import java.util.List;
import java.util.Map;

public interface Ui {
    String showMessage(String toBeShown, List<String> possibleAnswers, boolean isAnswerRequired);
    void showGame(SquareView[][] board, MatchView match, PlayerView me, Map<PlayerId, PlayerView> enemies);
    void run();
}
