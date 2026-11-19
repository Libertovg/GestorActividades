/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Main.App;
import Modelos.Actividad;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author liber
 */
public class InscripcionController extends controlador implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtCorreo;
    @FXML
    private ComboBox<Actividad> cmbActividad;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private RadioButton rbManana;
    @FXML
    private ToggleGroup GROUP1;
    @FXML
    private RadioButton rbTarde;
    @FXML
    private CheckBox chkInformacion;
    @FXML
    private CheckBox chkNormas;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnEnviar;
    @FXML
    private Label lblActividad;
    @FXML
    private Label lblAviso;
    @FXML
    private Label lblEnviar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblActividad.setText("Ninguna actividad seleccionada");

        cmbActividad.valueProperty().addListener((obs, anterior, nuevo) -> {
            if (nuevo == null) {
                lblActividad.setText("Ninguna actividad seleccionada");
            } else {
                lblActividad.setText("Actividad seleccionada: " + nuevo.getNombre());
            }
        });
        lblAviso.setText("No recibirás notificaciones");

        chkInformacion.selectedProperty().addListener((obs, anterior, nuevo) -> {
            if (nuevo) {
                lblAviso.setText("Recibirás notificaciones");
            } else {
                lblAviso.setText("No recibirás notificaciones");
            }
        });

        cmbActividad.setItems(App.getDatos().getActividades());
        cmbActividad.setConverter(new StringConverter<Actividad>() {
            public String toString(Actividad actividad) {
                return actividad == null ? "" : actividad.getNombre();
            }

            public Actividad fromString(String texto) {
                return null;
            }
        });

    }

    @FXML
    public void onLimpiar(ActionEvent event) {
        boolean hayDatos = !txtNombre.getText().isEmpty()
                || !txtApellidos.getText().isEmpty()
                || !txtCorreo.getText().isEmpty()
                || cmbActividad.getValue() != null
                || dpFecha.getValue() != null
                || chkInformacion.isSelected()
                || chkNormas.isSelected()
                || !txtObservaciones.getText().isEmpty();

        if (hayDatos) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                    "Se borrarán los datos introducidos. ¿Quieres continuar?",
                    ButtonType.YES, ButtonType.NO);
            confirmacion.setTitle("Limpiar formulario");
            confirmacion.setHeaderText(null);

            Optional<ButtonType> respuesta = confirmacion.showAndWait();
            if (respuesta.isEmpty() || respuesta.get() != ButtonType.YES) {
                return;
            }
        }

        txtNombre.clear();
        txtApellidos.clear();
        txtCorreo.clear();
        cmbActividad.setValue(null);
        dpFecha.setValue(null);
        chkInformacion.setSelected(false);
        chkNormas.setSelected(false);
        txtObservaciones.clear();
        rbManana.getToggleGroup().selectToggle(null);
    }

    @FXML
    public void onEnviar(ActionEvent event) {
        List<String> errores = new ArrayList<>();

        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String correo = txtCorreo.getText().trim();
        Actividad actividad = cmbActividad.getValue();
        LocalDate fecha = dpFecha.getValue();

        if (nombre.isEmpty()) {
            errores.add("El nombre es obligatorio.");
        }
        if (apellidos.isEmpty()) {
            errores.add("Los apellidos son obligatorios.");
        }
        if (correo.isEmpty()) {
            errores.add("El correo es obligatorio.");
        } else if (!correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            errores.add("El correo no tiene un formato válido.");
        }
        if (actividad == null) {
            errores.add("Debes seleccionar una actividad.");
        }
        if (fecha == null) {
            errores.add("Debes seleccionar una fecha.");
        } else if (fecha.isBefore(LocalDate.now())) {
            errores.add("La fecha no puede ser anterior a hoy.");
        }
        if (rbManana.getToggleGroup().getSelectedToggle() == null) {
            errores.add("Debes seleccionar un turno.");
        }
        if (!chkNormas.isSelected()) {
            errores.add("Debes aceptar las normas.");
        }

        if (!errores.isEmpty()) {
            Alert aviso = new Alert(Alert.AlertType.WARNING, String.join("\n", errores));
            aviso.setTitle("Formulario incompleto");
            aviso.setHeaderText("Revisa los datos");
            aviso.showAndWait();
            return;
        }

        if (actividad.getPlazasOcupadas() >= actividad.getPlazasTotales()) {
            Alert completa = new Alert(Alert.AlertType.ERROR,
                    "La actividad \"" + actividad.getNombre() + "\" no tiene plazas libres.");
            completa.setTitle("Actividad completa");
            completa.setHeaderText(null);
            completa.showAndWait();
            return;
        }

        actividad.setPlazasOcupadas(actividad.getPlazasOcupadas() + 1);

        String turno = rbManana.isSelected() ? "Mañana" : "Tarde";
        String resumen = "Nombre: " + nombre + " " + apellidos + "\n"
                + "Correo: " + correo + "\n"
                + "Actividad: " + actividad.getNombre() + "\n" // CAMBIADO
                + "Fecha: " + fecha + "\n"
                + "Turno: " + turno + "\n"
                + "Avisos: " + (chkInformacion.isSelected() ? "Sí" : "No") + "\n"
                + "Observaciones: " + txtObservaciones.getText().trim();

        Alert resumenAlert = new Alert(Alert.AlertType.INFORMATION, resumen);
        resumenAlert.setTitle("Resumen de inscripción");
        resumenAlert.setHeaderText(null);
        resumenAlert.showAndWait();
    }

}
