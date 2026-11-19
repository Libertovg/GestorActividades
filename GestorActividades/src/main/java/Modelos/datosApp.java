/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author liber
 */
public class datosApp {

    private void crearActividadesIniciales() {
        getActividades().addAll(
                new Actividad(1, "Taller de JavaFX", "Tecnológica", LocalDate.of(2026, 11, 12), 20, 8, "Abierta"),
                new Actividad(2, "Taller de Teatro", "Cultural", LocalDate.of(2026, 11, 18), 25, 12, "Abierta"),
                new Actividad(3, "Torneo de Fútbol Sala", "Deportiva", LocalDate.of(2026, 11, 21), 16, 16, "Completa"),
                new Actividad(4, "Voluntariado Ambiental", "Solidaria", LocalDate.of(2026, 11, 27), 30, 5, "Abierta"),
                new Actividad(5, "Concurso de Fotografía", "Cultural", LocalDate.of(2026, 12, 2), 20, 9, "Abierta"),
                new Actividad(6, "Robótica educativa", "Tecnológica", LocalDate.of(2026, 12, 10), 15, 0, "Abierta"));
    }

    public datosApp() {
        crearActividadesIniciales();
    }

    private ObservableList<Actividad> actividades = FXCollections.observableArrayList();

    /**
     * @return the actividades
     */
    public ObservableList<Actividad> getActividades() {
        return actividades;
    }

    /**
     * @param actividades the actividades to set
     */
    public void setActividades(ObservableList<Actividad> actividades) {
        this.actividades = actividades;
    }

}
