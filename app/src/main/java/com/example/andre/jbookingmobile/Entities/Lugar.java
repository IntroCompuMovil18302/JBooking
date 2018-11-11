package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lugar implements Serializable {
    private String id;
    private String nombre;
    private String descripcion;
    private Propietario propietario;
    private String propietarioId;
    private String fotos;
    private Ubicacion ubicacion;
    private List<Comentario> comentarios;

    public Lugar() {
        comentarios = new ArrayList<>();
    }

    public Lugar(String id, String nombre, String descripcion, Propietario propietario, String propietarioId, String fotos, Ubicacion ubicacion, List<Comentario> comentarios) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.propietario = propietario;
        this.propietarioId = propietarioId;
        this.fotos = fotos;
        this.ubicacion = ubicacion;
        this.comentarios = comentarios;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(String propietarioId) {
        this.propietarioId = propietarioId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
