package main.java.com.example.sudoku6x6.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador de la pantalla de menú principal.
 *
 * <p>Se encarga de:
 * <ul>
 *   <li>Renderizar el preview decorativo del tablero al iniciar.</li>
 *   <li>Mostrar el diálogo de confirmación antes de iniciar el juego (HU-2).</li>
 *   <li>Navegar a la pantalla de juego cuando el usuario acepta.</li>
 *   <li>Mostrar las instrucciones del juego.</li>
 *   <li>Cerrar la aplicación.</li>
 * </ul>
 *
 * @author [Tu nombre]
 * @version 1.0
 */
public class MenuController implements Initializable {

    // ----------------------------------------------------------------
    // Elementos inyectados desde menu-view.fxml
    // ----------------------------------------------------------------

    /** Grid donde se dibuja el preview decorativo del tablero. */
    @FXML
    private GridPane previewGrid;

    /** Botón para iniciar el juego. */
    @FXML
    private Button btnJugar;

    /** Botón para ver instrucciones. */
    @FXML
    private Button btnInstrucciones;

    /** Botón para salir de la aplicación. */
    @FXML
    private Button btnSalir;

    // ----------------------------------------------------------------
    // Tablero de muestra (solo decorativo, no es partida real)
    // ----------------------------------------------------------------

    /**
     * Números que se muestran en el preview del menú.
     * 0 = celda vacía (color crema), >0 = celda rellena (color dorado).
     */
    private static final int[][] PREVIEW = {
        {1, 0, 3, 0, 5, 6},
        {4, 5, 0, 1, 0, 3},
        {0, 3, 1, 5, 6, 0},
        {5, 0, 4, 2, 3, 0},
        {3, 1, 0, 6, 0, 5},
        {0, 4, 5, 0, 1, 2}
    };

    // ----------------------------------------------------------------
    // Inicialización
    // ----------------------------------------------------------------

    /**
     * Se ejecuta automáticamente al cargar el FXML.
     * Construye el preview decorativo del tablero.
     *
     * @param url            URL del recurso FXML (no se usa).
     * @param resourceBundle Recursos de localización (no se usa).
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buildPreview();
    }

    /**
     * Construye el GridPane de preview con celdas decorativas.
     * Las celdas con número se pintan doradas; las vacías, crema.
     */
    private void buildPreview() {
        previewGrid.getChildren().clear();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Label cell = new Label();
                cell.setMinSize(28, 28);
                cell.setMaxSize(28, 28);
                cell.setAlignment(javafx.geometry.Pos.CENTER);
                cell.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 11px; -fx-font-weight: bold;");

                if (PREVIEW[i][j] != 0) {
                    // Celda rellena: dorada
                    cell.setText(String.valueOf(PREVIEW[i][j]));
                    cell.getStyleClass().addAll("preview-cell", "preview-cell-filled");
                } else {
                    // Celda vacía: crema
                    cell.getStyleClass().add("preview-cell");
                }

                previewGrid.add(cell, j, i);
            }
        }
    }

    // ----------------------------------------------------------------
    // Manejadores de eventos (conectados desde el FXML con onAction)
    // ----------------------------------------------------------------

    /**
     * Maneja el clic en el botón "Jugar".
     *
     * <p>Muestra un {@link Alert} de confirmación (HU-2). Si el usuario
     * acepta, navega a la pantalla de juego cargando {@code game-view.fxml}.
     *
     * @param event Evento de acción del botón.
     */
    @FXML
    private void onJugar(ActionEvent event) {
        // --- Diálogo de confirmación (HU-2) ---
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Nueva Partida");
        confirmacion.setHeaderText("¿Iniciar un nuevo juego?");
        confirmacion.setContentText("Se generará un tablero aleatorio. ¿Deseas continuar?");

        // Personalizar los botones del diálogo
        ButtonType btnAceptar = new ButtonType("Aceptar");
        ButtonType btnCancelar = new ButtonType("Cancelar");
        confirmacion.getButtonTypes().setAll(btnAceptar, btnCancelar);

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        // Si el usuario acepta, cargar la pantalla de juego
        if (resultado.isPresent() && resultado.get() == btnAceptar) {
            cargarPantallaJuego(event);
        }
        // Si cancela, no hace nada y queda en el menú
    }

    /**
     * Carga el FXML de la pantalla de juego y lo muestra en el mismo Stage.
     *
     * @param event Evento usado para obtener la referencia al Stage actual.
     */
    private void cargarPantallaJuego(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/sudoku6x6/game-view.fxml")
            );
            Scene escenaJuego = new Scene(loader.load());

            // Obtener el Stage actual desde el botón que disparó el evento
            Stage stage = (Stage) btnJugar.getScene().getWindow();
            stage.setScene(escenaJuego);
            stage.setTitle("Sudoku 6×6");
            stage.show();

        } catch (IOException e) {
            // Si el FXML no se encuentra, mostrar error
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("No se pudo cargar el juego");
            error.setContentText("Archivo game-view.fxml no encontrado: " + e.getMessage());
            error.showAndWait();
        }
    }

    /**
     * Maneja el clic en "Instrucciones".
     * Muestra un Alert informativo con las reglas del Sudoku 6×6.
     *
     * @param event Evento de acción del botón.
     */
    @FXML
    private void onInstrucciones(ActionEvent event) {
        Alert instrucciones = new Alert(Alert.AlertType.INFORMATION);
        instrucciones.setTitle("Instrucciones");
        instrucciones.setHeaderText("¿Cómo jugar Sudoku 6×6?");
        instrucciones.setContentText(
            "1. Completa la cuadrícula de 6×6 con números del 1 al 6.\n\n" +
            "2. Cada fila debe contener los números del 1 al 6 sin repetir.\n\n" +
            "3. Cada columna debe contener los números del 1 al 6 sin repetir.\n\n" +
            "4. Cada bloque de 2×3 debe contener los números del 1 al 6 sin repetir.\n\n" +
            "5. Las celdas doradas son pistas fijas — no se pueden modificar.\n\n" +
            "6. Tienes hasta 3 ayudas disponibles por partida.\n\n" +
            "Usa el teclado o los botones numéricos para ingresar valores."
        );
        instrucciones.showAndWait();
    }

    /**
     * Maneja el clic en "Salir".
     * Cierra la aplicación completamente.
     *
     * @param event Evento de acción del botón.
     */
    @FXML
    private void onSalir(ActionEvent event) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Salir");
        confirmacion.setHeaderText("¿Deseas salir del juego?");

        ButtonType btnSi = new ButtonType("Sí, salir");
        ButtonType btnNo = new ButtonType("Cancelar");
        confirmacion.getButtonTypes().setAll(btnSi, btnNo);

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == btnSi) {
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();
        }
    }
}
