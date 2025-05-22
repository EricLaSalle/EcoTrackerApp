package org.example.ecotrackerapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import org.example.ecotrackerapp.model.ActivitatsSostenibles;
import org.example.ecotrackerapp.model.GestorBbDd;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GraficController {

    //Variables fxml
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    /**
     * Inicialitza el gràfic i carrega les dades.
     */
    @FXML
    public void initialize() {
        configurarGrafic();
        carregarDades();
    }

    /**
     * Configura el gràfic.
     */
    private void configurarGrafic() {
        // Configurar eixos
        xAxis.setLabel("Data");
        yAxis.setLabel("Estalvi de CO₂ (kg)");

        // Configurar títol del gràfic
        lineChart.setTitle("Estalvi de CO₂ per Data");
        lineChart.setCreateSymbols(true);
        lineChart.setAnimated(false);
    }

    /**
     * Carrega les dades al gràfic.
     */
    private void carregarDades() {
        // Obtenir la llista d'activitats sostenibles
        List<ActivitatsSostenibles> activitats = GestorBbDd.getLlistaActivitatsSostenibles();

        // Comprovar si la llista és buida
        if (activitats == null || activitats.isEmpty()) {
            return;
        }

        // Ordenar per data
        activitats.sort(Comparator.comparing(ActivitatsSostenibles::getData));

        // Agrupar per mes
        Map<String, Double> estalviPerMes = new LinkedHashMap<>();

        // Iterar sobre les activitats i sumar l'estalvi de CO₂ per mes
        for (ActivitatsSostenibles activitat : activitats) {
            String mes = activitat.getData().format(DateTimeFormatter.ofPattern("MM-yyyy"));
            estalviPerMes.merge(mes, activitat.getCo2TotalEstalviat(), Double::sum);
        }

        // Crear sèrie de dades
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Estalvi mensual de CO₂");

        // Afegir dades a la sèrie
        for (Map.Entry<String, Double> entry : estalviPerMes.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        // Afegir sèrie al gràfic
        lineChart.getData().add(series);
    }
}