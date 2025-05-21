package org.example.ecotrackerapp.controllers;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.ecotrackerapp.model.ActivitatsSostenibles;
import org.example.ecotrackerapp.model.GestorBbDd;
import java.time.format.DateTimeFormatter;

public class LlistaActivitatsController {
    //Declaració de valors fxml
    @FXML private TableView<ActivitatsSostenibles> tablaActividades;
    @FXML private TableColumn<ActivitatsSostenibles, String> colNom;
    @FXML private TableColumn<ActivitatsSostenibles, String> colData;
    @FXML private TableColumn<ActivitatsSostenibles, String> colCategoria;
    @FXML private TableColumn<ActivitatsSostenibles, String> colDescripcio;
    @FXML private TableColumn<ActivitatsSostenibles, Double> colQuantitat;
    @FXML private TableColumn<ActivitatsSostenibles, Double> colCo2;
    @FXML private Label totalCo2;

    /**
     * Aquest mètode inicialitza la vista de la llista d'activitats sostenibles.
     */
    public void initialize() {
        // Configurar columnes
        colNom.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNom()));
        colData.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        colCategoria.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCategoria().getNomCategoria()));
        colDescripcio.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescripcio()));
        colQuantitat.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getQuantitat()).asObject());
        colCo2.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getCo2TotalEstalviat()).asObject());

        // Cridem al mètode carregar dades per omplir la taula
        cargarDatos();
    }

    private void cargarDatos() {
        ObservableList<ActivitatsSostenibles> datos = FXCollections.observableArrayList(GestorBbDd.getLlistaActivitatsSostenibles());

        tablaActividades.setItems(datos);
        mostrarTotalCo2();
    }

    private void mostrarTotalCo2() {
        double total = GestorBbDd.getSumaCo2TotalEstalviat();
        totalCo2.setText(String.format("%,.2f kg CO₂", total));
    }
}