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
import com.example.andre.jbookingmobile.Entities.Reserva;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ReservaDetalleActivity extends AppCompatActivity {

    private Reserva reserva;
    private Toolbar toolbar;

    private LinearLayout linearLayoutGaleria;
    private LayoutInflater inflater;

    private TextView textViewLugar;
    private TextView textViewNombre;
    private TextView textViewPrecio;

    private TextView textViewTipo;
    private TextView textViewServicios;

    private TextView textViewDesde;
    private TextView textViewHasta;

    private Button buttonComentarios;
    private Button buttonHoy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_detalle);

        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textViewLugar = findViewById(R.id.textViewReservaDetalleLugar);
        textViewNombre = findViewById(R.id.textViewReservaDetalleNombre);

        textViewPrecio = findViewById(R.id.textViewReservaDetallePrecio);
        textViewTipo = findViewById(R.id.textViewReservaDetalleTipo);

        textViewServicios = findViewById(R.id.textViewReservaDetalleServicios);
        textViewDesde = findViewById(R.id.textViewFrom);
        textViewHasta = findViewById(R.id.textViewTo);

        buttonComentarios = findViewById(R.id.buttonReservaDetalleComentario);
        buttonHoy = findViewById(R.id.buttonReservaHoy);

        reserva = (Reserva) getIntent().getExtras().getSerializable("reserva");

        linearLayoutGaleria = findViewById(R.id.linearLayoutGaleria);
        inflater = LayoutInflater.from(this);
        initEvents();
        cargarVista();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }

    private void cargarVista(){
        List<String> imagenes = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(reserva.getAlojamiento().getFotos(),";");
        while (st.hasMoreTokens()){
            imagenes.add(st.nextToken());
        }

        for (String img: imagenes){
            View view = inflater.inflate(R.layout.imagen_alojamiento_item, linearLayoutGaleria,false);
            ImageView imagenActual = view.findViewById(R.id.imageViewAlojamientoDetalleFoto);
            Picasso.with(ReservaDetalleActivity.this).load(img).into(imagenActual);
            linearLayoutGaleria.addView(view);
        }

        textViewLugar.setText(reserva.getAlojamiento().getUbicacion().getNombre());
        textViewNombre.setText(reserva.getAlojamiento().getNombre());
        textViewTipo.setText(reserva.getAlojamiento().getTipoPropiedad());
        textViewServicios.setText(reserva.getAlojamiento().getServicios()+"");
        textViewPrecio.setText("$"+reserva.getValor());
        // yyyy.MM.dd
        String fecha1 = new SimpleDateFormat("dd/MM/yyyy").format(reserva.getFechaInicio());
        String fecha2 = new SimpleDateFormat("dd/MM/yyyy").format(reserva.getFechaFin());

        textViewDesde.setText(fecha1);
        textViewHasta.setText(fecha2);
    }

    private void initEvents(){
        buttonComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservaDetalleActivity.this,ComentariosActivity.class);
                intent.putExtra("reserva",(Serializable) reserva);
                startActivity(intent);
            }
        });
        buttonHoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservaDetalleActivity.this,TourActivity.class);
                intent.putExtra("reserva",(Serializable) reserva);
                startActivity(intent);
            }
        });
    }
}
