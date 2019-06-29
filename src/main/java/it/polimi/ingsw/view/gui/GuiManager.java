package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Gui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class GuiManager extends Application {

    private static final double WIDTH = 730.0;
    private static final double HEIGHT = 510.0;
    private static Gui gui;
    private static Stage stage;

    private static MainGuiController controller;

    public static void setGui(Gui gui) {
        GuiManager.gui = gui;
    }

    public static void setMessageAndShow(String string, List<String> answers, boolean isAnswerRequired) {
        String defaultAnswer = "";
        if (answers != null)
            defaultAnswer = answers.get(0);
        LoginWindow window = new LoginWindow(stage, gui, string, answers, defaultAnswer, isAnswerRequired);
        stage.setScene(new Scene(window));
        stage.show();
    }

    public static void startMainGui() {
        controller = MainGuiController.getInstance();

        Scene scene = new Scene(controller.getRoot());
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static MainGuiController getController() {
        return controller;
    }


    @Override
    public void start(Stage inputStage) {
        stage = inputStage;
        LoginWindow window = new LoginWindow(inputStage, gui, "", null, "", false);
        Scene scene = new Scene(window);
        inputStage.setTitle("Login");
        inputStage.setWidth(WIDTH);
        inputStage.setHeight(HEIGHT);
        inputStage.setScene(scene);
        gui.setGuiReady(true);
    }
}
