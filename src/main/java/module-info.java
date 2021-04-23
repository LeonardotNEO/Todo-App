module ntnu.idatt1002 {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.materialdesignicons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.jfoenix;
    requires java.desktop;
    requires com.google.gson;
    requires commons.csv;
    requires java.sql;

    exports ntnu.idatt1002;
    opens ntnu.idatt1002.controllers to javafx.fxml;
}