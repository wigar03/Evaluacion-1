module org.uam.sistemadematricula {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.uam.sistemadematricula to javafx.fxml;
    opens org.uam.sistemadematricula.controllers to javafx.fxml;
    opens org.uam.sistemadematricula.models to javafx.base;

    exports org.uam.sistemadematricula;
    exports org.uam.sistemadematricula.models;
    exports org.uam.sistemadematricula.controllers;
}