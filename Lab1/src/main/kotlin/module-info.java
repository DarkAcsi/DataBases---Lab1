module table.lab1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens table.lab1 to javafx.fxml;
    exports table.lab1;
}