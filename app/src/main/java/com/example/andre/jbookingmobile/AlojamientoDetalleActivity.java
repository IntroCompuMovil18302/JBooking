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
import com.example.andre.jbookingmobile.Entities.Comentario;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AlojamientoDetalleActivity extends AppCompatActivity {
    private Alojamiento alojamiento;
    private Toolbar toolbar;

    private LinearLayout linearLayoutGaleria;
    private LayoutInflater inflater;

    private TextView textViewLugar;
    private TextView textViewNombre;
    private TextView textViewDescripcion;
    private TextView textViewPrecio;

    private TextView textViewTipo;
    private TextView textViewHuespedes;
    private TextView textViewCuartos;
    private TextView textViewCamas;
    private TextView textViewBanhos;
    private TextView textViewServicios;
    private TextView textViewCalificacion;

    private Button buttonDisponibilidad;
    private Button buttonComentarios;
    private Button butttonComollegar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alojamiento_detalle);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textViewLugar = findViewById(R.id.textViewAlojamientoDetalleLugar);
        textViewNombre = findViewById(R.id.textViewAlojamientoDetalleNombre);
        textViewDescripcion = findViewById(R.id.textViewAlojamientoDetalleDescripcion);
        textViewPrecio = findViewById(R.id.textViewAlojamientoDetallePrecio);
        textViewTipo = findViewById(R.id.textViewAlojamientoDetalleTipo);
        textViewHuespedes = findViewById(R.id.textViewAlojamientoDetalleHuespedes);
        textViewCuartos = findViewById(R.id.textViewAlojamientoDetalleDormitorios);
        textViewCamas = findViewById(R.id.textViewAlojamientoDetalleCamas);
        textViewBanhos = findViewById(R.id.textViewAlojamientoDetalleBanhos);
        textViewServicios = findViewById(R.id.textViewAlojamientoDetalleServicios);
        textViewCalificacion = findViewById(R.id.textViewCalificacioni);

        buttonDisponibilidad = findViewById(R.id.buttonAlojamientoDetalleDisponible);
        buttonComentarios = findViewById(R.id.buttonComentarios);
        butttonComollegar = findViewById(R.id.buttonComoLlegarTo);

        alojamiento = (Alojamiento) getIntent().getExtras().getSerializable("alojamiento");

        linearLayoutGaleria = findViewById(R.id.linearLayoutGaleria);
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
        StringTokenizer st = new StringTokenizer(alojamiento.getFotos(),";");
        while (st.hasMoreTokens()){
            imagenes.add(st.nextToken());
        }

        for (String img: imagenes){
            View view = inflater.inflate(R.layout.imagen_alojamiento_item, linearLayoutGaleria,false);
            ImageView imagenActual = view.findViewById(R.id.imageViewAlojamientoDetalleFoto);
            Picasso.with(AlojamientoDetalleActivity.this).load(img).into(imagenActual);
            linearLayoutGaleria.addView(view);
        }

        textViewLugar.setText(alojamiento.getUbicacion().getNombre());
        textViewNombre.setText(alojamiento.getNombre());
        textViewTipo.setText(alojamiento.getTipo());
        textViewDescripcion.setText(alojamiento.getDescripcion());
        textViewHuespedes.setText(alojamiento.getHuespedes()+" Huespedes");
        textViewCuartos.setText(alojamiento.getDormitorios()+" Dormitorios");
        textViewCamas.setText(alojamiento.getCamas()+" Camas");
        textViewBanhos.setText(alojamiento.getBanhos()+" Baños");
        textViewServicios.setText(alojamiento.getServicios()+"");
        textViewPrecio.setText("$"+alojamiento.getValorNoche()+"/ Noche");

        List<Comentario> comentarios = alojamiento.getComentarios();
        int sum = 0;
        for (Comentario c : comentarios){
            sum += c.getPuntuacion();
        }
        if (comentarios.size() == 0){
            sum = sum;
        }else{
            sum = sum/comentarios.size();
        }
        textViewCalificacion.setText("Calificacion: "+sum);
    }

    private void initEvents(){
        buttonDisponibilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlojamientoDetalleActivity.this,CalendarioAlojamientoActivity.class);
                intent.putExtra("alojamiento",(Serializable) alojamiento);
                startActivity(intent);
            }
        });
        buttonComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlojamientoDetalleActivity.this,ComentariosListaActivity.class);
                intent.putExtra("alojamiento",(Serializable) alojamiento);
                startActivity(intent);
            }
        });
        butttonComollegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlojamientoDetalleActivity.this,ComoLlegarActivity.class);
                intent.putExtra("alojamiento",(Serializable) alojamiento);
                startActivity(intent);
            }
        });
    }
}
