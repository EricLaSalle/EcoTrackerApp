package org.example.ecotrackerapp.controllers;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.ecotrackerapp.model.ActivitatsSostenibles;
import org.example.ecotrackerapp.model.GestorBbDd;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class LlistaActivitatsController {
    //Declaració de valors fxml
    @FXML private TableView<ActivitatsSostenibles> tablaActividades;
    @FXML private TableColumn<ActivitatsSostenibles, Integer> colId;
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
        colId.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
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

    /**
     * Aquest mètode carrega les dades de les activitats sostenibles a la taula.
     */
    private void cargarDatos() {
        ObservableList<ActivitatsSostenibles> datos = FXCollections.observableArrayList(GestorBbDd.getLlistaActivitatsSostenibles());


        tablaActividades.setItems(datos);
        mostrarTotalCo2();
    }

    /**
     * Aquest mètode mostra el total de CO2 estalviat a la interfície d'usuari.
     */
    private void mostrarTotalCo2() {
        double total = GestorBbDd.getSumaCo2TotalEstalviat();
        totalCo2.setText(String.format("%,.2f kg CO₂", total));
    }

    /**
     * Aquest mètode s'executa quan es fa clic al botó "Afegir Activitat" i mostra la finestra del gràfic.
     * @throws IOException
     */
    @FXML
    private void onClickGraficButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ecotrackerapp/view/grafic.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load(), 800, 600);
        stage.setTitle("Gràfic d'Activitats Sostenibles segons la Data");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
}