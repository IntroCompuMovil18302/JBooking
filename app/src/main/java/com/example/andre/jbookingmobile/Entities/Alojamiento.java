package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Alojamiento implements Serializable {
    private int id;
    private String nombre;
    private String tipo;
    private double valorNoche;
    private String fotos;
    private Ubicacion ubicacion;
    private Anfitrion anfitrion;
    private Calendario calendario;
    private List<Comentario> comentarios;
    private List<Reserva> reservas;

    public Alojamiento() {
        comentarios = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    public Alojamiento(int id,String nombre, String tipo, double valorNoche, String fotos, Ubicacion ubicacion, Anfitrion anfitrion, Calendario calendario, List<Comentario> comentarios, List<Reserva> reservas) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.valorNoche = valorNoche;
        this.fotos = fotos;
        this.ubicacion = ubicacion;
        this.anfitrion = anfitrion;
        this.calendario = calendario;
        this.comentarios = comentarios;
        this.reservas = reservas;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValorNoche() {
        return valorNoche;
    }

    public void setValorNoche(double valorNoche) {
        this.valorNoche = valorNoche;
    }

    public String getFotos() {
        return fotos;
    }

    public void setFotos(String fotos) {
        this.fotos = fotos;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Anfitrion getAnfitrion() {
        return anfitrion;
    }

    public void setAnfitrion(Anfitrion anfitrion) {
        this.anfitrion = anfitrion;
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
