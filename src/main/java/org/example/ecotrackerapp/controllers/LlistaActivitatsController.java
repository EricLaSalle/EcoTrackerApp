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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LlistaActivitatsController {
    @FXML private TableView<ActivitatsSostenibles> tablaActividades;
    @FXML private TableColumn<ActivitatsSostenibles, Integer> colId;
    @FXML private TableColumn<ActivitatsSostenibles, String> colNom;
    @FXML private TableColumn<ActivitatsSostenibles, String> colData;
    @FXML private TableColumn<ActivitatsSostenibles, String> colCategoria;
    @FXML private TableColumn<ActivitatsSostenibles, String> colDescripcio;
    @FXML private TableColumn<ActivitatsSostenibles, Double> colQuantitat;
    @FXML private TableColumn<ActivitatsSostenibles, Double> colCo2;
    @FXML private Label totalCo2;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void initialize() {
        // Configurar columnas
        colId.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        colNom.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNom()));

        colData.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getData().format(dateFormatter)));

        colCategoria.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCategoria().getNomCategoria()));

        colDescripcio.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescripcio()));

        colQuantitat.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getQuantitat()).asObject());

        colCo2.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getCo2TotalEstalviat()).asObject());

        // Cargar datos
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