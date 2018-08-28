package javerana.edu.co.proyectocompumovil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IAnfitrion extends AppCompatActivity {

    Button agregar;
    Button editar;
    Button borrar;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ianfitrion);
        agregar = (Button)findViewById(R.id.agregar_aloj);
        editar = (Button)findViewById(R.id.editar_aloj);
        borrar = (Button)findViewById(R.id.borrar_aloj);
        logout = (Button)findViewById(R.id.logout);


        agregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(),IAnfitrion.class));

            }
        });

        editar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(),IAnfitrion.class));

            }
        });

        borrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(v.getContext(),IAnfitrion.class));

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
