package org.example.ecotrackerapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.ecotrackerapp.model.GestorBbDd;
import java.io.File;

public class AfegirDadesDesdeArxiuController {

    //Elements fxml
    @FXML
    private TextField rutaArxiuField;
    @FXML
    private Label missatgeLabel;

    /**
     * Mètode per gestionar l'acció d'afegir dades des d'arxiu
     */
    @FXML
    private void handleAfegirDades() {
        String rutaArxiu = rutaArxiuField.getText().trim();

        // Validacions bàsiques
        if (rutaArxiu.isEmpty()) {
            mostrarError("Error", "Si us plau, introdueix una ruta d'arxiu vàlida.");
            return;
        }

        File arxiu = new File(rutaArxiu);

        // Comprovar si l'arxiu existeix
        if (!arxiu.exists()) {
            mostrarError("Error", "L'arxiu no existeix a la ruta especificada.");
            return;
        }

        // Comprovar si és un arxiu vàlid
        if (!arxiu.isFile()) {
            mostrarError("Error", "La ruta especificada no correspon a un arxiu vàlid.");
            return;
        }

        try {
            // Cridar al mètode del gestor de BBDD
            boolean resultat = GestorBbDd.afegirArxiuActivitats(rutaArxiu, GestorBbDd.getConnection());

            if (resultat) {
                //Actualitzem la llista
                GestorBbDd.crearLlistaActivitatsSostenibles(GestorBbDd.getConnection());
                mostrarInformacio("Èxit", "Les dades s'han afegit correctament des de l'arxiu.");
                tancarFinestra();
            } else {
                mostrarError("Error", "No s'han pogut afegir les dades. Comprova el format de l'arxiu.");
            }
        } catch (Exception e) {
            mostrarError("Error", "S'ha produït un error en processar l'arxiu: " + e.getMessage());
        }
    }

    /**
     * Mètode per gestionar l'acció de cancel·lar
     */
    @FXML
    private void handleCancelar() {
        tancarFinestra();
    }

    /**
     * Mostra un missatge d'error
     */
    private void mostrarError(String titol, String missatge) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }

    /**
     * Mostra un missatge d'informació
     */
    private void mostrarInformacio(String titol, String missatge) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }

    /**
     * Tanca la finestra actual
     */
    private void tancarFinestra() {
        Stage stage = (Stage) rutaArxiuField.getScene().getWindow();
        stage.close();
    }
}