package org.example.ecotrackerapp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.ecotrackerapp.model.ActivitatsSostenibles;
import org.example.ecotrackerapp.model.GestorBbDd;

import java.io.IOException;
import java.sql.SQLException;

public class EliminarModificarActivitatController {
    //Declaració de valors fxml
    @FXML
    private TextField idActivitat;
    @FXML
    private TableView<ActivitatsSostenibles> tablaActividades;
    @FXML
    private TableColumn<ActivitatsSostenibles, Integer> colId;
    @FXML
    private TableColumn<ActivitatsSostenibles, String> colNom;
    @FXML
    private TableColumn<ActivitatsSostenibles, String> colData;
    @FXML
    private TableColumn<ActivitatsSostenibles, String> colCategoria;
    @FXML
    private TableColumn<ActivitatsSostenibles, String> colDescripcio;
    @FXML
    private TableColumn<ActivitatsSostenibles, Double> colQuantitat;
    @FXML
    private TableColumn<ActivitatsSostenibles, Double> colCo2;
    @FXML
    private ComboBox<String> eliminarOmodificar;
    @FXML
    private Button cancelarButton;
    @FXML private Button continuarButton;

    private ActivitatsSostenibles activitatSeleccionada;

    /**
     * Aquest mètode inicialitza la vista de la llista d'activitats sostenibles.
     */
    public void initialize() {
        // Configurar columnes
        colId.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colNom.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));
        colData.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getData().toString()));
        colCategoria.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategoria().getNomCategoria()));
        colDescripcio.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescripcio()));
        colQuantitat.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getQuantitat()).asObject());
        colCo2.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getCo2TotalEstalviat()).asObject());

        // Configurar ComboBox
        eliminarOmodificar.getItems().addAll("Eliminar", "Modificar");

        // Configurar listener para el TextField
        idActivitat.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                idActivitat.setText(oldVal);
            } else if (!newVal.isEmpty()) {
                buscarActivitat();
            } else {
                tablaActividades.getItems().clear();
                activitatSeleccionada = null;
            }
        });
    }

    /**
     * Busca l'activitat a la base de dades i omple la taula amb les dades de l'activitat seleccionada.
     */
    private void buscarActivitat() {
        try {
            int idBuscado = Integer.parseInt(idActivitat.getText());
            activitatSeleccionada = null;

            for (ActivitatsSostenibles activitat : GestorBbDd.getLlistaActivitatsSostenibles()) {
                if (activitat.getId() == idBuscado) {
                    activitatSeleccionada = activitat;
                    break;
                }
            }

            if (activitatSeleccionada != null) {
                ObservableList<ActivitatsSostenibles> datos = FXCollections.observableArrayList(activitatSeleccionada);
                tablaActividades.setItems(datos);
                tablaActividades.getSelectionModel().selectFirst(); // Seleccionar automáticamente
            } else {
                tablaActividades.getItems().clear();
                mostrarAlerta("Activitat no trobada", "No s'ha trobat cap activitat amb l'ID " + idBuscado, Alert.AlertType.WARNING);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de format", "L'ID ha de ser un número vàlid", Alert.AlertType.ERROR);
        }
    }

    /**
     * Aquest mètode s'executa quan es prem el botó "Continuar" i elimina o modifica l'activitat seleccionada.
     */
    @FXML
    private void onClickContinuar() throws SQLException, IOException {
        String accion = eliminarOmodificar.getSelectionModel().getSelectedItem();

        if (accion == null) {
            mostrarAlerta("Error", "Selecciona una acció (Eliminar o Modificar)", Alert.AlertType.ERROR);
            return;
        }

        if (activitatSeleccionada == null) {
            mostrarAlerta("Error", "No s'ha seleccionat cap activitat", Alert.AlertType.ERROR);
            return;
        }

        if ("Eliminar".equals(accion)) {
            // Eliminar la actividad
            int id = activitatSeleccionada.getId();
            GestorBbDd.eliminarActivitatBBDD(id, GestorBbDd.getConnection());
            GestorBbDd.crearLlistaActivitatsSostenibles(GestorBbDd.getConnection());

            mostrarAlerta("Èxit", "Activitat eliminada correctament", Alert.AlertType.INFORMATION);
            limpiarFormulario();

            //Tancar la finestra actual
            ((Stage) continuarButton.getScene().getWindow()).close();

        } else if ("Modificar".equals(accion)) {

            //Obrir la finestra de modificar activitat
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ecotrackerapp/view/afegir_modificar_activitat.fxml"));
            Parent root = loader.load();
            AfegirModificarActivitatController controller = loader.getController();
            controller.setActividadAModificar(activitatSeleccionada);
            Stage stage = new Stage();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Modificar Activitat Sostenible");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

            //Tancar la finestra actual
            ((Stage) continuarButton.getScene().getWindow()).close();
        }
    }

    /**
     * Aquest mètode neteja el formulari i la taula.
     */
    private void limpiarFormulario() {
        idActivitat.clear();
        tablaActividades.getItems().clear();
        activitatSeleccionada = null;
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
     * Tanca la finestra si es prem cancelar
     */
    @FXML
    private void onClickCancelar() {
        //Tancar la finestra actual
        ((Stage) cancelarButton.getScene().getWindow()).close();
    }
}