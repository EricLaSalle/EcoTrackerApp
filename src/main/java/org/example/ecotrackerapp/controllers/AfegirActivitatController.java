package org.example.ecotrackerapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.ecotrackerapp.model.ActivitatsSostenibles;
import org.example.ecotrackerapp.model.Categoria;
import org.example.ecotrackerapp.model.GestorBbDd;
import java.time.LocalDate;

public class AfegirActivitatController {
    // FXML elements
    @FXML private TextField nomField;
    @FXML private DatePicker dataPicker;
    @FXML private ComboBox<String> categoriaCombo;
    @FXML private TextArea descripcioArea;
    @FXML private TextField quantitatField;
    @FXML private Label co2Label;
    @FXML private Label quantitatLabel;
    @FXML private Button guardarButton;
    @FXML private Button cancelarButton;

    /**
     * Inicialitza el controlador.
     */
    public void initialize() {
        // Configurar limitacions de caràcters
        nomField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() > 25) {
                nomField.setText(oldVal);
            }
        });

        // Configurar limitacions de caràcters
        descripcioArea.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() > 100) {
                descripcioArea.setText(oldVal);
            }
        });

        // Selecciona la categoria segons l'opcio escollida
        for (int i = 0; i < GestorBbDd.getLlistaCategories().size(); i++) {
            String nomCategoria = GestorBbDd.getLlistaCategories().get(i).getNomCategoria();
            categoriaCombo.getItems().add(nomCategoria);
        }

        // Comprova que quantitat sigui un número double vàlid
        quantitatField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*\\.?\\d*")) {
                quantitatField.setText(oldVal);
            }
            calcularCo2();
        });

        categoriaCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarEtiquetaQuantitat(GestorBbDd.getLlistaCategories().get(categoriaCombo.getSelectionModel().getSelectedIndex()));
            calcularCo2();
        });
    }

    /**
     * Calcula el CO₂ estalviat en funció de la categoria seleccionada i la quantitat introduïda.
     */
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

    /**
     * Guarda l'activitat introduïda en el formulari i tanca la finestra.
     */
    @FXML
    private void guardarActivitat() {
        try {
            int id = GestorBbDd.getLlistaActivitatsSostenibles().size() + 1;
            String nom = nomField.getText();
            LocalDate data = dataPicker.getValue();
            Categoria categoria = GestorBbDd.getLlistaCategories().get(categoriaCombo.getSelectionModel().getSelectedIndex());
            String descripcio = descripcioArea.getText();
            double quantitat = Double.parseDouble(quantitatField.getText());

            ActivitatsSostenibles novaActivitat = new ActivitatsSostenibles(id, nom, data, categoria, descripcio, quantitat);
            GestorBbDd.afeigrActivitataArrayList(novaActivitat);

            GestorBbDd.afegirActivitataBBDD(novaActivitat);

            //Crea una finestra que mostra que tot ha anat bé
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Activitat afegida");
            alert.setHeaderText("Activitat afegida correctament");
            alert.setContentText("L'activitat ha estat afegida correctament a la base de dades.");
            alert.showAndWait();

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

    /**
     * Actualitza la etiqueta de quantitat i el prompt text segons la categoria seleccionada.
     * @param categoria
     */
    private void actualizarEtiquetaQuantitat(Categoria categoria) {
        if (categoria == null) return;

        switch (categoria.getNomCategoria()) {
            case "Transport":
                quantitatLabel.setText("Distància (km):");
                quantitatField.setPromptText("Introdueix els quilòmetres");
                break;
            case "Energia":
                quantitatLabel.setText("Temps (hores):");
                quantitatField.setPromptText("Introdueix les hores");
                break;
            case "Alimentació":
            case "Residus":
                quantitatLabel.setText("Quantitat (kg):");
                quantitatField.setPromptText("Introdueix els kilograms");
                break;
            default:
                quantitatLabel.setText("Quantitat:");
                quantitatField.setPromptText("Introdueix la quantitat");
        }
    }

    /**
     * Tanca la finestra sense guardar l'activitat.
     */
    @FXML
    private void cancelar() {
        ((Stage) cancelarButton.getScene().getWindow()).close();
    }
}