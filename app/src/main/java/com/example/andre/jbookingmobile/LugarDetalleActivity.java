package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Lugar;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LugarDetalleActivity extends AppCompatActivity {

    private Lugar lugar;
    private Toolbar toolbar;

    private LinearLayout linearLayoutGaleria;
    private LayoutInflater inflater;

    private TextView textViewLugar;
    private TextView textViewNombre;
    private TextView textViewDescripcion;

    private Button comollegar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar_detalle);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        comollegar = findViewById(R.id.buttoncomollegarLugar);
        textViewLugar = findViewById(R.id.textViewLugarDetalleLugar);
        textViewNombre = findViewById(R.id.textViewLugarDetalleNombre);
        textViewDescripcion = findViewById(R.id.textViewLugarDetalleDescripcion);

        lugar = (Lugar) getIntent().getExtras().getSerializable("lugar");

        linearLayoutGaleria = findViewById(R.id.linearLayoutGaleria2);
        inflater = LayoutInflater.from(this);
        initEvents();
        cargarVista();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }

    private void cargarVista(){
        List<String> imagenes = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(lugar.getFotos(),";");
        while (st.hasMoreTokens()){
            imagenes.add(st.nextToken());
        }

        for (String img: imagenes){
            View view = inflater.inflate(R.layout.imagen_alojamiento_item, linearLayoutGaleria,false);
            ImageView imagenActual = view.findViewById(R.id.imageViewAlojamientoDetalleFoto);
            Picasso.with(LugarDetalleActivity.this).load(img).into(imagenActual);
            linearLayoutGaleria.addView(view);
        }

        textViewLugar.setText(lugar.getUbicacion().getNombre());
        textViewNombre.setText(lugar.getNombre());
        textViewDescripcion.setText(lugar.getDescripcion());
    }

    private void initEvents(){
        // FOR BUTTONS
        comollegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LugarDetalleActivity.this,ConsultarAlojamientoActivity.class));
            }
        });
    }
}
