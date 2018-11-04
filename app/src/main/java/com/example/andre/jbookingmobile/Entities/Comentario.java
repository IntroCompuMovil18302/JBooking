package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;

public class Comentario implements Serializable {
    private int id;
    private int puntuacion;
    private String nombreUsuario;
    private String comentario;

    public Comentario() {
    }

    public Comentario(int id, int puntuacion, String nombreUsuario, String comentario) {
        this.id = id;
        this.puntuacion = puntuacion;
        this.nombreUsuario = nombreUsuario;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
