package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.andre.jbookingmobile.Adapters.AlojamientoAdaptador;
import com.example.andre.jbookingmobile.Adapters.ReservaAdaptador;
import com.example.andre.jbookingmobile.CrearAlojamientos.CrearAlojamiento1;
import com.example.andre.jbookingmobile.CrearAlojamientos.CrearAlojamiento3;
import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Reserva;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends Fragment {

    private  Button newAlj ;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<Reserva> reservas;
    private GridView gridViewReservas;
    private ReservaAdaptador reservaAdaptador;

    public static final String PATH_RESERVAS = "reservas/";


    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        database = FirebaseDatabase.getInstance();
        reservas = new ArrayList<>();
        reservaAdaptador = new ReservaAdaptador(getContext(), reservas);
        gridViewReservas = view.findViewById(R.id.gridViewFragment1Reservas);

        gridViewReservas.setAdapter(reservaAdaptador);

        initEvents();
        cargarReservas();
        return view;
    }

    private void cargarReservas(){
        myRef = database.getReference(PATH_RESERVAS);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Reserva reserva = dataSnapshot.getValue(Reserva.class);
                reservas.add(reserva);
                reservaAdaptador.notifyDataSetChanged();
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

    private void initEvents(){
        gridViewReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reserva ReservaActual = (Reserva) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(),ReservaDetalleActivity.class);
                Log.i("ENE",ReservaActual.getId()+"HOLAAA");
                intent.putExtra("reserva",(Serializable) ReservaActual);
                startActivity(intent);
            }
        });

    }
}