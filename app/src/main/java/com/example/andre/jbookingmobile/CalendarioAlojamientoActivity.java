package com.example.andre.jbookingmobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Calendario;
import com.example.andre.jbookingmobile.Entities.Huesped;
import com.example.andre.jbookingmobile.Entities.Reserva;
import com.example.andre.jbookingmobile.Entities.User;
import com.example.andre.jbookingmobile.Entities.Usuario;
import com.example.andre.jbookingmobile.Services.NotificationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Date limiteInferior;
    private Date limiteSuperior;
    private Date fechaInicio;
    private Date fechaFin;
    private List<Date> fechasNoDispo = new ArrayList<>();
    private FirebaseAuth mAuth;
    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database;
    DatabaseReference myRef;
    public final String PATH_RESERVAS = "reservas";
    public final String PATH_ALOJAMIENTO = "alojamientos";

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
        Log.i("Tagggg",alojamiento.getId()+"Hola");
        Log.i("Tagggg",alojamiento.getNombre());
        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        calendarView = findViewById(R.id.calendarAlojamiento);
        buttonReservar = findViewById(R.id.buttonCalendarioAlojamientoReservar);
        contadorTouch = 0;
        initEvents();
        database = FirebaseDatabase.getInstance();

        buttonReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarreserva();
            }
        });
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
        calendarView.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#37bad6"));
        //calendarView.setMarkedStyle(MarkStyle.BACKGROUND, Color.parseColor("#37bad6"));
        calendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                actualizarCalendario(date);
            }
        });
    }

    private boolean fechaUnicaValida(DateData date){
        for (Date dd : fechasNoDispo){
            Calendar cc = Calendar.getInstance();
            cc.setTime(dd);
            if (cc.get(Calendar.YEAR) == date.getYear() && cc.get(Calendar.MONTH)+1 == date.getMonth() && cc.get(Calendar.DAY_OF_MONTH) == date.getDay()){
                return false;
            }
        }
        return true;
    }

    private List<Date> disponiblesToNoDisponibles(List<Date> disponibles){
        List<Date> misNoDisponibles = new ArrayList<>();
        if (disponibles==null){
            disponibles = new ArrayList<>();
        }
        disponibles.sort(new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }
        });
        if (!disponibles.isEmpty()){
            limiteInferior = (Date) disponibles.get(0).clone();
            limiteSuperior = (Date) disponibles.get(disponibles.size()-1).clone();
            Calendar c1 = Calendar.getInstance();
            c1.setTime(limiteInferior);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(limiteSuperior);
            int tot = 0;
            while (!(c1.get(Calendar.YEAR)==c2.get(Calendar.YEAR)) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)){
                c1.add(Calendar.DATE,1);
                Calendar r1 = Calendar.getInstance();
                r1.setTime(disponibles.get(tot));
                if(r1.get(Calendar.YEAR) == c1.get(Calendar.YEAR) && r1.get(Calendar.MONTH) == c1.get(Calendar.MONTH) && r1.get(Calendar.DAY_OF_MONTH) == c1.get(Calendar.DAY_OF_MONTH)){
                    tot++;
                }else{
                    misNoDisponibles.add(r1.getTime());
                }
            }
        }
        return  misNoDisponibles;
    }

    private boolean fechaRangoValido(Date fe1, Date fe2){
        for (Date dd : fechasNoDispo){
            if (dd.getTime()>=fe1.getTime() && dd.getTime()<=fe2.getTime()){
                return  false;
            }
        }
        return true;
    }

    private void marcarNoDisponibles(){
        Calendario calendario = alojamiento.getCalendario();
        List<Date> fechasOcupadas =  null;
        List<Date> fechasNoDispoibles = disponiblesToNoDisponibles(new ArrayList<Date>());// sacar del calendario
        fechasOcupadas = calendario.getFechasOcupadas();
        if (fechasOcupadas == null){
            fechasOcupadas =  new ArrayList<>();
        }
        fechasOcupadas.addAll(fechasNoDispoibles);
        fechasNoDispo = fechasOcupadas;

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
            if(fechaUnicaValida(date)) {
                ++contadorTouch;

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, date.getYear());
                calendar.set(Calendar.MONTH, date.getMonth() - 1);
                calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
                fechaInicio = calendar.getTime();
                calendarView.markDate(new DateData(date.getYear(), date.getMonth(), date.getDay()));
            }else{
                Toast.makeText(CalendarioAlojamientoActivity.this,"Seleccione fecha disponible",Toast.LENGTH_SHORT).show();
            }
        }else if (contadorTouch == 1){
            if (fechaIgual(fechaInicio, calendar0.getTime()) == 0){
                // se deja todo igual
            }else if(fechaIgual(fechaInicio, calendar0.getTime()) > 0){
                if (fechaUnicaValida(date)) {
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(fechaInicio);
                    calendarView.unMarkDate(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH) + 1, calendar1.get(Calendar.DAY_OF_MONTH));

                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.set(Calendar.YEAR, date.getYear());
                    calendar2.set(Calendar.MONTH, date.getMonth() - 1);
                    calendar2.set(Calendar.DAY_OF_MONTH, date.getDay());
                    fechaInicio = calendar2.getTime();
                    calendarView.markDate(date);
                }else {
                    Toast.makeText(CalendarioAlojamientoActivity.this,"Seleccione fecha disponible",Toast.LENGTH_SHORT).show();
                }
            }else{
                Calendar calendar3 = Calendar.getInstance();
                calendar3.set(Calendar.YEAR, date.getYear());
                calendar3.set(Calendar.MONTH, date.getMonth()-1);
                calendar3.set(Calendar.DAY_OF_MONTH, date.getDay());
                if (fechaRangoValido(fechaInicio,calendar3.getTime())) {
                    ++contadorTouch;
                    fechaFin = calendar3.getTime();
                    calendarView.markDate(date);

                    dibujarSegmento(fechaInicio, fechaFin);
                }else{
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(fechaInicio);
                    calendarView.unMarkDate(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH) + 1, calendar1.get(Calendar.DAY_OF_MONTH));

                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.set(Calendar.YEAR, date.getYear());
                    calendar2.set(Calendar.MONTH, date.getMonth() - 1);
                    calendar2.set(Calendar.DAY_OF_MONTH, date.getDay());
                    fechaInicio = calendar2.getTime();
                    calendarView.markDate(date);
                }
            }
        }else if (contadorTouch == 2){
            if (fechaUnicaValida(date)) {
                contadorTouch = 1;

                limpiarSegmento(fechaInicio, fechaFin);

                fechaFin = null;

                Calendar calendar4 = Calendar.getInstance();
                calendar4.set(Calendar.YEAR, date.getYear());
                calendar4.set(Calendar.MONTH, date.getMonth() - 1);
                calendar4.set(Calendar.DAY_OF_MONTH, date.getDay());

                fechaInicio = calendar4.getTime();


                calendarView.markDate(date);
            }
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

    private void realizarreserva(){
        if (fechaInicio!=null && fechaFin!=null) {
            Reserva myReserva = new Reserva();
            myReserva.setAlojamiento(this.alojamiento);
            myReserva.setAlojamientoId(this.alojamiento.getId());
            myReserva.setFechaInicio(this.fechaInicio);
            myReserva.setFechaFin(this.fechaFin);
            int dias = fechaFin.getDay() - fechaInicio.getDay();
            myReserva.setValor(alojamiento.getValorNoche() * dias);
            myReserva.setTipo(alojamiento.getTipo());
            loadHuesped(myReserva);
        }else {
            Toast.makeText(CalendarioAlojamientoActivity.this,"Seleccione las fechas",Toast.LENGTH_SHORT).show();
        }

    }


    public void loadHuesped(final Reserva reserva) {
        List<Huesped> aux = new ArrayList<Huesped>();
        myRef = database.getReference("/users/huespedes");
        myRef. addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Huesped locaciones = singleSnapshot.getValue(Huesped.class);
                    Log.i("TAG1",locaciones.getCorreo());
                    Log.i("TAG1",mAuth.getCurrentUser().getEmail());
                    if (locaciones.getCorreo().equals(mAuth.getCurrentUser().getEmail())){
                        Log.i("TAG1","Encontro un correo");
                        // PONER CODIGO PARA CARGAR LA IMAGEN DESDE HUESPED
                        reserva.setUsuario(locaciones);
                        reserva.setUsuarioId(locaciones.getId());
                        updatealojamiento(reserva);

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "error en la consulta", databaseError.toException());
            }
        });
    }

    public void updatealojamiento(Reserva reserva){

        //Update Alojamiento

        List<Date> fechasocupadas;
        if (alojamiento.getCalendario().isEmpty()){
            fechasocupadas = new ArrayList<Date>();
        }else {
            fechasocupadas = alojamiento.getCalendario().getFechasOcupadas();
        }
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(fechaInicio);
        calendar2.setTime(fechaFin);

        calendar1.add(Calendar.DATE,1);
        fechasocupadas.add(calendar1.getTime());
        while(fechaIgual(calendar1.getTime(),calendar2.getTime()) < 0){
            fechasocupadas.add(calendar1.getTime());
            calendar1.add(Calendar.DATE,1);
        }
        fechasocupadas.add(calendar1.getTime());

        Calendario calendario = new Calendario();
        calendario.setFechasOcupadas(fechasocupadas);
        alojamiento.setCalendario(calendario);

        FirebaseDatabase database3= FirebaseDatabase.getInstance();
        DatabaseReference myRef3 = database.getReference().child(PATH_ALOJAMIENTO);

        String key = alojamiento.getId();
        myRef3=database3.getReference().child(PATH_ALOJAMIENTO);
        Log.i("TAGa",alojamiento.toString());
        //myRef3.setValue(alojamiento);

        Map<String, Object> alojamientoUpdates = new HashMap<>();
        alojamientoUpdates.put(alojamiento.getId(), alojamiento);
        myRef3.updateChildren(alojamientoUpdates);

        addreserva(reserva);

    }

    public void addreserva(Reserva reserva){
        //Agregar Reserva
        FirebaseDatabase database2= FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database.getReference().child(PATH_RESERVAS);
        String key = myRef2.push().getKey();
        myRef2=database2.getReference().child(PATH_RESERVAS).child(key);
        reserva.setId(key);
        myRef2.setValue(reserva);

        Intent intent = new Intent(CalendarioAlojamientoActivity.this, NotificationService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("reserva", reserva);

        intent.putExtras(bundle);


        startService(intent);
        Intent intent2 = new Intent(CalendarioAlojamientoActivity.this,MainActivity.class);
        startActivity(intent2);
    }

}




