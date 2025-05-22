package org.example.ecotrackerapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.ecotrackerapp.model.ActivitatsSostenibles;
import org.example.ecotrackerapp.model.Categoria;
import org.example.ecotrackerapp.model.GestorBbDd;
import java.time.LocalDate;

public class AfegirModificarActivitatController {
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
    @FXML private Label tituloLabel;

    // Variables d'estat
    private boolean modoModificacion = false;
    private ActivitatsSostenibles actividadAModificar;
    private int idActividadOriginal;

    /**
     * Mètode per configurar el controlador en mode modificació
     * @param actividad
     */
    public void setActividadAModificar(ActivitatsSostenibles actividad) {
        //Configura el títol
        tituloLabel.setText("Modificar Activitat Sostenible");

        //Variables
        this.modoModificacion = true;
        this.actividadAModificar = actividad;
        this.idActividadOriginal = actividad.getId();

        // Omple els camps amb els valors actuals de l'activitat a modificar
        nomField.setText(actividad.getNom());
        dataPicker.setValue(actividad.getData());
        // Buscar i seleccionar la categoria corresponent
        for (int i = 0; i < GestorBbDd.getLlistaCategories().size(); i++) {
            if (GestorBbDd.getLlistaCategories().get(i).getNomCategoria().equals(actividad.getCategoria().getNomCategoria())) {
                categoriaCombo.getSelectionModel().select(i);
                break;
            }
        }
        descripcioArea.setText(actividad.getDescripcio());
        quantitatField.setText(String.valueOf(actividad.getQuantitat()));
    }

    /**
     * Inicialitza el controlador.
     */
    public void initialize() {
        // Configurar título por defecto (para modo añadir)
        tituloLabel.setText("Afegir Nova Activitat Sostenible");
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

        // Actualitzar la etiqueta de quantitat i el prompt text segons la categoria seleccionada
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
            //Variables a declarar
            ActivitatsSostenibles activitat;
            // Validar campss obligatoris
            if (nomField.getText().isEmpty() || dataPicker.getValue() == null ||
                    categoriaCombo.getSelectionModel().isEmpty() || quantitatField.getText().isEmpty()) {
                mostrarAlerta("Error", "Si us plau, omple tots els camps obligatoris", Alert.AlertType.ERROR);
                return;
            }

            // Obtenir valors dels camps
            String nom = nomField.getText();
            LocalDate data = dataPicker.getValue();
            Categoria categoria = GestorBbDd.getLlistaCategories().get(categoriaCombo.getSelectionModel().getSelectedIndex());
            String descripcio = descripcioArea.getText();
            double quantitat = Double.parseDouble(quantitatField.getText());

            // Si estem en mode modificació, actualitzem l'activitat
            if (modoModificacion) {
                // Actualitzar l'activitat existent
                activitat = new ActivitatsSostenibles(idActividadOriginal, nom, data, categoria, descripcio, quantitat);

                // Actualitzar l'activitat a la base de dades i a la llista
                GestorBbDd.modificarActivitat(activitat, GestorBbDd.getConnection());
                GestorBbDd.calcularSumaCo2TotalEstalviat();

                //Mostrem un missatge d'èxit
                mostrarAlerta("Èxit", "Activitat modificada correctament", Alert.AlertType.INFORMATION);
            } else {
                //Afegim nova activitat a la base de dades
                activitat = new ActivitatsSostenibles(nom, data, categoria, descripcio, quantitat);
                GestorBbDd.afegirActivitataBBDD(activitat, GestorBbDd.getConnection());
                GestorBbDd.crearLlistaActivitatsSostenibles(GestorBbDd.getConnection());

                //Mostrem missatge d'èxit
                mostrarAlerta("Èxit", "Activitat afegida correctament", Alert.AlertType.INFORMATION);
            }

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

        // Actualitzar la etiqueta de quantitat i el prompt text segons la categoria seleccionada
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
     * Mostra una alerta amb el missatge i el tipus especificat.
     * @param titulo
     * @param mensaje
     * @param tipo
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Tanca la finestra sense guardar l'activitat.
     */
    @FXML
    private void cancelar() {
        ((Stage) cancelarButton.getScene().getWindow()).close();
    }
}