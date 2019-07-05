package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.List;

/**
 * This class manages Graphical User Interface as User Interface.
 */
public class GuiManager extends Application {

    private static final double WIDTH = 730.0;
    private static final double HEIGHT = 510.0;
    private static Gui gui;
    private static Stage stage;

    private static MainGuiController controller;

    /**
     * Sets the gui
     *
     * @param gui is the gui to be setted
     */
    public static void setGui(Gui gui) {
        GuiManager.gui = gui;
    }

    /**
     * Sets and shows the message on the window.
     *
     * @param string           is the message to be shown
     * @param answers          is the list of possible answers (it could be null if answer is open)
     * @param isAnswerRequired tells if answer is required (message is a question) or not (message is a notification)
     */
    public static void setMessageAndShow(String string, List<String> answers, boolean isAnswerRequired) {
        String defaultAnswer = "";
        if (answers != null)
            defaultAnswer = answers.get(0);
        LoginWindow window = new LoginWindow(stage, gui, string, answers, defaultAnswer, isAnswerRequired);
        stage.setScene(new Scene(window));
        stage.show();
    }

    /**
     * Starts the main window of the game.
     */
    public static void startMainGui() {
        controller = MainGuiController.getInstance();

        Scene scene = new Scene(controller.getRoot());
        controller.setStage(stage);
        stage.setTitle("Adrenalina");
        stage.setScene(scene);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        scene.setOnKeyPressed(t -> {
            KeyCode key = t.getCode();
            if (key == KeyCode.ESCAPE) {
                stage.getOnCloseRequest().handle(new WindowEvent(
                        stage,
                        WindowEvent.WINDOW_CLOSE_REQUEST
                ));
            }
        });
        stage.setFullScreen(true);
        stage.show();
    }

    /**
     * Gets the gui controller.
     *
     * @return the gui controller
     */
    public static MainGuiController getController() {
        return controller;
    }

    /**
     * Starts the gui with an empty window.
     *
     * @param inputStage is the stage composing the window
     */
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
