package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Alojamiento implements Serializable {
    private String id;

    private String nombre;
    private String descripcion;

    private String tipo;
    private String tipoPropiedad;
    private String disposicion;

    private int huespedes;
    private int dormitorios;
    private int camas;

    private int banhos;
    private boolean banhosPriv;

    private String servicios;
    private String serviciosSec;


    private double valorNoche;
    private String fotos;
    private Ubicacion ubicacion;

    private Anfitrion anfitrion;
    private String anfitrionId;

    private Calendario calendario;
    private List<Comentario> comentarios;
    private List<Reserva> reservas;

    public Alojamiento() {
        comentarios = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    public Alojamiento(String id, String tipo, double valorNoche, String fotos, Ubicacion ubicacion, Anfitrion anfitrion, Calendario calendario, List<Comentario> comentarios, List<Reserva> reservas) {
        this.id = id;
        this.tipo = tipo;
        this.valorNoche = valorNoche;
        this.fotos = fotos;
        this.ubicacion = ubicacion;
        this.anfitrion = anfitrion;
        this.calendario = calendario;
        this.comentarios = comentarios;
        this.reservas = reservas;
    }

    public Alojamiento(String id, String tipo, double valorNoche, String fotos, Ubicacion ubicacion, Anfitrion anfitrion, Calendario calendario, List<Comentario> comentarios, List<Reserva> reservas, String anfitrionId) {
        this.id = id;
        this.tipo = tipo;
        this.valorNoche = valorNoche;
        this.fotos = fotos;
        this.ubicacion = ubicacion;
        this.anfitrion = anfitrion;
        this.calendario = calendario;
        this.comentarios = comentarios;
        this.reservas = reservas;
        this.anfitrionId = anfitrionId;
    }

    public Alojamiento(String id, String nombre, String descripcion, String tipo, String tipoPropiedad, String disposicion, int huespedes, int dormitorios, int camas, int banhos, boolean banhosPriv, String servicios, String serviciosSec, double valorNoche, String fotos, Ubicacion ubicacion, Anfitrion anfitrion, String anfitrionId, Calendario calendario, List<Comentario> comentarios, List<Reserva> reservas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.tipoPropiedad = tipoPropiedad;
        this.disposicion = disposicion;
        this.huespedes = huespedes;
        this.dormitorios = dormitorios;
        this.camas = camas;
        this.banhos = banhos;
        this.banhosPriv = banhosPriv;
        this.servicios = servicios;
        this.serviciosSec = serviciosSec;
        this.valorNoche = valorNoche;
        this.fotos = fotos;
        this.ubicacion = ubicacion;
        this.anfitrion = anfitrion;
        this.anfitrionId = anfitrionId;
        this.calendario = calendario;
        this.comentarios = comentarios;
        this.reservas = reservas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getServiciosSec() {
        return serviciosSec;
    }

    public void setServiciosSec(String serviciosSec) {
        this.serviciosSec = serviciosSec;
    }

    public int getDormitorios() {
        return dormitorios;
    }

    public void setDormitorios(int dormitorios) {
        this.dormitorios = dormitorios;
    }

    public int getCamas() {
        return camas;
    }

    public void setCamas(int camas) {
        this.camas = camas;
    }

    public String getTipoPropiedad() {
        return tipoPropiedad;
    }

    public void setTipoPropiedad(String tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }

    public String getDisposicion() {
        return disposicion;
    }

    public void setDisposicion(String disposicion) {
        this.disposicion = disposicion;
    }

    public int getHuespedes() {
        return huespedes;
    }

    public void setHuespedes(int huespedes) {
        this.huespedes = huespedes;
    }

    public int getBanhos() {
        return banhos;
    }

    public void setBanhos(int banhos) {
        this.banhos = banhos;
    }

    public boolean isBanhosPriv() {
        return banhosPriv;
    }

    public void setBanhosPriv(boolean banhosPriv) {
        this.banhosPriv = banhosPriv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getAnfitrionId() {
        return anfitrionId;
    }

    public void setAnfitrionId(String anfitrionId) {
        this.anfitrionId = anfitrionId;
    }
}
