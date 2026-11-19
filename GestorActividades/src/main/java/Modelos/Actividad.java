/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.time.LocalDate;

/**
 *
 * @author liber
 */
public class Actividad {

    private int id;
    private String nombre;
    private String categoria;
    private LocalDate fecha;
    private int plazasTotales;
    private int plazasOcupadas;
    private String estado;

    public Actividad(int id, String nombre, String categoria, LocalDate fecha, int plazasTotales, int plazasOcupadas, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.fecha = fecha;
        this.plazasTotales = plazasTotales;
        this.plazasOcupadas = plazasOcupadas;
        this.estado = estado;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the plazasTotales
     */
    public int getPlazasTotales() {
        return plazasTotales;
    }

    /**
     * @param plazasTotales the plazasTotales to set
     */
    public void setPlazasTotales(int plazasTotales) {
        this.plazasTotales = plazasTotales;
    }

    /**
     * @return the plazasOcupadas
     */
    public int getPlazasOcupadas() {
        return plazasOcupadas;
    }

    /**
     * @param plazasOcupadas the plazasOcupadas to set
     */
    public void setPlazasOcupadas(int plazasOcupadas) {
        this.plazasOcupadas = plazasOcupadas;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

}
