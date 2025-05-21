package org.example.ecotrackerapp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ecotrackerapp.model.ActivitatsSostenibles;
import org.example.ecotrackerapp.model.GestorBbDd;

import java.sql.SQLException;

public class LlistaActivitatsController {
    @FXML private TableView<ActivitatsSostenibles> tablaActividades;
    @FXML private TableColumn<ActivitatsSostenibles, String> colNombre;
    @FXML private TableColumn<ActivitatsSostenibles, String> colDescripcion;
    @FXML private TableColumn<ActivitatsSostenibles, String> colCategoria;
    @FXML private TableColumn<ActivitatsSostenibles, String> colFecha;
    @FXML private TableColumn<ActivitatsSostenibles, Double> colCo2;
    @FXML private Label totalCo2;

    public void initialize() throws SQLException {
        // Configurar las columnas de la tabla
        colNombre.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("Descripció"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("Categoria"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("Data"));
        colCo2.setCellValueFactory(new PropertyValueFactory<>("CO2 Estalviat"));

        // Obtener los datos del GestorBbDd (singleton ya inicializado)
        ObservableList<ActivitatsSostenibles> datos = FXCollections.observableArrayList(GestorBbDd.getLlistaActivitatsSostenibles());

        // Asignar los datos a la tabla
        tablaActividades.setItems(datos);

        // Calcular y mostrar el total usando el método existente del GestorBbDd
        mostrarTotalCo2();
    }

    private void mostrarTotalCo2() {
        // Obtenemos el valor ya calculado
        double total = GestorBbDd.getSumaCo2TotalEstalviat();

        // Formateamos el resultado
        totalCo2.setText(String.format("%.2f kg CO₂", total));
    }
}