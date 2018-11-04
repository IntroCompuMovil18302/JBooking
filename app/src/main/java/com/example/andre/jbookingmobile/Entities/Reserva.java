package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;
import java.util.Date;

public class Reserva implements Serializable {
    private int id;
    private Alojamiento alojamiento;
    private Usuario usuario;
    private Date fechaInicio;
    private Date fechaFin;
    private double valor;
    private String tipo;

    public Reserva() {
    }

    public Reserva(int id, Alojamiento alojamiento, Usuario usuario, Date fechaInicio, Date fechaFin, double valor, String tipo) {
        this.id = id;
        this.alojamiento = alojamiento;
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.valor = valor;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
