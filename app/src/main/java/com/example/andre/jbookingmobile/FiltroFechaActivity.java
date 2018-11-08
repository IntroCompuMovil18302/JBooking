package com.example.andre.jbookingmobile;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class FiltroFechaActivity extends AppCompatActivity {

    private MCalendarView calendarView;
    private Toolbar toolbar;
    private int contadorTouch;
    private Date fechaInicio;
    private Date fechaFin;
    private Button buttonFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_fecha);

        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        calendarView = findViewById(R.id.calendarFiltroFecha);
        buttonFiltro = findViewById(R.id.buttonFiltroFechaListo);
        contadorTouch = 0;

        initEvents();

    }

    private void initEvents(){
        calendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                actualizarCalendario(date);
            }
        });
        buttonFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fechaInicio != null && fechaFin != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("fecha1",(Serializable)fechaInicio);
                    bundle.putSerializable("fecha2",(Serializable)fechaFin);
                }else{
                    Toast.makeText(FiltroFechaActivity.this,"Debes seleccionar las fechas",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
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
