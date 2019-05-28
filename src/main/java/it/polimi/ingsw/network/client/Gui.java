package it.polimi.ingsw.network.client;

import it.polimi.ingsw.gui.GuiManager;
import javafx.application.Application;
import javafx.application.Platform;

import java.util.List;

public class Gui implements Ui, Runnable {

    private String answer = "";
    private boolean ready = false;
    private boolean inputReady = false;
    private String ip;
    private String type;
    private String name;

    public String showMessage(String toBeShown){
        show();
        /*Stage stage = new Stage();
        stage.setTitle("Welcome");
        stage.setResizable(false);
        Platform.runLater(() -> stage.show());*/
        return answer;
    }

    public String showMessage(String toBeShown, List<String> possibleAnswers){
        setScene();
        show();
        return "";
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

    public void show(){
        Platform.runLater(() -> GuiManager.showWindow());
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
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

    public boolean isInputReady() {
        return inputReady;
    }

    public void setInputReady(boolean inputReady) {
        this.inputReady = inputReady;
    }

    public static void setScene() {
        Platform.runLater(() -> GuiManager.setScene());
    }
}
