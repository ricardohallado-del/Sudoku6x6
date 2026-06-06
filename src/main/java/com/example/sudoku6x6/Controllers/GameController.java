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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.java.com.example.sudoku6x6.Models.Game;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador de la pantalla de juego del Sudoku 6×6.
 *
 * <p>Implementa la arquitectura MVC como intermediario entre
 * {@code game-view.fxml} (Vista) y {@link Game} (Modelo).
 *
 * <p>Responsabilidades:
 * <ul>
 *   <li>Construir dinámicamente las 36 celdas del tablero en el GridPane.</li>
 *   <li>Gestionar la selección de celdas con mouse (HU-3).</li>
 *   <li>Gestionar el ingreso de números por teclado y botones (HU-3).</li>
 *   <li>Validar entradas en tiempo real y resaltar errores (HU-4).</li>
 *   <li>Gestionar la opción de ayuda limitada (HU-5).</li>
 *   <li>Controlar el cronómetro de la partida.</li>
 * </ul>
 *
 * @author [Nombre del compañero]
 * @version 1.0
 */
public class GameController implements Initializable {

    // ----------------------------------------------------------------
    // Elementos inyectados desde game-view.fxml
    // ----------------------------------------------------------------

    /** GridPane donde se renderizan las 36 celdas del tablero. */
    @FXML
    private GridPane gridTablero;

    /** Label que muestra el número de ayudas disponibles. */
    @FXML
    private Label lblAyudas;

    /** Label de la barra de estado (mensajes al usuario). */
    @FXML
    private Label lblStatus;

    /** Label del cronómetro. */
    @FXML
    private Label lblTimer;

    /** Botón de ayuda (se deshabilita cuando helps == 0). */
    @FXML
    private Button btnAyuda;

    // ----------------------------------------------------------------
    // Estado interno del controlador
    // ----------------------------------------------------------------

    /** Instancia del modelo del juego. */
    private Game game;

    /** Fila de la celda actualmente seleccionada (-1 = ninguna). */
    private int filaSeleccionada = -1;

    /** Columna de la celda actualmente seleccionada (-1 = ninguna). */
    private int columnaSeleccionada = -1;

    /** Matriz de StackPane que representa cada celda visualmente. */
    private StackPane[][] celdas;

    /** Número de ayudas restantes. */
    private int ayudasRestantes = 3;

    /** Segundos transcurridos desde que inició la partida. */
    private int segundos = 0;

    /** Timeline para el cronómetro. */
    private javafx.animation.Timeline cronometro;

    // ----------------------------------------------------------------
    // Inicialización
    // ----------------------------------------------------------------

    /**
     * Se ejecuta automáticamente al cargar el FXML.
     * Crea el modelo, construye el tablero visual e inicia el cronómetro.
     *
     * @param url            URL del recurso FXML (no se usa).
     * @param resourceBundle Recursos de localización (no se usa).
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new Game();
        celdas = new StackPane[6][6];
        construirTablero();
        iniciarCronometro();
    }

    // ----------------------------------------------------------------
    // Construcción del tablero visual
    // ----------------------------------------------------------------

    /**
     * Construye dinámicamente las 36 celdas del GridPane.
     *
     * <p>Por cada celda (i, j):
     * <ol>
     *   <li>Crea un {@link StackPane} con un {@link Label} en el centro.</li>
     *   <li>Asigna el estilo según si es pista o celda vacía.</li>
     *   <li>Aplica bordes gruesos en los límites de los bloques 2×3.</li>
     *   <li>Registra el evento de click del mouse para seleccionar la celda.</li>
     * </ol>
     *
     * <p>TODO: implementar este método.
     *
     * <p>Pista — para saber si una celda es pista: {@code game.isClue(i, j)}.
     * Para obtener su valor: {@code game.getVal(i, j)}.
     * Para los bordes de bloque: verificar si {@code (j+1) % 3 == 0} (borde derecho)
     * o {@code (i+1) % 2 == 0} (borde inferior).
     */
    private void construirTablero() {
        // TODO: implementar
        //
        // Estructura sugerida:
        // for (int i = 0; i < 6; i++) {
        //     for (int j = 0; j < 6; j++) {
        //         StackPane celda = crearCelda(i, j);
        //         celdas[i][j] = celda;
        //         gridTablero.add(celda, j, i);
        //     }
        // }
    }

    /**
     * Crea y configura un StackPane que representa una celda del tablero.
     *
     * @param fila    Fila de la celda (0–5).
     * @param columna Columna de la celda (0–5).
     * @return El StackPane configurado con estilos y eventos.
     *
     * <p>TODO: implementar este método.
     */
    private StackPane crearCelda(int fila, int columna) {
        // TODO: implementar
        // 1. Crear StackPane + Label con el número (o vacío si val == 0)
        // 2. Aplicar styleClass "cell-normal"
        // 3. Si game.isClue(fila, columna) → agregar "cell-clue"
        // 4. Aplicar bordes de bloque según posición
        // 5. Agregar setOnMouseClicked → llamar seleccionarCelda(fila, columna)
        return new StackPane();
    }

    // ----------------------------------------------------------------
    // Selección de celdas
    // ----------------------------------------------------------------

    /**
     * Marca la celda en (fila, columna) como seleccionada visualmente.
     * Deselecciona la anterior si había una.
     *
     * @param fila    Fila de la celda clickeada.
     * @param columna Columna de la celda clickeada.
     *
     * <p>TODO: implementar este método.
     *
     * <p>Pista — para cambiar el estilo de una celda:
     * {@code celdas[i][j].getStyleClass().add("cell-selected");}
     * {@code celdas[i][j].getStyleClass().remove("cell-selected");}
     */
    private void seleccionarCelda(int fila, int columna) {
        // TODO: implementar
        // 1. Remover "cell-selected" de la celda anterior (si filaSeleccionada != -1)
        // 2. Guardar filaSeleccionada = fila, columnaSeleccionada = columna
        // 3. Agregar "cell-selected" a la nueva celda
        // 4. Actualizar lblStatus con la posición
        // 5. Pedir foco al root para capturar eventos de teclado
    }

    // ----------------------------------------------------------------
    // Ingreso de números
    // ----------------------------------------------------------------

    /**
     * Ingresa un número en la celda seleccionada.
     * Llama al modelo para guardar el valor y luego refresca el estilo.
     *
     * @param numero Número a ingresar (1–6), o 0 para borrar.
     *
     * <p>TODO: implementar este método.
     *
     * <p>Pista — flujo sugerido:
     * <ol>
     *   <li>Verificar que haya celda seleccionada y no sea pista.</li>
     *   <li>Llamar {@code game.setVal(fila, columna, numero)}.</li>
     *   <li>Actualizar el texto del Label dentro del StackPane.</li>
     *   <li>Llamar {@code actualizarEstiloCelda(fila, columna)}.</li>
     *   <li>Si {@code game.winUser()} → mostrar mensaje de victoria.</li>
     * </ol>
     */
    private void ingresarNumero(int numero) {
        // TODO: implementar
    }

    /**
     * Actualiza el estilo CSS de una celda según su validez.
     * Aplica "cell-error" si el número viola las reglas, o lo quita si es válido.
     *
     * @param fila    Fila de la celda.
     * @param columna Columna de la celda.
     *
     * <p>TODO: implementar este método.
     *
     * <p>Pista — para validar: {@code game.validNum(fila, columna, game.getVal(fila, columna))}.
     */
    private void actualizarEstiloCelda(int fila, int columna) {
        // TODO: implementar
    }

    // ----------------------------------------------------------------
    // Manejadores de eventos del FXML
    // ----------------------------------------------------------------

    /**
     * Maneja el evento de tecla presionada en la escena.
     * Llamado desde el FXML con {@code onKeyPressed}.
     *
     * @param event Evento de teclado.
     *
     * <p>TODO: conectar este método al FXML agregando en el BorderPane:
     * {@code onKeyPressed="#onTeclado"}
     *
     * <p>Pista — para obtener el dígito:
     * {@code event.getCode().isDigitKey()} y {@code event.getText()}.
     */
    @FXML
    public void onTeclado(KeyEvent event) {
        // TODO: implementar
        // switch (event.getCode()) {
        //     case DIGIT1, NUMPAD1 -> ingresarNumero(1);
        //     ...
        //     case BACK_SPACE, DELETE -> ingresarNumero(0);
        // }
    }

    /**
     * Maneja el clic en los botones numéricos del panel lateral.
     * El texto del botón contiene el número (ej. "1", "2", ..., "6").
     *
     * @param event Evento de acción del botón.
     */
    @FXML
    private void onNumKey(ActionEvent event) {
        Button btn = (Button) event.getSource();
        try {
            int numero = Integer.parseInt(btn.getText().trim());
            ingresarNumero(numero);
        } catch (NumberFormatException e) {
            // No es un número válido, ignorar
        }
    }

    /**
     * Maneja el clic en el botón "Borrar".
     *
     * @param event Evento de acción del botón.
     */
    @FXML
    private void onBorrar(ActionEvent event) {
        ingresarNumero(0);
    }

    /**
     * Maneja el clic en "Nuevo Juego".
     * Muestra confirmación y reinicia la partida si el usuario acepta.
     *
     * @param event Evento de acción del botón.
     *
     * <p>TODO: implementar este método.
     */
    @FXML
    private void onNuevoJuego(ActionEvent event) {
        // TODO: mostrar Alert de confirmación, si acepta:
        // game = new Game();
        // filaSeleccionada = -1; columnaSeleccionada = -1;
        // ayudasRestantes = 3;
        // gridTablero.getChildren().clear();
        // construirTablero();
        // reiniciarCronometro();
    }

    /**
     * Maneja el clic en "Reiniciar".
     * Limpia los valores ingresados por el usuario sin cambiar las pistas.
     *
     * @param event Evento de acción del botón.
     *
     * <p>TODO: implementar este método.
     *
     * <p>Pista — recorrer las celdas y donde no sea pista:
     * {@code game.setVal(i, j, 0)} y actualizar el Label.
     */
    @FXML
    private void onReiniciar(ActionEvent event) {
        // TODO: implementar
    }

    /**
     * Maneja el clic en "Pedir Ayuda".
     * Sugiere un número válido para la celda seleccionada (HU-5).
     *
     * @param event Evento de acción del botón.
     *
     * <p>TODO: implementar este método.
     *
     * <p>Pista — flujo:
     * <ol>
     *   <li>Verificar celda seleccionada, que esté vacía y no sea pista.</li>
     *   <li>Llamar {@code game.helpUser(fila, columna)} → retorna el número sugerido.</li>
     *   <li>Aplicar "cell-hint" al estilo de la celda.</li>
     *   <li>Actualizar {@code ayudasRestantes} y {@code lblAyudas}.</li>
     *   <li>Si {@code ayudasRestantes == 0} → deshabilitar {@code btnAyuda}.</li>
     * </ol>
     */
    @FXML
    private void onAyuda(ActionEvent event) {
        // TODO: implementar
    }

    /**
     * Maneja el clic en "← Menú".
     * Regresa a la pantalla del menú principal.
     *
     * @param event Evento de acción del botón.
     */
    @FXML
    private void onVolverMenu(ActionEvent event) {
        try {
            cronometro.stop();
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/sudoku6x6/menu-view.fxml")
            );
            Scene escenaMenu = new Scene(loader.load());
            Stage stage = (Stage) gridTablero.getScene().getWindow();
            stage.setScene(escenaMenu);
            stage.setTitle("Sudoku 6×6 — Menú");
            stage.show();
        } catch (IOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setContentText("No se pudo cargar el menú: " + e.getMessage());
            error.showAndWait();
        }
    }

    // ----------------------------------------------------------------
    // Cronómetro
    // ----------------------------------------------------------------

    /**
     * Inicia el cronómetro que actualiza {@code lblTimer} cada segundo.
     *
     * <p>TODO: implementar este método.
     *
     * <p>Pista — usar {@link javafx.animation.Timeline} con un
     * {@link javafx.animation.KeyFrame} de 1 segundo que incremente
     * {@code segundos} y actualice el label con formato "MM:SS".
     */
    private void iniciarCronometro() {
        // TODO: implementar
        // cronometro = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
        //     segundos++;
        //     int m = segundos / 60, s = segundos % 60;
        //     lblTimer.setText(String.format("%02d:%02d", m, s));
        // }));
        // cronometro.setCycleCount(Timeline.INDEFINITE);
        // cronometro.play();
    }

    /**
     * Reinicia el cronómetro a 00:00.
     *
     * <p>TODO: implementar este método.
     */
    private void reiniciarCronometro() {
        // TODO: implementar
    }

    // ----------------------------------------------------------------
    // Victoria
    // ----------------------------------------------------------------

    /**
     * Muestra el mensaje de victoria cuando el tablero está completo.
     *
     * <p>TODO: implementar este método.
     */
    private void mostrarVictoria() {
        // TODO: implementar
        // cronometro.stop();
        // Alert victoria = new Alert(Alert.AlertType.INFORMATION);
        // victoria.setTitle("¡Felicidades!");
        // victoria.setHeaderText("¡Tablero completado!");
        // victoria.setContentText("Lo lograste en " + lblTimer.getText());
        // victoria.showAndWait();
    }
}
