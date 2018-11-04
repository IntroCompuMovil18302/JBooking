package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lugar implements Serializable {
    private int id;
    private String nombre;
    private Propietario propietario;
    private String fotos;
    private Ubicacion ubicacion;
    private List<Comentario> comentarios;

    public Lugar() {
        comentarios = new ArrayList<>();
    }

    public Lugar(int id, String nombre, Propietario propietario, String fotos, Ubicacion ubicacion, List<Comentario> comentarios) {
        this.id = id;
        this.nombre = nombre;
        this.propietario = propietario;
        this.fotos = fotos;
        this.ubicacion = ubicacion;
        this.comentarios = comentarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
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

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
