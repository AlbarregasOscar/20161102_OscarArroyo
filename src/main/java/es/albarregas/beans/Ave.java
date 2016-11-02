/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.beans;

import java.io.Serializable;

/**
 *
 * @author Oscar
 */
public class Ave implements Serializable{
    private String anilla;
    private String especie;
    private String lugar;
    private String fecha;

    public void setAnilla(String anilla) {
        this.anilla = anilla;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAnilla() {
        return anilla;
    }

    public String getEspecie() {
        return especie;
    }

    public String getLugar() {
        return lugar;
    }

    public String getFecha() {
        return fecha;
    }
    
}
