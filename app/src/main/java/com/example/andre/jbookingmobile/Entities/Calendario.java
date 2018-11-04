package com.example.andre.jbookingmobile.Entities;

import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calendario implements Serializable {
    private List < Pair<Date,Date> > fechasOcupadas;

    public Calendario() {
        fechasOcupadas = new ArrayList<>();
    }

    public Calendario(List<Pair<Date, Date>> fechasOcupadas) {
        this.fechasOcupadas = fechasOcupadas;
    }

    public List<Pair<Date, Date>> getFechasOcupadas() {
        return fechasOcupadas;
    }

    public void setFechasOcupadas(List<Pair<Date, Date>> fechasOcupadas) {
        this.fechasOcupadas = fechasOcupadas;
    }

    public boolean insertarFechaOcupada(Date inicio, Date fin){
        //TODO: Implementar
        return true;
    }

    public boolean consultarDisponibilidad(Date inicio, Date fin){
        //TODO: Implementar
        return true;
    }
}
