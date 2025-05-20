package org.example.ecotrackerapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ecotrackerapp.Main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class Controller {

    /**
     * Aquest mètode actúa a l'inici de tot quan es prem el botó "Obrir App".
     */
    @FXML
    protected void onOpenAppButtonClick() throws IOException {
        conectarAmbBBDD();
        pantallaMenu();
    }

    /**
     * Aquest mètode estableix la connexió amb la base de dades.
     */
    protected void conectarAmbBBDD() {
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

    protected void pantallaMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/org/example/ecotrackerapp/view/menu.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Ecotracker App");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}