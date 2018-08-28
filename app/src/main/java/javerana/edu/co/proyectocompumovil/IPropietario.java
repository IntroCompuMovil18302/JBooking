package javerana.edu.co.proyectocompumovil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IPropietario extends AppCompatActivity {


    Button agregar;
    Button editar;
    Button borrar;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipropietario);
        agregar = (Button)findViewById(R.id.agregar_turist);
        editar = (Button)findViewById(R.id.editar_turisticos);
        borrar = (Button)findViewById(R.id.borrar_turistico);
        logout = (Button)findViewById(R.id.logout2);


        agregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(),agregar_turistico.class));

            }
        });

        editar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(),agregar_turistico.class));

            }
        });

        borrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(),eliminar_turistico.class));

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
