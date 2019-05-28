package it.polimi.ingsw.network.client;

import it.polimi.ingsw.gui.ChooseConnection;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class Gui implements Ui, Runnable {

    private String answer = "";

    public String showMessage(String toBeShown) {
        ChooseConnection window = new ChooseConnection();
        window.start(new Stage());
        /*Stage stage = new Stage();
        stage.setTitle("Welcome");
        stage.setResizable(false);
        Platform.runLater(() -> stage.show());*/
        return answer;
    }

    public String showMessage(String toBeShown, List<String> possibleAnswers) {
        return "";
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public void run() {
        Application.launch(ChooseConnection.class);
    }

    public void show() {
        ChooseConnection.show();
    }
}
