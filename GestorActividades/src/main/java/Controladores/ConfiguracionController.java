/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import javafx.scene.paint.Color;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;

/**
 * FXML Controller class
 *
 * @author liber
 */
public class ConfiguracionController extends controlador implements Initializable {

    @FXML
    private ComboBox<?> cmbTema;
    @FXML
    private Slider sliderFuente;
    @FXML
    private ColorPicker cpColor;
    @FXML
    private Spinner<?> spZoom;
    @FXML
    private CheckBox chkImagenes;
    @FXML
    private CheckBox chkAnimaciones;
    @FXML
    private CheckBox chkDescripciones;
    @FXML
    private CheckBox chkConsejos;
    @FXML
    private CheckBox chkCompacto;
    @FXML
    private ComboBox<?> cmbIdioma;
    @FXML
    private Button btnRestablecer;
    @FXML
    private Button btnGuardar;
    @FXML
    private MenuItem btnsalir;

    private int temaInicialIndice;
    private double fuenteInicial;
    private Color colorInicial;
    private boolean imagenesInicial;
    private boolean animacionesInicial;
    private boolean descripcionesInicial;
    private boolean consejosInicial;
    private boolean compactoInicial;
    @FXML
    private Label lblFuente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTema.getSelectionModel().selectFirst();

        lblFuente.setText(formatoFuente(sliderFuente.getValue()));
        sliderFuente.valueProperty().addListener((obs, anterior, nuevo)
                -> lblFuente.setText(formatoFuente(nuevo.doubleValue())));

        temaInicialIndice = cmbTema.getSelectionModel().getSelectedIndex();
        fuenteInicial = sliderFuente.getValue();
        colorInicial = cpColor.getValue();
        imagenesInicial = chkImagenes.isSelected();
        animacionesInicial = chkAnimaciones.isSelected();
        descripcionesInicial = chkDescripciones.isSelected();
        consejosInicial = chkConsejos.isSelected();
        compactoInicial = chkCompacto.isSelected();
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        String resumen = "Tema: " + cmbTema.getValue() + "\n"
                + "Tamaño de fuente: " + Math.round(sliderFuente.getValue()) + " px\n"
                + "Color principal: " + colorHex(cpColor.getValue()) + "\n"
                + "Mostrar imágenes: " + siNo(chkImagenes) + "\n"
                + "Animaciones: " + siNo(chkAnimaciones) + "\n"
                + "Descripciones completas: " + siNo(chkDescripciones) + "\n"
                + "Consejos de ayuda: " + siNo(chkConsejos) + "\n"
                + "Diseño compacto: " + siNo(chkCompacto);

        Alert resumenAlert = new Alert(Alert.AlertType.INFORMATION, resumen);
        resumenAlert.setTitle("Resumen de configuración");
        resumenAlert.setHeaderText("Valores actuales");
        resumenAlert.showAndWait();
    }

    @FXML
    private void onRestablecer(ActionEvent event) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "Se recuperarán los valores iniciales. ¿Quieres continuar?",
                ButtonType.YES, ButtonType.NO);
        confirmacion.setTitle("Restablecer configuración");
        confirmacion.setHeaderText(null);

        Optional<ButtonType> respuesta = confirmacion.showAndWait();
        if (respuesta.isEmpty() || respuesta.get() != ButtonType.YES) {
            return;
        }

        cmbTema.getSelectionModel().select(temaInicialIndice);
        sliderFuente.setValue(fuenteInicial);
        cpColor.setValue(colorInicial);
        chkImagenes.setSelected(imagenesInicial);
        chkAnimaciones.setSelected(animacionesInicial);
        chkDescripciones.setSelected(descripcionesInicial);
        chkConsejos.setSelected(consejosInicial);
        chkCompacto.setSelected(compactoInicial);
    }

    private String formatoFuente(double valor) {
        return "Tamaño: " + Math.round(valor) + " px";
    }

    private String siNo(CheckBox casilla) {
        return casilla.isSelected() ? "Sí" : "No";
    }

    private String colorHex(Color color) {
        return String.format("#%02X%02X%02X",
                Math.round(color.getRed() * 255),
                Math.round(color.getGreen() * 255),
                Math.round(color.getBlue() * 255));

    }

}
