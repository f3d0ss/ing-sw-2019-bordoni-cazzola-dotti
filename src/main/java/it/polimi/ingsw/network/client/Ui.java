package it.polimi.ingsw.network.client;

import java.util.List;

public interface Ui {
    String showMessage(String toBeShown, List<String> possibleAnswers);
    void run();
}
