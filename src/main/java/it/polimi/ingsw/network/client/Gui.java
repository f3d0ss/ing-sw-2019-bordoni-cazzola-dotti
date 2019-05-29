package it.polimi.ingsw.network.client;

import it.polimi.ingsw.gui.GuiManager;
import javafx.application.Application;
import javafx.application.Platform;

import java.util.List;

import static java.lang.Thread.sleep;

public class Gui implements Ui, Runnable {

    private String answer = "";
    private boolean ready = false;
    private boolean inputReady = false;
    private String type;
    private String name;

    public String showMessage(String toBeShown, List<String> possibleAnswers){
        inputReady = false;
        System.out.println(toBeShown);//to be removed
        Platform.runLater(() -> GuiManager.setMessageAndShow(toBeShown, possibleAnswers));
        while (!inputReady) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {}
        }
        GuiManager.setInputReady(false);
        System.out.println(answer);
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public void run(){
        Application.launch(GuiManager.class);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInputReady(boolean inputReady) {
        this.inputReady = inputReady;
    }
}
