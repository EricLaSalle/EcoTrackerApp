package org.example.ecotrackerapp.controllers;

import com.dlsc.formsfx.model.structure.IntegerField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.ecotrackerapp.model.ActivitatsSostenibles;
import org.example.ecotrackerapp.model.GestorBbDd;

import java.sql.SQLException;

public class EliminarModificarActivitatController {
    //Declaració de valors fxml
    @FXML private TextField idActivitat;
    @FXML private TableView<ActivitatsSostenibles> tablaActividades;
    @FXML private TableColumn<ActivitatsSostenibles, Integer> colId;
    @FXML private TableColumn<ActivitatsSostenibles, String> colNom;
    @FXML private TableColumn<ActivitatsSostenibles, String> colData;
    @FXML private TableColumn<ActivitatsSostenibles, String> colCategoria;
    @FXML private TableColumn<ActivitatsSostenibles, String> colDescripcio;
    @FXML private TableColumn<ActivitatsSostenibles, Double> colQuantitat;
    @FXML private TableColumn<ActivitatsSostenibles, Double> colCo2;
    @FXML private ChoiceBox<String> eliminarOmodificar;
    @FXML private Button cancelarButton;

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

        //Comprovem que l'id sigui un número enter vàlid i llegim el valor
        idActivitat.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                idActivitat.setText(oldVal);
            }
        });

        //Afegim les opcions de modificar o eliminar
        eliminarOmodificar.getItems().add("Eliminar");
        eliminarOmodificar.getItems().add("Modificar");

        omplirTaula();
    }

    /**
     * Omple la taula amb les dades de l'activitat que coincideix amb l'id.
     */
    private void omplirTaula() {
        //Declaració de variables
        int id = 0;

        //Busquem l'activitat a la base de dades
        for (ActivitatsSostenibles activitat : GestorBbDd.getLlistaActivitatsSostenibles()) {
            if (activitat.getId() == Integer.parseInt(idActivitat.getText())) {
                tablaActividades.getItems().add(activitat);
                break;
            }
        }
    }

    /**
     * Tanca la finestra si es prem cancelar
     */
    @FXML
    private void onClickCancelar() {
        //Tancar la finestra actual
        ((Stage) cancelarButton.getScene().getWindow()).close();
    }

    /**
     * Elimina o modifica l'activitat seleccionada a la taula.
     */
    @FXML
    private void onClickContinuar() throws SQLException {
        if (eliminarOmodificar.getSelectionModel().getSelectedItem().equals("Eliminar")) {
            //Eliminar l'activitat seleccionada
            ActivitatsSostenibles activitat = tablaActividades.getSelectionModel().getSelectedItem();
            if (activitat != null) {
                int id=activitat.getId();
                //Eliminar l'activitat de la base de dades i de la llista
                GestorBbDd.eliminarActivitatBBDD(id);
                GestorBbDd.crearLlistaActivitatsSostenibles(GestorBbDd.getConnection());
                //Missatge d'alerta informatiu
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Activitat eliminada");
                alert.setHeaderText("Activitat eliminada correctament");
                alert.setContentText("L'activitat ha estat eliminada correctament de la base de dades.");
                alert.showAndWait();
            } else {
                //Missatge d'error si no s'ha seleccionat cap activitat
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No s'ha seleccionat cap activitat");
                alert.setContentText("Si us plau, selecciona una activitat per eliminar.");
                alert.showAndWait();
            }
        } else if (eliminarOmodificar.getSelectionModel().getSelectedItem().equals("Modificar")) {
            //Modificar l'activitat seleccionada
            ActivitatsSostenibles activitat = tablaActividades.getSelectionModel().getSelectedItem();
            if (activitat != null) {
                int id=activitat.getId();

            } else {
                //Missatge d'error si no s'ha seleccionat cap activitat
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No s'ha seleccionat cap activitat");
                alert.setContentText("Si us plau, selecciona una activitat per modificar.");
                alert.showAndWait();
            }
        }
    }
}
