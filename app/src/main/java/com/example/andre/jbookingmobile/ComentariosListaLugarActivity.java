package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.andre.jbookingmobile.Adapters.ComentarioAdaptador;
import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Comentario;
import com.example.andre.jbookingmobile.Entities.Lugar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComentariosListaLugarActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private List<Comentario> comentarios;
    private Toolbar toolbar;
    private GridView gridView;
    private Button agregar;
    Lugar lugar;
    private ComentarioAdaptador comentarioAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios_lista_lugar);

        lugar = (Lugar) getIntent().getExtras().getSerializable("lugar");
        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        comentarios = new ArrayList<>();
        comentarioAdaptador = new ComentarioAdaptador(this, comentarios);
        gridView = findViewById(R.id.gridview);
        gridView.setAdapter(comentarioAdaptador);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        agregar = findViewById(R.id.buttonAddComentario);

        initevents();
        cargarcomentarios();
    }

    public void  initevents(){

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Lugar lugarActual = (Lugar) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(),LugarDetalleActivity.class);
                intent.putExtra("lugar",(Serializable) lugarActual);
                startActivity(intent);*/
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComentariosListaLugarActivity.this,ComentariosActivity.class);
                intent.putExtra("lugar",(Serializable) lugar);
                startActivity(intent);
            }
        });
    }


    private void cargarcomentarios() {
        List<Comentario> coments = lugar.getComentarios();

        for (Comentario c:coments){
            comentarios.add(c);
            comentarioAdaptador.notifyDataSetChanged();

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }
}
