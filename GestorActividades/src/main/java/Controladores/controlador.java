/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Main.App;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author liber
 */
public class controlador {

    @FXML
    public void irinicio(ActionEvent event) throws IOException {
        App.setRoot("inicio");
    }

    @FXML
    public void irinscripcion(ActionEvent event) throws IOException {
        App.setRoot("inscripcion");

    }

    @FXML
    public void iractividades(ActionEvent event) throws IOException {
        App.setRoot("actividades");

    }

    @FXML
    public void irconfiguracion(ActionEvent event) throws IOException {
        App.setRoot("configuracion");

    }

    public void onsalir(ActionEvent event) {

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Seguro que quieres salir de la aplicación?",
                ButtonType.YES, ButtonType.NO);
        confirmacion.setTitle("Salir");
        confirmacion.setHeaderText(null);

        Optional<ButtonType> respuesta = confirmacion.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.YES) {
            Platform.exit();
        }
    }

    public void onacercade(ActionEvent event) {
        Alert informacion = new Alert(Alert.AlertType.INFORMATION,
                "Gestor de actividades  " + "\n" + "Realizado por Liberto Valero Gonzalez");
        informacion.showAndWait();

    }

}
