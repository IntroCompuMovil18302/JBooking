package com.example.andre.jbookingmobile.Entities;

import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calendario implements Serializable {
    private List<Date> fechasOcupadas;
    private List<Date> fechasDisponibles;

    public Calendario() {
        fechasOcupadas = new ArrayList<Date>();
    }

    public Calendario(List<Date> fechasOcupadas) {
        this.fechasOcupadas = fechasOcupadas;
    }

    public List<Date> getFechasOcupadas() {
        return fechasOcupadas;
    }

    public void setFechasOcupadas(List<Date> fechasOcupadas) {
        this.fechasOcupadas = fechasOcupadas;
    }

    public List<Date> getFechasDisponibles() {
        return fechasDisponibles;
    }

    public void setFechasDisponibles(List<Date> fechasDisponibles) {
        this.fechasDisponibles = fechasDisponibles;
    }

    public boolean insertarFechaOcupada(Date inicio, Date fin){
        //TODO: Implementar
        return true;
    }

    public boolean consultarDisponibilidad(Date inicio, Date fin) {
        if (fechasOcupadas != null) {
            for (Date d : fechasOcupadas) {
                if (d.getTime() >= inicio.getTime() && d.getTime() <= fin.getTime()) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    public boolean isEmpty(){
        if (this.fechasOcupadas.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
}
