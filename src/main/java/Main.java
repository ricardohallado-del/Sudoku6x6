package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource("menu-view.fxml")
        );
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(
                Main.class.getResource("styles.css").toExternalForm()
        );
        stage.setTitle("Sudoku 6×6");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}