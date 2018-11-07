package com.example.andre.jbookingmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.squareup.picasso.Picasso;

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

        alojamiento = (Alojamiento) getIntent().getExtras().getSerializable("alojamiento");

        linearLayoutGaleria = findViewById(R.id.linearLayoutGaleria);
        inflater = LayoutInflater.from(this);

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
        textViewPrecio.setText("$"+alojamiento.getValorNoche()+"/ Noche");
    }
}
