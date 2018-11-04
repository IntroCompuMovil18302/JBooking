package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Huesped extends Usuario implements Serializable {
    private String genero;
    private String nacionalidad;
    private int puntos;

    public Huesped() {
    }

    public Huesped(int id, String nombre, Date fechaNacimiento, String foto, String correo, List<Reserva> reservas, String genero, String nacionalidad, int puntos) {
        super(id, nombre, fechaNacimiento, foto, correo, reservas);
        this.genero = genero;
        this.nacionalidad = nacionalidad;
        this.puntos = puntos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
