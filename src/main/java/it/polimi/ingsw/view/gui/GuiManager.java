package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.Gui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class GuiManager extends Application {

    private static final double WIDTH = 730.0;
    private static final double HEIGHT = 510.0;
    private static Client client;
    private static Gui gui;
    private static Stage stage;
    private static LoginWindow window;
    private static boolean inputReady;

    private static MainGuiController controller;

    public static void setClient(Client c) {
        client = c;
    }

    public static void setGui(Gui gui) {
        GuiManager.gui = gui;
    }

    public static void setMessageAndShow(String string, List<String> answers, boolean isAnswerRequired) {
        String defaultAnswer = "";
        if (answers != null)
            defaultAnswer = answers.get(0);
        window = new LoginWindow(stage, gui, string, answers, defaultAnswer, isAnswerRequired);
        inputReady = false;
        stage.setScene(new Scene(window));
        stage.show();
    }

    public static void setInputReady(boolean input) {
        inputReady = input;
    }

    public static void startMainGui() {
        controller = MainGuiController.getInstance();

        Scene scene = new Scene(controller.getRoot());
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static MainGuiController getController(){
        return controller;
    }


    @Override
    public void start(Stage inputStage) {
        stage = new Stage();
        window = new LoginWindow(stage, gui, "", null, "", false);
        Scene scene = new Scene(window);
        stage.setTitle("Login");
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setScene(scene);
        gui.setGuiReady(true);
    }
}
