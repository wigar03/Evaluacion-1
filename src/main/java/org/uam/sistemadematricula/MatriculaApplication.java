package org.uam.sistemadematricula;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MatriculaApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MatriculaApplication.class.getResource("matricula-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Centro Nicaragüense de Formación Tecnológica - Sistema de Matrícula");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
