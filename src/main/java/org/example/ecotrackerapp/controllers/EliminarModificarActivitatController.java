package org.example.ecotrackerapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.ecotrackerapp.model.ActivitatsSostenibles;

public class EliminarModificarActivitatController {
    //Declaració de valors fxml
    @FXML private TableView<ActivitatsSostenibles> tablaActividades;
    @FXML private TableColumn<ActivitatsSostenibles, Integer> colId;
    @FXML private TableColumn<ActivitatsSostenibles, String> colNom;
    @FXML private TableColumn<ActivitatsSostenibles, String> colData;
    @FXML private TableColumn<ActivitatsSostenibles, String> colCategoria;
    @FXML private TableColumn<ActivitatsSostenibles, String> colDescripcio;
    @FXML private TableColumn<ActivitatsSostenibles, Double> colQuantitat;
    @FXML private TableColumn<ActivitatsSostenibles, Double> colCo2;

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
    }
}
