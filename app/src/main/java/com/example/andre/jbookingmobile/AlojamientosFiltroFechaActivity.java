package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.andre.jbookingmobile.Adapters.AlojamientoAdaptador;
import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sun.bob.mcalendarview.vo.DateData;

public class AlojamientosFiltroFechaActivity extends AppCompatActivity {

    private Date fechaInicial;
    private Date fechaFinal;
    private Toolbar toolbar;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private List<Alojamiento> alojamientos;
    private GridView gridViewAlojamientos;
    private AlojamientoAdaptador alojamientoAdaptador;
    public static final String PATH_ALOJAMIENTOS = "alojamientos/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alojamientos_filtro_fecha);

        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getBundleExtra("bundle");
        fechaInicial = (Date) bundle.getSerializable("fecha1");
        fechaFinal = (Date) bundle.getSerializable("fecha2");

        database = FirebaseDatabase.getInstance();
        alojamientos = new ArrayList<>();
        alojamientoAdaptador = new AlojamientoAdaptador(AlojamientosFiltroFechaActivity.this, alojamientos);
        gridViewAlojamientos = findViewById(R.id.gridViewAlojamientosFiltroFechaAlojamientos);
        gridViewAlojamientos.setAdapter(alojamientoAdaptador);

        initEvents();
        cargarAlojamientos();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }

    private void initEvents(){
        gridViewAlojamientos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alojamiento alojamientoActual = (Alojamiento) parent.getItemAtPosition(position);
                Intent intent = new Intent(AlojamientosFiltroFechaActivity.this, AlojamientoDetalleActivity.class);
                intent.putExtra("alojamiento",(Serializable) alojamientoActual);
                startActivity(intent);
            }
        });
    }

    private void cargarAlojamientos(){
        myRef = database.getReference(PATH_ALOJAMIENTOS);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Alojamiento alojamiento = dataSnapshot.getValue(Alojamiento.class);
                if (alojamiento.getCalendario() != null) {
                    if (alojamiento.getCalendario().consultarDisponibilidad(fechaInicial, fechaFinal)) {
                        alojamientos.add(alojamiento);
                        alojamientoAdaptador.notifyDataSetChanged();
                    }
                }else{
                    alojamientos.add(alojamiento);
                    alojamientoAdaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
