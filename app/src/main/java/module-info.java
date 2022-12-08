module edu.vanier.ufo {
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.controls;

    exports edu.vanier.ufo.ui;
    exports edu.vanier.ufo.engine to javafx.fxml;
    opens edu.vanier.ufo.ui to javafx.fxml;
}