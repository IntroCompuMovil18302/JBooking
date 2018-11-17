package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Calendario;
import com.example.andre.jbookingmobile.Entities.Comentario;
import com.example.andre.jbookingmobile.Entities.Lugar;
import com.example.andre.jbookingmobile.Entities.Reserva;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComentariosActivity extends AppCompatActivity {

    Spinner calificacion;
    EditText comentariodes;
    Button agregarComentario;
    Reserva reserva;
    Comentario comentarioclass;
    Alojamiento alojamiento;
    public final String PATH_ALOJAMIENTO = "alojamientos";
    public final String PATH_COMENTARIO = "comentarios";
    public final String PATH_RESERVA = "reservas";
    boolean ya = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        initVariables();
        initEventos();
    }

    public void initVariables(){
        calificacion = findViewById(R.id.spinner);
        comentariodes = findViewById(R.id.edescripcion);
        reserva = (Reserva) getIntent().getExtras().getSerializable("reserva");
        agregarComentario = findViewById(R.id.buttonAddComentario);
        Log.i("RES",reserva.getAlojamiento().getNombre());



    }
    public void initEventos(){
        agregarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadalojs();
                Intent intent = new Intent(ComentariosActivity.this,MainActivity.class);
                intent.putExtra("reserva",(Serializable) reserva);
                startActivity(intent);
            }
        });
    }

    public void loadalojs() {

        FirebaseDatabase database4 = FirebaseDatabase.getInstance();
        DatabaseReference myRef4 = database4.getReference("/alojamientos");
        myRef4. addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Alojamiento locaciones = singleSnapshot.getValue(Alojamiento.class);
                    Log.i("LOGGGG",locaciones.getNombre());
                    Log.i("LOGGGG",reserva.getAlojamiento().getNombre());
                    if (locaciones.getNombre().equals(reserva.getAlojamiento().getNombre())){
                        Log.i("LOGU",locaciones.getNombre());
                        updateAlojamiento(locaciones);
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "error en la consulta", databaseError.toException());
            }
        });
    }

    public void updateAlojamiento(Alojamiento myalj){
        if(!ya){
            Comentario comentario = new Comentario();
            comentario.setPuntuacion(Integer.parseInt((String)calificacion.getSelectedItem()));
            comentario.setComentario(comentariodes.getText().toString());
            comentario.setNombreUsuario(reserva.getUsuario().getUserName());
            FirebaseDatabase database2= FirebaseDatabase.getInstance();
            DatabaseReference myRef2 = database2.getReference().child(PATH_COMENTARIO);
            String key = myRef2.push().getKey();
            myRef2=database2.getReference().child(PATH_COMENTARIO).child(key);
            comentario.setId(key);
            myRef2.setValue(comentario);


            myalj.agregarcomentario(comentario);


            FirebaseDatabase database= FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child(PATH_ALOJAMIENTO);
            String key2 = myalj.getId();
            myRef=database.getReference().child(PATH_ALOJAMIENTO);
            Log.i("TAGa",myalj.toString());
            Map<String, Object> alojamientoUpdates = new HashMap<>();
            alojamientoUpdates.put(myalj.getId(), myalj);
            myRef.updateChildren(alojamientoUpdates);


            reserva.setAlojamiento(myalj);
            FirebaseDatabase database5= FirebaseDatabase.getInstance();
            DatabaseReference myRef5 = database.getReference().child(PATH_RESERVA);
            String key3 = reserva.getId();
            myRef=database.getReference().child(PATH_RESERVA);
            Log.i("TAGa",reserva.toString());
            Map<String, Object> reservaUpdates = new HashMap<>();
            reservaUpdates.put(reserva.getId(), reserva);
            myRef.updateChildren(reservaUpdates);
            ya = true;
        }



    }

}
