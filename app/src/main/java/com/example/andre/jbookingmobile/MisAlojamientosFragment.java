package com.example.andre.jbookingmobile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.andre.jbookingmobile.Adapters.AlojamientoAdaptador;
import com.example.andre.jbookingmobile.CrearAlojamientos.CrearAlojamiento1;
import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MisAlojamientosFragment extends Fragment {

    private Button buttonAgregar;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private List<Alojamiento> alojamientos;
    private GridView gridViewAlojamientos;
    private AlojamientoAdaptador alojamientoAdaptador;
    public static final String PATH_ALOJAMIENTOS = "alojamientos/";

    public MisAlojamientosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_alojamientos, container, false);
        buttonAgregar = view.findViewById(R.id.buttonMisAlojamientosAdd);


        database = FirebaseDatabase.getInstance();
        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        alojamientos = new ArrayList<>();
        alojamientoAdaptador = new AlojamientoAdaptador(getContext(), alojamientos);
        gridViewAlojamientos = view.findViewById(R.id.gridViewFragment1Alojamientos);
        gridViewAlojamientos.setAdapter(alojamientoAdaptador);
        initEvents();
        cargarAlojamientos();
        return view;
    }

    private void initEvents(){
        gridViewAlojamientos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alojamiento alojamientoActual = (Alojamiento) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(),AlojamientoDetalleActivity.class);
                intent.putExtra("alojamiento",(Serializable) alojamientoActual);
                startActivity(intent);
            }
        });

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),CrearAlojamiento1.class));
            }
        });
    }

    private void cargarAlojamientos(){
        myRef = database.getReference(PATH_ALOJAMIENTOS);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Alojamiento alojamiento = dataSnapshot.getValue(Alojamiento.class);
                if (alojamiento.getAnfitrion().getCorreo().equals(mAuth.getCurrentUser().getEmail())){
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
