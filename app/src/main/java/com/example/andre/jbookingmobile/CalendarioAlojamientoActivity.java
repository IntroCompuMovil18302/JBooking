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
    private Date fechaInicio;
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
                actualizarCalendario(date);
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

    private void actualizarCalendario(DateData date){
        Calendar calendar0 = Calendar.getInstance();
        calendar0.set(Calendar.YEAR,date.getYear());
        calendar0.set(Calendar.MONTH,date.getMonth()-1);
        calendar0.set(Calendar.DAY_OF_MONTH,date.getDay());
        if (contadorTouch == 0){
            ++contadorTouch;

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, date.getYear());
            calendar.set(Calendar.MONTH, date.getMonth()-1);
            calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
            fechaInicio = calendar.getTime();

            calendarView.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#37bad6"));
            calendarView.markDate(new DateData(date.getYear(),date.getMonth(),date.getDay()));
        }else if (contadorTouch == 1){
            if (fechaIgual(fechaInicio, calendar0.getTime()) == 0){
                // se deja todo igual
            }else if(fechaIgual(fechaInicio, calendar0.getTime()) > 0){
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(fechaInicio);
                calendarView.unMarkDate(calendar1.get(Calendar.YEAR),calendar1.get(Calendar.MONTH)+1,calendar1.get(Calendar.DAY_OF_MONTH));

                Calendar calendar2 = Calendar.getInstance();
                calendar2.set(Calendar.YEAR, date.getYear());
                calendar2.set(Calendar.MONTH, date.getMonth()-1);
                calendar2.set(Calendar.DAY_OF_MONTH, date.getDay());
                fechaInicio = calendar2.getTime();

                calendarView.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#37bad6"));
                calendarView.markDate(date);
            }else{
                ++contadorTouch;

                Calendar calendar3 = Calendar.getInstance();
                calendar3.set(Calendar.YEAR, date.getYear());
                calendar3.set(Calendar.MONTH, date.getMonth()-1);
                calendar3.set(Calendar.DAY_OF_MONTH, date.getDay());
                fechaFin = calendar3.getTime();

                calendarView.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#37bad6"));
                calendarView.markDate(date);

                dibujarSegmento(fechaInicio,fechaFin);
            }
        }else if (contadorTouch == 2){
            contadorTouch = 1;

            limpiarSegmento(fechaInicio,fechaFin);

            fechaFin = null;

            Calendar calendar4 = Calendar.getInstance();
            calendar4.set(Calendar.YEAR, date.getYear());
            calendar4.set(Calendar.MONTH, date.getMonth()-1);
            calendar4.set(Calendar.DAY_OF_MONTH, date.getDay());

            fechaInicio = calendar4.getTime();

            calendarView.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#37bad6"));
            calendarView.markDate(date);
        }
    }

    private long fechaIgual(Date fechaInicio, Date data){
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaInicio);
        Calendar c2 =  Calendar.getInstance();
        c2.setTime(data);
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)){
            return 0;
        }
        return fechaInicio.getTime() - data.getTime();
    }

    private void dibujarSegmento(Date f1, Date f2){
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(f1);
        calendar2.setTime(f2);

        calendar1.add(Calendar.DATE,1);

        while(fechaIgual(calendar1.getTime(),calendar2.getTime()) < 0){
            calendarView.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#37bad6"));
            calendarView.markDate(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH)+1, calendar1.get(Calendar.DAY_OF_MONTH));
            calendar1.add(Calendar.DATE,1);
        }
    }

    private void limpiarSegmento(Date f1, Date f2){
        List<DateData> diasPintados = new ArrayList<>();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(f1);
        calendar2.setTime(f2);

        while (fechaIgual(calendar1.getTime(),calendar2.getTime()) <= 0){
            calendarView.unMarkDate(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH)+1, calendar1.get(Calendar.DAY_OF_MONTH));
            calendar1.add(Calendar.DATE,1);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        limpiarCalendario();
    }

    private void limpiarCalendario(){
        ArrayList<DateData> marcadas = (ArrayList)calendarView.getMarkedDates().getAll().clone();
        for (int i = 0; i<marcadas.size();i++){
            calendarView.unMarkDate(marcadas.get(i));
        }
    }
}




