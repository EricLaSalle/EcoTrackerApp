package org.example.ecotrackerapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.ecotrackerapp.model.GestorBbDd;
import java.io.IOException;
import java.sql.SQLException;


public class Controller {
    @FXML
    private Button openAppButton;

    /**
     * Aquest mètode actúa a l'inici de tot quan es prem el botó "Obrir App".
     */
    @FXML
    protected void onOpenAppButtonClick() throws IOException, SQLException {
        // Carregar la classe GestorBbDd i connectar amb la base de dades
        GestorBbDd gestorBbDd = GestorBbDd.getInstance();
        // Mostrar la pantalla de menú
        pantallaMenu();
    }

    /**
     * Aquest mètode carrega la pantalla de menú i tanca l'anterior.
     * @throws IOException
     */
    protected void pantallaMenu() throws IOException {
        // Carregar la nova finestra
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/org/example/ecotrackerapp/view/menu.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Ecotracker App");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        // Tancar la finestra anterior
        Stage ventanaActual = (Stage) openAppButton.getScene().getWindow();
        ventanaActual.close();
    }
}