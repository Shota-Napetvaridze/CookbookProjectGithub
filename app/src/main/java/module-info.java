module main.cookbookproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;



    opens main to javafx.fxml;
    exports main;
    exports controller;
    opens controller to javafx.fxml;
}