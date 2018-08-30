package javerana.edu.co.proyectocompumovil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class IHuesped extends AppCompatActivity {
    Button consultaal;
    Button consultatur;
    Button calificar;
    Button historial;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ihuesped);
        consultaal = (Button) findViewById(R.id.consultar_alojamientos);
        consultatur = (Button) findViewById(R.id.consultar_turisticos);
        calificar = (Button) findViewById(R.id.calificar);
        historial = (Button) findViewById(R.id.historial);
        logout = (Button) findViewById(R.id.logout);

        consultaal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(),vista_mapa.class));

            }
        });

        consultatur.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(),vista_mapa.class));

            }
        });

        calificar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(),IHuesped.class));

            }
        });

        historial.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(),IHuesped.class));

            }
        });

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(),Login.class));

            }
        });

    }
}
