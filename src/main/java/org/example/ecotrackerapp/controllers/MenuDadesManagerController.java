package org.example.ecotrackerapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuDadesManagerController {


    /**
     * Mètode per obrir la finestra d'afegir una activitat sostenible.
     * @throws IOException
     */
    @FXML
    private void onClickAfegirActivitat() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ecotrackerapp/view/afegir_modificar_activitat.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load(), 800, 600);
        stage.setTitle("Afegir Activitat Sostenible");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    /**
     * Mètode per obrir la finestra d'afegir una categoria.
     * @throws IOException
     */
    @FXML
    private void onClickEliminarModificarActivitat() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ecotrackerapp/view/eliminar_modificar_activitat.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load(), 800, 600);
        stage.setTitle("Eliminar Activitat Sostenible");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    /**
     * Mètode per obrir la finestra d'afegir vàries activitats a l'hora des d'un arxiu.
     * @throws IOException
     */
    @FXML
    private void onClickAfegirDesdeArxiu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("org/example/ecotrackerapp/view/afegir_dades_desde_arxiu.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load(), 800, 600);
        stage.setTitle("Afegir Arxiu d'Activitats Sostenibles");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
}
