module ntnu.idatt1002 {
    requires javafx.controls;
    requires javafx.fxml;
    exports ntnu.idatt1002;
    opens ntnu.idatt1002.controllers to javafx.fxml;
}