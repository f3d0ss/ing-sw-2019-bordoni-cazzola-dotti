package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGui extends Application {

    private Stage primaryStage;
    private Pane base;

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
        Scene scene = new Scene(base);
        primaryStage.setScene(scene);
        primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(2));
        primaryStage.minHeightProperty().bind(scene.widthProperty().divide(2));
        primaryStage.setWidth(1850);
        primaryStage.setHeight(925);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}