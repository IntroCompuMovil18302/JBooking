package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;
import java.util.Date;

public class Reserva implements Serializable {
    private String id;
    private Alojamiento alojamiento;
    private String alojamientoId;
    private Usuario usuario;
    private String usuarioId;
    private Date fechaInicio;
    private Date fechaFin;
    private double valor;
    private String tipo;

    public Reserva() {
    }

    public Reserva(String id, Alojamiento alojamiento, String alojamientoId, Usuario usuario, String usuarioId, Date fechaInicio, Date fechaFin, double valor, String tipo) {
        this.id = id;
        this.alojamiento = alojamiento;
        this.alojamientoId = alojamientoId;
        this.usuario = usuario;
        this.usuarioId = usuarioId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getAlojamientoId() {
        return alojamientoId;
    }

    public void setAlojamientoId(String alojamientoId) {
        this.alojamientoId = alojamientoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
}
