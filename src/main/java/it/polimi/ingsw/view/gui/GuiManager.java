package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.Gui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class GuiManager extends Application {

    private static Client client;
    private static Gui gui;
    private static Stage stage;
    private static GridPane gridPane;
    private static Window window;
    private static boolean inputReady;

    public static void setClient(Client c) {
        client = c;
    }

    public static void setGui(Gui gui) {
        GuiManager.gui = gui;
    }

    @Override
    public void start(Stage inputStage) {
        stage = new Stage();
        window = new Window(stage, gui, "ciao", null, "");
        Scene scene = new Scene(window);
        stage.setTitle("Welcome");
        stage.setResizable(false);
        stage.setScene(scene);
        gui.setReady(true);
    }

    public static void setMessageAndShow(String string, List<String> answers) {
        String defaultAnswer = "";
        if (answers != null)
            defaultAnswer = answers.get(0);
        window = new Window(stage, gui, string, answers, defaultAnswer);
        //window.setMessage(string);
        inputReady = false;
        stage.setScene(new Scene(window));
        stage.show();
    }

    public static void setInputReady(boolean input) {
        inputReady = input;
    }
}
