/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Main.App;
import Modelos.Actividad;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Modelos.Actividad;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * FXML Controller class
 *
 * @author liber
 */
public class ActividadesController extends controlador implements Initializable {

    private FilteredList<Actividad> actividadesFiltradas;

    @FXML
    private MenuItem btnsalir;
    @FXML
    private TextField txtBuscar;
    @FXML
    private ComboBox<String> cmbCategoria;
    @FXML
    private ComboBox<String> cmbEstado;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnNueva;
    @FXML
    private TableView<Actividad> tblActividades;
    @FXML
    private TableColumn<Actividad, String> colActividad;
    @FXML
    private TableColumn<Actividad, String> colCategoria;
    @FXML
    private TableColumn<Actividad, LocalDate> colFecha;
    @FXML
    private TableColumn<Actividad, String> colPlazas;
    @FXML
    private TableColumn<Actividad, String> colEstado;
    @FXML
    private Label lblEstado;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblEstado.setText("Pestaña activa: Todas");

        cmbCategoria.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nueva) -> {
            lblEstado.setText("Pestaña activa: " + nueva);
            aplicarFiltros();
        });

        txtBuscar.textProperty().addListener((obs, anterior, actual) -> aplicarFiltros());

        colActividad.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colPlazas.setCellValueFactory(celda -> new SimpleStringProperty(
                celda.getValue().getPlazasOcupadas() + " / " + celda.getValue().getPlazasTotales()));

        tblActividades.setItems(App.getDatos().getActividades());
        
        actividadesFiltradas = new FilteredList<>(App.getDatos().getActividades());
        tblActividades.setItems(actividadesFiltradas);

        ObservableList<Actividad> actividades
                = App.getDatos().getActividades();
        cmbCategoria.getItems().add("Todas");
        for (int i = 0; i < actividades.size(); i++) {
            Actividad actividad = actividades.get(i);
            String categoria = actividad.getCategoria();
            if (!cmbCategoria.getItems().contains(categoria)) {
                cmbCategoria.getItems().add(categoria);
            }
        }
        cmbCategoria.setValue("Todas");

    }

    private void aplicarFiltros() {
        if (actividadesFiltradas == null) {
            return;
        }

        String texto = txtBuscar.getText();
        String buscado = (texto == null) ? "" : texto.trim().toLowerCase();
        String categoria = cmbCategoria.getValue();
        boolean todasCategorias = (categoria == null || categoria.equals("Todas"));

        actividadesFiltradas.setPredicate(a
                -> (buscado.isEmpty() || a.getNombre().toLowerCase().contains(buscado))
                && (todasCategorias || a.getCategoria().equals(categoria)));
    }

    

    @FXML
    private void onBuscar(ActionEvent event) {
        List<String> criterios = new ArrayList<>();

        String texto = txtBuscar.getText().trim();
        if (!texto.isEmpty()) {
            criterios.add("Texto: " + texto);
        }

        String categoria = cmbCategoria.getValue();
        if (categoria != null && !categoria.equals("Todas")) {
            criterios.add("Categoría: " + categoria);
        }

        String estado = cmbEstado.getValue();
        if (estado != null && !estado.equals("Todos")) {
            criterios.add("Estado: " + estado);
        }

        String mensaje;
        if (criterios.isEmpty()) {
            mensaje = "Se mostrarían todas las actividades.";
        } else {
            mensaje = "Criterios de búsqueda:\n" + String.join("\n", criterios);
        }

        Alert resultado = new Alert(Alert.AlertType.INFORMATION, mensaje);
        resultado.setTitle("Buscar actividades");
        resultado.setHeaderText(null);
        resultado.showAndWait();
    }

    @FXML
    private void onNuevaActividad(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/dialogo.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Nueva actividad ");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void onEditar(ActionEvent event) {

        Actividad actividad
                = tblActividades.getSelectionModel().getSelectedItem();
        Dialog<ButtonType> dialogo = new Dialog<>();
        dialogo.setTitle("Editar actividad");
        dialogo.getDialogPane().getButtonTypes().addAll(
                ButtonType.OK, ButtonType.CANCEL);
        TextField txtNombre = new TextField(actividad.getNombre());
        TextField txtCategoria = new TextField(actividad.getCategoria());
        DatePicker dpFecha = new DatePicker(actividad.getFecha());
        GridPane panel = new GridPane();
        panel.setHgap(10);
        panel.setVgap(10);
        panel.add(new Label("Nombre:"), 0, 0);
        panel.add(txtNombre, 1, 0);
        panel.add(new Label("Categoría:"), 0, 1);
        panel.add(txtCategoria, 1, 1);
        panel.add(new Label("Fecha:"), 0, 2);
        panel.add(dpFecha, 1, 2);
        dialogo.getDialogPane().setContent(panel);
        Optional<ButtonType> respuesta = dialogo.showAndWait();
        if (respuesta.isPresent()
                && respuesta.get() == ButtonType.OK) {
            actividad.setNombre(txtNombre.getText());
            actividad.setCategoria(txtCategoria.getText());
            actividad.setFecha(dpFecha.getValue());
            tblActividades.refresh();
        }
    }

    @FXML
    private void onEliminar(ActionEvent event) {
        Actividad seleccionada = tblActividades.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Eliminar actividad");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Eliminar " + seleccionada.getNombre() + "?");
        confirmacion.initOwner(tblActividades.getScene().getWindow());

        Optional<ButtonType> respuesta = confirmacion.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            App.getDatos().getActividades().remove(seleccionada);
        }
    }

}
