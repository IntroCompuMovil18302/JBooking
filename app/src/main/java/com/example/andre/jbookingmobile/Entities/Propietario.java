package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Propietario extends Usuario  implements Serializable {
    private List<Lugar> lugar;

    public Propietario() {
    }

    public Propietario(String id, String nombre, Date fechaNacimiento, String foto, String correo, List<Reserva> reservas, List<Lugar> lugar) {
        super(id, nombre, fechaNacimiento, foto, correo, reservas);
        this.lugar = lugar;
    }

    public List<Lugar> getLugar() {
        return lugar;
    }

    public void setLugar(List<Lugar> lugar) {
        this.lugar = lugar;
    }
}
