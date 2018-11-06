package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Anfitrion extends Usuario implements Serializable {
    private List<Alojamiento> alojamiento;

    public Anfitrion() {
    }

    public Anfitrion(String id, String nombre, Date fechaNacimiento, String foto, String correo, List<Reserva> reservas, List<Alojamiento> alojamiento) {
        super(id, nombre, fechaNacimiento, foto, correo, reservas);
        this.alojamiento = alojamiento;
    }

    public List<Alojamiento> getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(List<Alojamiento> alojamiento) {
        this.alojamiento = alojamiento;
    }
}
