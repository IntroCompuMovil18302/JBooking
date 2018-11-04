package com.example.andre.jbookingmobile.Entities;

import java.io.Serializable;

public class Ubicacion implements Serializable {
    private double latitud;
    private double longitud;
    private double altitud;

    public Ubicacion() {
    }

    public Ubicacion(double latitud, double longitud, double altitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getAltitud() {
        return altitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }
}
