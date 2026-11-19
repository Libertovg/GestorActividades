package Controladores;

import Main.App;
import Modelos.Actividad;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DialogoController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<String> cmbCategoria;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TextField txtPlazas;
    @FXML
    private Button btncancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbCategoria.getItems().addAll("Cultural", "Deportiva", "Tecnológica", "Solidaria");
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        Stage ventana = (Stage) ((Node) event.getSource()).getScene().getWindow();

        String nombre = txtNombre.getText();
        String categoria = cmbCategoria.getValue();
        LocalDate fecha = dpFecha.getValue();
        String textoPlazas = txtPlazas.getText();

        String error = validar(nombre, categoria, fecha, textoPlazas);
        if (error != null) {
            Alert alerta = new Alert(Alert.AlertType.ERROR, error);
            alerta.setTitle("Error de validación");
            alerta.setHeaderText(null);
            alerta.initOwner(ventana);
            alerta.showAndWait();
            return;
        }

        Actividad nueva = new Actividad(
                siguienteId(),
                nombre.trim(),
                categoria,
                fecha,
                Integer.parseInt(textoPlazas.trim()),
                0,
                "Abierta"
        );

        App.getDatos().getActividades().add(nueva);

        ventana.close();
    }

    @FXML
    private void oncancelar(ActionEvent event) {
        Stage ventana = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ventana.close();
    }

    private String validar(String nombre, String categoria, LocalDate fecha, String textoPlazas) {
        if (nombre == null || nombre.isBlank()) {
            return "El nombre es obligatorio y no puede estar formado solo por espacios.";
        }
        if (categoria == null) {
            return "Debes seleccionar una categoría.";
        }
        if (fecha == null) {
            return "Debes indicar una fecha.";
        }
        if (textoPlazas == null || textoPlazas.isBlank()) {
            return "Las plazas totales son obligatorias.";
        }
        try {
            int plazas = Integer.parseInt(textoPlazas.trim());
            if (plazas <= 0) {
                return "Las plazas totales deben ser un entero mayor que cero.";
            }
        } catch (NumberFormatException e) {
            return "Las plazas totales deben ser un número entero.";
        }
        return null;
    }

    private int siguienteId() {
        return App.getDatos().getActividades().stream()
                .mapToInt(Actividad::getId)
                .max()
                .orElse(0) + 1;
    }
}
