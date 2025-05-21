package org.example.ecotrackerapp.controllers;

import com.dlsc.formsfx.model.structure.DoubleField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.ecotrackerapp.model.ActivitatsSostenibles;
import org.example.ecotrackerapp.model.Categoria;
import org.example.ecotrackerapp.model.GestorBbDd;
import java.time.LocalDate;

public class AfegirActivitatController {
    @FXML private TextField nomField;
    @FXML private DatePicker dataPicker;
    @FXML private ComboBox<String> categoriaCombo;
    @FXML private TextArea descripcioArea;
    @FXML private TextField quantitatField;
    @FXML private Label co2Label;
    @FXML private Button guardarButton;
    @FXML private Button cancelarButton;

    private GestorBbDd gestorBbDd;

    public void initialize() {
        // Configurar limitacions de caràcters
        nomField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() > 25) {
                nomField.setText(oldVal);
            }
        });

        descripcioArea.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() > 100) {
                descripcioArea.setText(oldVal);
            }
        });

        for (int i = 0; i < GestorBbDd.getLlistaCategories().size(); i++) {
            String nomCategoria = GestorBbDd.getLlistaCategories().get(i).getNomCategoria();
            categoriaCombo.getItems().add(nomCategoria);
        }

        quantitatField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*\\.?\\d*")) {
                quantitatField.setText(oldVal);
            }
            calcularCo2();
        });

        // Calcul automàtic del CO₂
        quantitatField.textProperty().addListener((obs, oldVal, newVal) -> calcularCo2());
        categoriaCombo.valueProperty().addListener((obs, oldVal, newVal) -> calcularCo2());
    }

    private void calcularCo2() {
        try {
            if (categoriaCombo.getValue() != null && !quantitatField.getText().isEmpty()) {
                double quantitat = Double.parseDouble(quantitatField.getText());
                double co2Unitari = GestorBbDd.getLlistaCategories().get(categoriaCombo.getSelectionModel().getSelectedIndex()).getCo2UnitariEstalviat();
                double total = quantitat * co2Unitari;
                co2Label.setText(String.format("%.2f kg CO₂", total));
            }
        } catch (NumberFormatException e) {
            co2Label.setText("0.00 kg CO₂");
        }
    }

    @FXML
    private void guardarActivitat() {
        try {
            String nom = nomField.getText();
            LocalDate data = dataPicker.getValue();
            Categoria categoria = GestorBbDd.getLlistaCategories().get(categoriaCombo.getSelectionModel().getSelectedIndex());
            String descripcio = descripcioArea.getText();
            double quantitat = Double.parseDouble(quantitatField.getText());

            ActivitatsSostenibles novaActivitat = new ActivitatsSostenibles(0, nom, data, categoria, descripcio, quantitat);
            GestorBbDd.afeigrActivitataArrayList(novaActivitat);

            GestorBbDd.afegirActivitataBBDD(novaActivitat);

            // Tancar la finestra
            ((Stage) guardarButton.getScene().getWindow()).close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en guardar l'activitat");
            alert.setContentText("Si us plau, omple tots els camps correctament.");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelar() {
        ((Stage) cancelarButton.getScene().getWindow()).close();
    }
}