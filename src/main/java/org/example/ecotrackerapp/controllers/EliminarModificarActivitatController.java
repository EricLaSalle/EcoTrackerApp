package org.example.ecotrackerapp.controllers;

import com.dlsc.formsfx.model.structure.IntegerField;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.ecotrackerapp.model.ActivitatsSostenibles;
import org.example.ecotrackerapp.model.GestorBbDd;

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
}
