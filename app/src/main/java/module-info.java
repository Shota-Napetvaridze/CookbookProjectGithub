module main.cookbookproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires transitive javafx.graphics;



    opens main to javafx.fxml;
    exports main;
    exports controller;
    opens controller to javafx.fxml;
}