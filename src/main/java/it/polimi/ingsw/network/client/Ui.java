package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.ConcreteView;
import it.polimi.ingsw.view.commandmessage.CommandMessage;

import java.util.List;

public interface Ui {
    String showMessage(String toBeShown, List<String> possibleAnswers, boolean isAnswerRequired);
    void refreshView(ConcreteView concreteView);
    int manageCommandChoice(List<CommandMessage> commands, boolean undo);
    void run();
}
