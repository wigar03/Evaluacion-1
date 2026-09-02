module org.uam.sistemadematricula {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.uam.sistemadematricula to javafx.fxml;
    exports org.uam.sistemadematricula;
}