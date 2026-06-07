package com.example.sudoku6x6.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Controller responsible for the main menu screen.
 * @author Dylan-Tobar, Ricardo-Hallado
 * @version 1.0
 */
public class MenuController {

    @FXML
    private GridPane previewGrid;

    /**
     * Constructor method
     */
    @FXML
    public void initialize() {
        buildPreview();
    }
    /**
     * Builds a decorative preview of a partially filled Sudoku board
     * displayed on the main menu screen.
     */
    private void buildPreview() {
        int[][] preview = {
                {1, 0, 3, 0, 5, 0},
                {0, 4, 0, 2, 0, 6},
                {2, 0, 4, 0, 6, 0},
                {0, 5, 0, 3, 0, 1},
                {3, 0, 5, 0, 1, 0},
                {0, 6, 0, 4, 0, 2}
        };

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                StackPane cell = new StackPane();
                if (preview[i][j] != 0) {
                    cell.getStyleClass().add("preview-cell-filled");
                    Label lbl = new Label(String.valueOf(preview[i][j]));
                    lbl.getStyleClass().add("logo-num");
                    cell.getChildren().add(lbl);
                } else {
                    cell.getStyleClass().add("preview-cell");
                }
                previewGrid.add(cell, j, i);
            }
        }
    }

    /**
     * Handles the play button action. Shows a confirmation dialog
     * and loads the game screen if the user confirms.
     * @param event the action event triggered by the play button
     */
    @FXML
    private void onPlay(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nuevo Juego");
        alert.setHeaderText("¿Iniciar un nuevo juego?");
        alert.setContentText("Se comenzará una nueva partida.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/example/sudoku6x6/game-view.fxml")
                );
                Scene scene = new Scene(loader.load());
                scene.getStylesheets().add(
                        getClass().getResource("/com/example/sudoku6x6/styles.css").toExternalForm()
                );
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the instructions button action.
     * Displays an information dialog with the game rules.
     * @param event the action event triggered by the instructions button
     */
    @FXML
    private void onInstruc(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instrucciones");
        alert.setHeaderText("¿Cómo jugar?");
        alert.setContentText(
                "1. Completa la cuadrícula de 6x6.\n" +
                        "2. Cada fila debe tener los números del 1 al 6.\n" +
                        "3. Cada columna debe tener los números del 1 al 6.\n" +
                        "4. Cada bloque 2x3 debe tener los números del 1 al 6.\n" +
                        "5. Usa el botón Ayuda si estás atascado (máx. 3 veces)."
        );
        alert.showAndWait();
    }

}