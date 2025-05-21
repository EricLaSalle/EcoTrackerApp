package org.example.ecotrackerapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.ecotrackerapp.model.GestorBbDd;
import java.io.IOException;
import java.sql.SQLException;


public class IniciIMenuController {
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
        FXMLLoader fxmlLoader = new FXMLLoader(IniciIMenuController.class.getResource("/org/example/ecotrackerapp/view/menu.fxml"));
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

    @FXML
    private Button verActividadesBtn;

    @FXML
    private void mostrarListaActividades() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ecotrackerapp/view/llistat_activitats.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load(), 800, 600);
        stage.setTitle("Llistat d'Activitats Sostenibles");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();

    }
}