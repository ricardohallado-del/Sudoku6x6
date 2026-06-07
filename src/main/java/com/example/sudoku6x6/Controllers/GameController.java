
package com.example.sudoku6x6.Controllers;

import com.example.sudoku6x6.Models.Game;
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

/**
 * Class responsible for controlling the game screen.
 * @author Dylan-Tobar, Ricardo-Hallado
 * @version 1.0
 */
public class GameController {
    @FXML private StackPane c00, c01, c02, c03, c04, c05;
    @FXML private StackPane c10, c11, c12, c13, c14, c15;
    @FXML private StackPane c20, c21, c22, c23, c24, c25;
    @FXML private StackPane c30, c31, c32, c33, c34, c35;
    @FXML private StackPane c40, c41, c42, c43, c44, c45;
    @FXML private StackPane c50, c51, c52, c53, c54, c55;

    @FXML private Label lblStatus;
    @FXML private Label lblAyudas;
    @FXML private javafx.scene.control.Button btnAyuda;

    private StackPane[][] cells;
    private Game game;
    private int selecRow = -1;
    private int selectCol = -1;
    private int helps = 3;

    /**
     * Constructor method
     */
    @FXML
    public void initialize() {
        game = new Game();

        cells = new StackPane[][] {
                {c00, c01, c02, c03, c04, c05},
                {c10, c11, c12, c13, c14, c15},
                {c20, c21, c22, c23, c24, c25},
                {c30, c31, c32, c33, c34, c35},
                {c40, c41, c42, c43, c44, c45},
                {c50, c51, c52, c53, c54, c55}
        };

        construcTab();
    }

    /**
     * Method responsible for creating the visual grid by setting styles and values for each cell.
     */
    private void construcTab() {
        for (int i = 0; i<6; i++) {
            for (int j =0;j<6;j++) {
                final int fi = i, fj = j;
                Label lbl = (Label) cells[i][j].getChildren().get(0);
                lbl.getStyleClass().clear();
                lbl.getStyleClass().add("cell-label");
                cells[i][j].getStyleClass().removeAll("cell-clue", "cell-error", "cell-hint", "cell-selected");

                if (game.isClue(i, j)) {
                    lbl.setText(String.valueOf(game.getVal(i, j)));
                    lbl.getStyleClass().add("cell-label-clue");
                    cells[i][j].getStyleClass().add("cell-clue");
                } else {
                    lbl.setText("");
                    game.setVal(i, j, 0);
                }
                cells[i][j].setOnMouseClicked(e -> selectCells(fi, fj));
            }
        }
    }

    /**
     * Method that handles the selected cells.
     * @param row: the row index of the cell that was clicked.
     * @param col: the column index of the cell that was clicked.
     */
    private void selectCells(int row, int col) {
        if (game.isClue(row, col)) {
            lblStatus.setText("Celda fija, no editable.");
            return;
        }
        selecRow = row;
        selectCol = col;
        lblStatus.setText("Celda (" + (row + 1) + ", " + (col + 1) + ") seleccionada");

        for (int i = 0;i<6;i++)
            for (int j = 0; j<6;j++)
                cells[i][j].getStyleClass().remove("cell-selected");

        cells[row][col].getStyleClass().add("cell-selected");
    }

    /**
     * Method that handles the player's keyboard input.
     * @param event the key event triggered by the player's keyboard
     */
    @FXML
    private void onKeyboard(KeyEvent event) {
        if (selecRow == -1) {
            return;
        }

        String key = event.getText();
        if (key.matches("[1-6]")) {
            enterNum(Integer.parseInt(key));
        } else if (event.getCode().toString().equals("BACK_SPACE") ||
                event.getCode().toString().equals("DELETE")) {
            deleteCells();
        }
    }

    /**
     * Method responsible for validating the number entered by the player.
     * @param num: the number to be entered in the selected box
     */
    private void enterNum(int num) {
        int f = selecRow, c = selectCol;
        game.setVal(f,c,num);
        Label lbl = (Label) cells[f][c].getChildren().get(0);
        lbl.setText(String.valueOf(num));

        cells[f][c].getStyleClass().remove("cell-error");
        cells[f][c].getStyleClass().remove("cell-selected");
        cells[f][c].getStyleClass().remove("cell-hint");
        lbl.getStyleClass().remove("cell-label-error");
        lbl.getStyleClass().remove("cell-label-hint");

        if (!game.validNum(f, c, num)) {
            cells[f][c].getStyleClass().add("cell-error");
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

    /**
     * Clears the value of the selected cell and resets its visual style.
     */
    private void deleteCells() {
        int f = selecRow, c = selectCol;
        game.setVal(f, c, 0);
        Label lbl = (Label) cells[f][c].getChildren().get(0);
        lbl.setText("");
        cells[f][c].getStyleClass().remove("cell-error");
        cells[f][c].getStyleClass().remove("cell-selected");
        cells[f][c].getStyleClass().remove("cell-hint");
        lbl.getStyleClass().remove("cell-label-error");
        lbl.getStyleClass().remove("cell-label-hint");
        lblStatus.setText("Celda borrada");
    }

    /**
     * Handles the hint button action. Provides a valid number for the first empty cell found.
     * Prevents completing the board with hints by blocking help when only one cell remains empty.
     */
    @FXML
    private void onHelps() {
        int celdasVacias = 0;

        if (helps <= 0) {
            lblStatus.setText("Sin ayudas disponibles.");
            btnAyuda.setDisable(true);
            return;
        }

        for (int i= 0; i<6;i++) {
            for (int j=0;j<6;j++) {
                if (!game.isClue(i,j) && game.getVal(i,j) == 0) {
                    celdasVacias++;
                }
            }
        }

        if (celdasVacias == 1) {
            lblStatus.setText("¡¡¡No se puede completar con ayudas!!!");
            return;
        }

        for (int i=0;i <6;i++) {
            for (int j=0;j<6;j++) {
                if (!game.isClue(i,j) && game.getVal(i,j) == 0) {
                    int val = game.helpUser(i,j);
                    if (val !=0) {
                        game.setVal(i,j,val);
                        Label lbl = (Label) cells[i][j].getChildren().get(0);
                        lbl.setText(String.valueOf(val));
                        lbl.getStyleClass().add("cell-label-hint");
                        cells[i][j].getStyleClass().add("cell-hint");
                        helps--;
                        lblAyudas.setText(String.valueOf(helps));
                        lblStatus.setText("Ayuda en (" + (i+1) + ", " + (j+1) + ")");
                        if (helps <=0) btnAyuda.setDisable(true);
                        return;
                    }
                }
            }
        }
        lblStatus.setText("No hay celdas vacías.");
    }

    /**
     * Handles the restart button action. Shows a confirmation dialog and
     * resets the current board to its initial clue state.
     */
    @FXML
    private void onRestart() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reiniciar");
        alert.setHeaderText("¿Reiniciar la partida actual?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            selecRow =-1;
            selectCol =-1;
            helps=3;
            construcTab();
            lblStatus.setText("Selecciona una celda");
            lblAyudas.setText("3");
            btnAyuda.setDisable(false);
        }
    }

    /**
     * Handles the new game button action. Shows a confirmation dialog and
     * starts a completely new game with a freshly generated board.
     */
    @FXML
    private void onNewPlayer() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nuevo Juego");
        alert.setHeaderText("¿Iniciar un nuevo juego?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            game = new Game();
            selecRow =-1;
            selectCol =-1;
            helps=3;
            construcTab();
            lblStatus.setText("Selecciona una celda");
            lblAyudas.setText("3");
            btnAyuda.setDisable(false);
        }
    }

    /**
     * Handles the back button action. Loads and displays the main menu screen.
     */
    @FXML
    private void onBackMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/sudoku6x6/menu-view.fxml")
            );
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(
                    getClass().getResource("/com/example/sudoku6x6/styles.css").toExternalForm()
            );
            Stage stage = (Stage) cells[0][0].getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
