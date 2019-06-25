package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.ModelView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGui extends Application {

    private Stage primaryStage;
    private Pane base;
    private MainGuiController controller;
    private static ModelView modelView;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Adrenaline");
        showMainView();
    }

    private void showMainView() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainGui.class.getResource("/fx/MainGui.fxml"));
        try {
            base = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller = fxmlLoader.getController();
        Scene scene = new Scene(base);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        //controller.serModelView(modelView);
        primaryStage.show();
    }

    public static void setModelView(ModelView modelView) {
        MainGui.modelView = modelView;
    }

    public static void main(String[] args) {
        launch(args);
    }
}