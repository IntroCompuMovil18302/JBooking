package com.example.andre.jbookingmobile;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Calendario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.views.BaseCellView;
import sun.bob.mcalendarview.vo.DateData;
import sun.bob.mcalendarview.vo.DayData;

public class CalendarioAlojamientoActivity extends AppCompatActivity {

    private MCalendarView calendarView;
    private Alojamiento alojamiento;
    private Toolbar toolbar;
    private Button buttonReservar;
    private int contadorTouch;
    private Date fechaInicion;
    private Date fechaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_alojamiento);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        alojamiento =  (Alojamiento)getIntent().getExtras().getSerializable("alojamiento");

        calendarView = findViewById(R.id.calendarAlojamiento);
        buttonReservar = findViewById(R.id.buttonCalendarioAlojamientoReservar);
        contadorTouch = 0;
        initEvents();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }

    private void initEvents(){
        Calendar today = Calendar.getInstance();
        int todayYear = today.get(Calendar.YEAR);
        int todayMonth = today.get(Calendar.MONTH);
        int todayDay = today.get(Calendar.DAY_OF_MONTH);
        //calendarView.travelTo(new DateData(todayYear,todayMonth+1,todayDay));
        marcarNoDisponibles();
        calendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                actualizarFechas(date);
            }
        });
    }

    private void marcarNoDisponibles(){
        Calendario calendario = alojamiento.getCalendario();
        List<Date> fechasOcupadas =  null;// = calendario.getFechasOcupadas();
        if (fechasOcupadas == null){
            fechasOcupadas =  new ArrayList<>();
        }
        Calendar fechita = Calendar.getInstance();
        fechita.set(Calendar.YEAR,2018);
        fechita.set(Calendar.MONTH,10);
        fechita.set(Calendar.DAY_OF_MONTH,6);
        fechasOcupadas.add(fechita.getTime());
        calendarView.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#E34444"));
        for (Date fecha: fechasOcupadas){
            Calendar diaActual = Calendar.getInstance();
            diaActual.setTime(fecha);
            calendarView.markDate(new DateData(diaActual.get(Calendar.YEAR),diaActual.get(Calendar.MONTH)+1,diaActual.get(Calendar.DAY_OF_MONTH)));
        }
    }

    private void actualizarFechas(DateData date){
        if(contadorTouch == 0){
            if(fechaDisponible(date)){
                ++contadorTouch;
                calendarView.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#44a4ff"));
                calendarView.markDate(new DateData(date.getYear(),date.getMonth(),date.getDay()));
            }else{
                Toast.makeText(CalendarioAlojamientoActivity.this,"Fecha no disponible",Toast.LENGTH_SHORT).show();
            }

        }else if (contadorTouch == 1){

        }
    }

    private boolean fechaDisponible(DateData data){
        List<Date> fechasOcupadas =  null;// = calendario.getFechasOcupadas();
        if (fechasOcupadas == null){
            fechasOcupadas =  new ArrayList<>();
        }
        Calendar fechita = Calendar.getInstance();
        fechita.set(Calendar.YEAR,2018);
        fechita.set(Calendar.MONTH,10);
        fechita.set(Calendar.DAY_OF_MONTH,6);
        fechasOcupadas.add(fechita.getTime());

        for (Date fechaActual: fechasOcupadas){
            Calendar calendarFecha = Calendar.getInstance();
            calendarFecha.setTime(fechaActual);
            if (calendarFecha.get(Calendar.YEAR) == data.getYear() && calendarFecha.get(Calendar.MONTH) == data.getMonth()-1 && calendarFecha.get(Calendar.DAY_OF_MONTH) == data.getDay()){
                return false;
            }
        }
        return true;
    }
}




