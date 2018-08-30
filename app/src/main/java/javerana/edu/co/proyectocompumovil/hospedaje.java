package javerana.edu.co.proyectocompumovil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class hospedaje extends AppCompatActivity {
    Button boton;
    CardView servicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospedaje);
        boton = (Button)findViewById(R.id.button);
        servicios = (CardView) findViewById(R.id.cardServicios);

        boton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(),reservar.class));

            }
        });
        servicios.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(),Servicios.class));

            }
        });
    }
}
