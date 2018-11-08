package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Anfitrion extends Usuario implements Serializable {
    private Alojamiento alojamiento;

    public Anfitrion() {
    }

    public Anfitrion(String id, String nombre, Date fechaNacimiento, String foto, String correo, List<Reserva> reservas, Alojamiento alojamiento) {
        super(id, nombre, fechaNacimiento, foto, correo, reservas);
        this.alojamiento = alojamiento;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
    }
}
