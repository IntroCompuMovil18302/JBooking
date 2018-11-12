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
import com.example.andre.jbookingmobile.Adapters.LugarAdaptador;
import com.example.andre.jbookingmobile.CrearAlojamientos.CrearAlojamiento1;
import com.example.andre.jbookingmobile.CrearLugares.CrearLugar1;
import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Lugar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MisLugaresFragment extends Fragment {

    private Button agregar;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<Lugar> lugares;
    private FirebaseAuth mAuth;
    private GridView gridViewLugares;
    private LugarAdaptador lugarAdaptador;
    public static final String PATH_ALOJAMIENTOS = "lugares/";


    public MisLugaresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_lugares, container, false);
        agregar = view.findViewById(R.id.buttonMisLugaresAdd);

        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        lugares = new ArrayList<>();
        lugarAdaptador = new LugarAdaptador(getContext(), lugares);
        gridViewLugares = view.findViewById(R.id.gridViewFragment1Lugares);
        gridViewLugares.setAdapter(lugarAdaptador);

        initevents();
        cargarLugares();


        return view;
    }

    public void  initevents(){
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),CrearLugar1.class));
            }
        });

        gridViewLugares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lugar lugarActual = (Lugar) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(),LugarDetalleActivity.class);
                intent.putExtra("lugar",(Serializable) lugarActual);
                startActivity(intent);
            }
        });
    }


    private void cargarLugares(){
        myRef = database.getReference(PATH_ALOJAMIENTOS);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Lugar lugar = dataSnapshot.getValue(Lugar.class);
                if (lugar.getPropietario().getCorreo().equals(mAuth.getCurrentUser().getEmail())){
                    lugares.add(lugar);
                    lugarAdaptador.notifyDataSetChanged();
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
