package org.example.ecotrackerapp.controllers;

import javafx.fxml.FXML;
import static org.example.ecotrackerapp.Main.conectarAmbBBDD;

public class Controller {

    @FXML
    protected void onOpenAppButtonClick() {
        conectarAmbBBDD();
    }
}