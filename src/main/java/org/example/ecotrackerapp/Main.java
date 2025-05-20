package org.example.ecotrackerapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    /**
     * Aquest mètode és el primer que s'executa quan s'inicia l'aplicació, i mostra la pantalla inicial.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Aquest mètode estableix la connexió amb la base de dades.
     */
    public static void conectarAmbBBDD() {
        // Declaració de variables
        String url = "jdbc:mysql://localhost:3306/ecotrackerapp";
        String usuari = "root";
        String password = "";

        try {
            //Establir la connexió
            Connection conn = DriverManager.getConnection(url, usuari, password);
            System.out.println("Connexió amb èxit a la base de dades.");
        }catch (SQLException sqle){
            System.out.println("Error al connectar a la base de dades: " + sqle.getMessage());
        }
    }
}