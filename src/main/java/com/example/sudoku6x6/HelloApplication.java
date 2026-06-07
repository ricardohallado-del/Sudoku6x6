package com.example.sudoku6x6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("/com/example/sudoku6x6/menu-view.fxml")        );
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(
                HelloApplication.class.getResource("styles.css").toExternalForm()
        );
        stage.setTitle("Sudoku 6×6");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}