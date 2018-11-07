package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.andre.jbookingmobile.Adapters.AlojamientoAdaptador;
import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Fragment1 extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<Alojamiento> alojamientos;
    private GridView gridViewAlojamientos;
    private AlojamientoAdaptador alojamientoAdaptador;
    public static final String PATH_ALOJAMIENTOS = "alojamientos/";

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        database = FirebaseDatabase.getInstance();
        alojamientos = new ArrayList<>();
        alojamientoAdaptador = new AlojamientoAdaptador(getContext(), alojamientos);
        gridViewAlojamientos = view.findViewById(R.id.gridViewFragment1Alojamientos);
        gridViewAlojamientos.setAdapter(alojamientoAdaptador);
        initEvents();
        cargarAlojamientos();
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void cargarAlojamientos(){
        myRef = database.getReference(PATH_ALOJAMIENTOS);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Alojamiento alojamiento = dataSnapshot.getValue(Alojamiento.class);
                alojamientos.add(alojamiento);
                alojamientoAdaptador.notifyDataSetChanged();
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
        gridViewAlojamientos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alojamiento alojamientoActual = (Alojamiento) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(),AlojamientoDetalleActivity.class);
                intent.putExtra("alojamiento",(Serializable) alojamientoActual);
                startActivity(intent);
            }
        });
    }
}
