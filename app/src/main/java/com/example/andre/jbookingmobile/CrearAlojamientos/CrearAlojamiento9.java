package com.example.andre.jbookingmobile.CrearAlojamientos;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Calendario;
import com.example.andre.jbookingmobile.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class CrearAlojamiento9 extends AppCompatActivity {

    private MCalendarView calendarViewAlj;
    private Toolbar toolbar;
    private Button nextCrear9;
    private FloatingActionButton addCal, lessCal;
    private Alojamiento myAlj;
    private Date fechaInicioTemp;
    private Date fechaFinTemp;
    private int contadorTouch;
    private ArrayList<Date> fechasoDisponibles;
    private Calendario calendario;
    private LinearLayout disponibilidades;
    private List<Pair<Calendar, Calendar>> guardados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alojamiento9);
        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initElementos();
        initEventos();
    }

    private void initElementos() {
        addCal = findViewById(R.id.butAddCal);
        lessCal = findViewById(R.id.butDelCal);
        calendarViewAlj = findViewById(R.id.calendarCrearAlojamiento);
        //edPrec=findViewById(R.id.eTPrecNum);
        contadorTouch = 0;
        nextCrear9 = findViewById(R.id.butAloj9);
        disponibilidades = findViewById(R.id.listDispo);
        calendario = new Calendario();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        myAlj = (Alojamiento) bundle.getSerializable("ALOJ");
        fechasoDisponibles = new ArrayList<>();
        guardados = new ArrayList<Pair<Calendar, Calendar>>();
    }


    private void initEventos() {

        addCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTOLIST();
            }
        });
        lessCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeLIST();
            }
        });
        nextCrear9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarForma()) {

                    myAlj.setCalendario(calendario);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ALOJ", myAlj);
                    Intent intent = new Intent(CrearAlojamiento9.this, CrearAlojamiento4_1.class);
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);
                }

            }
        });
        calendarViewAlj.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                actualizarCalendario(date);
            }
        });
    }

    private void removeLIST() {
        if (myAlj.getCalendario().isEmpty()) {
            return;
        } else {
            fechasoDisponibles = (ArrayList<Date>) myAlj.getCalendario().getFechasDisponibles();
        }
    }

    private void addTOLIST() {
        if (!fechasoDisponibles.isEmpty()) {
            if (myAlj.getCalendario().isEmpty()) {
                fechasoDisponibles = new ArrayList<Date>();
            }
        }
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(fechaInicioTemp);
        calendar2.setTime(fechaFinTemp);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format12 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(calendar1.getTime());
        String rangeTxt = formatted + " < _ > " + format12.format(calendar2.getTime());
        String rangeTxt1 = format1.format(fechaInicioTemp.getTime()) + " < _ > " + format12.format(fechaFinTemp.getTime());
        System.out.println(rangeTxt);
        System.out.println(rangeTxt1);
        TextView tempTV = new TextView(this);
        tempTV.setText(rangeTxt1);
        tempTV.setTextSize((float) 20.0);
        tempTV.setLayoutParams(params);

        disponibilidades.addView(tempTV);
        calendar1.setTime(fechaInicioTemp);
        calendar2.setTime(fechaFinTemp);
        guardados.add(new Pair<>(calendar1, calendar2));

        for (Pair<Calendar, Calendar> cal : guardados) {
            System.out.println(cal.first.getTime());
            System.out.println(cal.second.getTime() + "\n");
        }

        System.out.println("disponiblesss    ----------------------");
        for (Date fc : fechasoDisponibles) {

            System.out.println(fc.toString());
        }
        System.out.println("disponiblesss    ----------------------");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }

    private void foo() {

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        for (Pair<Calendar, Calendar> cal : guardados) {
            System.out.println(cal.first.getTime());
            System.out.println(cal.second.getTime() + "\n");
            calendar1 = cal.first;
            calendar2 = cal.second;
            calendar1.add(Calendar.DATE, 1);
            fechasoDisponibles.add(calendar1.getTime());
            while (fechaIgual(calendar1.getTime(), calendar2.getTime()) < 0) {
                fechasoDisponibles.add(calendar1.getTime());
                calendar1.add(Calendar.DATE, 1);
            }
        }
        calendario.setFechasDisponibles(fechasoDisponibles);
    }

    private boolean validarForma() {
      foo();
        return true;
    }


    private void actualizarCalendario(DateData date) {
        Calendar calendar0 = Calendar.getInstance();
        calendar0.set(Calendar.YEAR, date.getYear());
        calendar0.set(Calendar.MONTH, date.getMonth() - 1);
        calendar0.set(Calendar.DAY_OF_MONTH, date.getDay());
        if (contadorTouch == 0) {
            ++contadorTouch;

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, date.getYear());
            calendar.set(Calendar.MONTH, date.getMonth() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
            fechaInicioTemp = calendar.getTime();

            calendarViewAlj.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#37bad6"));
            calendarViewAlj.markDate(new DateData(date.getYear(), date.getMonth(), date.getDay()));
        } else if (contadorTouch == 1) {
            if (fechaIgual(fechaInicioTemp, calendar0.getTime()) == 0) {
                // se deja todo igual
            } else if (fechaIgual(fechaInicioTemp, calendar0.getTime()) > 0) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(fechaInicioTemp);
                calendarViewAlj.unMarkDate(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH) + 1, calendar1.get(Calendar.DAY_OF_MONTH));

                Calendar calendar2 = Calendar.getInstance();
                calendar2.set(Calendar.YEAR, date.getYear());
                calendar2.set(Calendar.MONTH, date.getMonth() - 1);
                calendar2.set(Calendar.DAY_OF_MONTH, date.getDay());
                fechaInicioTemp = calendar2.getTime();

                calendarViewAlj.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#37bad6"));
                calendarViewAlj.markDate(date);
            } else {
                ++contadorTouch;

                Calendar calendar3 = Calendar.getInstance();
                calendar3.set(Calendar.YEAR, date.getYear());
                calendar3.set(Calendar.MONTH, date.getMonth() - 1);
                calendar3.set(Calendar.DAY_OF_MONTH, date.getDay());
                fechaFinTemp = calendar3.getTime();

                calendarViewAlj.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#37bad6"));
                calendarViewAlj.markDate(date);

                dibujarSegmento(fechaInicioTemp, fechaFinTemp);
            }
        } else if (contadorTouch == 2) {
            contadorTouch = 1;

            limpiarSegmento(fechaInicioTemp, fechaFinTemp);

            fechaFinTemp = null;

            Calendar calendar4 = Calendar.getInstance();
            calendar4.set(Calendar.YEAR, date.getYear());
            calendar4.set(Calendar.MONTH, date.getMonth() - 1);
            calendar4.set(Calendar.DAY_OF_MONTH, date.getDay());

            fechaInicioTemp = calendar4.getTime();

            calendarViewAlj.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#37bad6"));
            calendarViewAlj.markDate(date);
        }
    }

    private long fechaIgual(Date fechaInicio, Date data) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaInicio);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(data);
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
            return 0;
        }
        return fechaInicio.getTime() - data.getTime();
    }

    private void dibujarSegmento(Date f1, Date f2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(f1);
        calendar2.setTime(f2);

        calendar1.add(Calendar.DATE, 1);

        while (fechaIgual(calendar1.getTime(), calendar2.getTime()) < 0) {
            calendarViewAlj.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#37bad6"));
            calendarViewAlj.markDate(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH) + 1, calendar1.get(Calendar.DAY_OF_MONTH));
            calendar1.add(Calendar.DATE, 1);
        }
    }

    private void limpiarSegmento(Date f1, Date f2) {
        List<DateData> diasPintados = new ArrayList<>();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(f1);
        calendar2.setTime(f2);

        while (fechaIgual(calendar1.getTime(), calendar2.getTime()) <= 0) {
            calendarViewAlj.unMarkDate(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH) + 1, calendar1.get(Calendar.DAY_OF_MONTH));
            calendar1.add(Calendar.DATE, 1);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        limpiarCalendario();
    }

    private void limpiarCalendario() {
        ArrayList<DateData> marcadas = (ArrayList) calendarViewAlj.getMarkedDates().getAll().clone();
        for (int i = 0; i < marcadas.size(); i++) {
            calendarViewAlj.unMarkDate(marcadas.get(i));
        }
    }

}
