module Sudoku6x6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.sudoku6x6 to javafx.fxml;
    opens com.example.sudoku6x6.Controllers to javafx.fxml;
    opens com.example.sudoku6x6.Models to javafx.fxml;

    exports com.example.sudoku6x6 to javafx.graphics;
}