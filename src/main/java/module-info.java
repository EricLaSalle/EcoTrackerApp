module org.example.ecotrackerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens org.example.ecotrackerapp to javafx.fxml;
    exports org.example.ecotrackerapp;
    exports org.example.ecotrackerapp.controllers;
    opens org.example.ecotrackerapp.controllers to javafx.fxml;
}