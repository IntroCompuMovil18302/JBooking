package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;

public class Comentario implements Serializable {
    private String id;
    private int puntuacion;
    private String nombreUsuario;
    private String comentario;

    private Alojamiento alojamiento;
    private String alojamientoId;

    private Lugar lugar;
    private String lugarId;

    public Comentario() {
    }

    public Comentario(String id, int puntuacion, String nombreUsuario, String comentario) {
        this.id = id;
        this.puntuacion = puntuacion;
        this.nombreUsuario = nombreUsuario;
        this.comentario = comentario;
    }

    public Comentario(String id, int puntuacion, String nombreUsuario, String comentario, Alojamiento alojamiento, String alojamientoId, Lugar lugar, String lugarId) {
        this.id = id;
        this.puntuacion = puntuacion;
        this.nombreUsuario = nombreUsuario;
        this.comentario = comentario;
        this.alojamiento = alojamiento;
        this.alojamientoId = alojamientoId;
        this.lugar = lugar;
        this.lugarId = lugarId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
    }

    public String getAlojamientoId() {
        return alojamientoId;
    }

    public void setAlojamientoId(String alojamientoId) {
        this.alojamientoId = alojamientoId;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public String getLugarId() {
        return lugarId;
    }

    public void setLugarId(String lugarId) {
        this.lugarId = lugarId;
    }
}
