package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Propietario extends Usuario  implements Serializable {
    private Lugar lugar;

    public Propietario() {
    }

    public Propietario(int id, String nombre, Date fechaNacimiento, String foto, String correo, List<Reserva> reservas, Lugar lugar) {
        super(id, nombre, fechaNacimiento, foto, correo, reservas);
        this.lugar = lugar;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }
}