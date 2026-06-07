
        package main.java.Controllers;

import Models.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Optional;

public class GameController {

    @FXML private StackPane c00, c01, c02, c03, c04, c05;
    @FXML private StackPane c10, c11, c12, c13, c14, c15;
    @FXML private StackPane c20, c21, c22, c23, c24, c25;
    @FXML private StackPane c30, c31, c32, c33, c34, c35;
    @FXML private StackPane c40, c41, c42, c43, c44, c45;
    @FXML private StackPane c50, c51, c52, c53, c54, c55;

    @FXML private Label lblStatus;
    @FXML private Label lblAyudas;
    @FXML private Label lblTimer;
    @FXML private javafx.scene.control.Button btnAyuda;

    private StackPane[][] celdas;
    private Game game;
    private int filaSeleccionada = -1;
    private int colSeleccionada = -1;

    @FXML
    public void initialize() {
        game = new Game();

        celdas = new StackPane[][] {
                {c00, c01, c02, c03, c04, c05},
                {c10, c11, c12, c13, c14, c15},
                {c20, c21, c22, c23, c24, c25},
                {c30, c31, c32, c33, c34, c35},
                {c40, c41, c42, c43, c44, c45},
                {c50, c51, c52, c53, c54, c55}
        };

        construirTablero();
    }

    private void construirTablero() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                final int fi = i, fj = j;
                Label lbl = (Label) celdas[i][j].getChildren().get(0);
                lbl.getStyleClass().clear();
                lbl.getStyleClass().add("cell-label");
                celdas[i][j].getStyleClass().removeAll("cell-clue", "cell-error", "cell-hint", "cell-selected");

                if (game.isClue(i, j)) {
                    lbl.setText(String.valueOf(game.getVal(i, j)));
                    lbl.getStyleClass().add("cell-label-clue");
                    celdas[i][j].getStyleClass().add("cell-clue");
                } else {
                    lbl.setText("");
                    game.setVal(i, j, 0);
                }
                celdas[i][j].setOnMouseClicked(e -> seleccionarCelda(fi, fj));
            }
        }
    }

    private void seleccionarCelda(int fila, int col) {
        if (game.isClue(fila, col)) {
            lblStatus.setText("Celda fija, no editable.");
            return;
        }
        filaSeleccionada = fila;
        colSeleccionada = col;
        lblStatus.setText("Celda (" + (fila + 1) + ", " + (col + 1) + ") seleccionada");

        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                celdas[i][j].getStyleClass().remove("cell-selected");

        celdas[fila][col].getStyleClass().add("cell-selected");
        celdas[fila][col].requestFocus();
    }

    @FXML
    private void onTeclado(KeyEvent event) {
        if (filaSeleccionada == -1) return;

        String key = event.getText();
        if (key.matches("[1-6]")) {
            ingresarNumero(Integer.parseInt(key));
        } else if (event.getCode().toString().equals("BACK_SPACE") ||
                event.getCode().toString().equals("DELETE")) {
            borrarCelda();
        }
    }

    private void ingresarNumero(int num) {
        int f = filaSeleccionada, c = colSeleccionada;
        game.setVal(f, c, num);
        Label lbl = (Label) celdas[f][c].getChildren().get(0);
        lbl.setText(String.valueOf(num));

        celdas[f][c].getStyleClass().removeAll("cell-error", "cell-selected", "cell-hint");
        lbl.getStyleClass().removeAll("cell-label-error", "cell-label-hint");

        if (!game.validNum(f, c, num)) {
            celdas[f][c].getStyleClass().add("cell-error");
            lbl.getStyleClass().add("cell-label-error");
            lblStatus.setText("¡Número inválido!");
        } else {
            lblStatus.setText("✓ Número correcto");
            if (game.winUser()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("¡Ganaste!");
                alert.setHeaderText("¡Felicitaciones!");
                alert.setContentText("Completaste el Sudoku correctamente.");
                alert.showAndWait();
            }
        }
    }

    private void borrarCelda() {
        int f = filaSeleccionada, c = colSeleccionada;
        game.setVal(f, c, 0);
        Label lbl = (Label) celdas[f][c].getChildren().get(0);
        lbl.setText("");
        celdas[f][c].getStyleClass().removeAll("cell-error", "cell-selected", "cell-hint");
        lbl.getStyleClass().removeAll("cell-label-error", "cell-label-hint");
        lblStatus.setText("Celda borrada");
    }

    @FXML
    private void onAyuda() {
        if (helps <= 0) {
            lblStatus.setText("Sin ayudas disponibles.");
            btnAyuda.setDisable(true);
            return;
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (!game.isClue(i, j) && game.getVal(i, j) == 0) {
                    int val = game.helpUser(i, j);
                    if (val != 0) {
                        game.setVal(i, j, val);
                        Label lbl = (Label) celdas[i][j].getChildren().get(0);
                        lbl.setText(String.valueOf(val));
                        lbl.getStyleClass().add("cell-label-hint");
                        celdas[i][j].getStyleClass().add("cell-hint");
                        helps--;
                        lblAyudas.setText(String.valueOf(helps));
                        lblStatus.setText("Ayuda en (" + (i+1) + ", " + (j+1) + ")");
                        if (helps <= 0) btnAyuda.setDisable(true);
                        return;
                    }
                }
            }
        }
        lblStatus.setText("No hay celdas vacías.");
    }

    private int helps = 3;

    @FXML
    private void onReiniciar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reiniciar");
        alert.setHeaderText("¿Reiniciar la partida actual?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            filaSeleccionada = -1;
            colSeleccionada = -1;
            helps = 3;
            construirTablero();
            lblStatus.setText("Selecciona una celda");
            lblAyudas.setText("3");
            btnAyuda.setDisable(false);
        }
    }

    @FXML
    private void onNuevoJuego() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nuevo Juego");
        alert.setHeaderText("¿Iniciar un nuevo juego?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            game = new Game();
            filaSeleccionada = -1;
            colSeleccionada = -1;
            helps = 3;
            construirTablero();
            lblStatus.setText("Selecciona una celda");
            lblAyudas.setText("3");
            btnAyuda.setDisable(false);
        }
    }

    @FXML
    private void onVolverMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/main/java/menu-view.fxml")
            );
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(
                    getClass().getResource("/main/java/styles.css").toExternalForm()
            );
            Stage stage = (Stage) celdas[0][0].getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
