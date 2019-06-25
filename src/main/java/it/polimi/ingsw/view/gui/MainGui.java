package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGui extends Application {

    private Stage primaryStage;
    private static MainGuiController controller;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Adrenaline");
        showMainView();

    }

    private void showMainView() {
        controller = MainGuiController.getInstance();

        Scene scene = new Scene(controller.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static MainGuiController getController(){
        return controller;
    }

    public static void main(String[] args) {
        launch(args);
    }
}