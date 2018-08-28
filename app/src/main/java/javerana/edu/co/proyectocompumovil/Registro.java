package javerana.edu.co.proyectocompumovil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class Registro extends AppCompatActivity {
    Button Crear_perfil;
    Spinner tipo_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Crear_perfil = (Button) findViewById(R.id.crear_perfil);
        tipo_usuario = (Spinner) findViewById(R.id.tipo_usuario);
        Crear_perfil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String tipo = (String)tipo_usuario.getSelectedItem();
                if (tipo.equals("Propietario")){
                    startActivity(new Intent(v.getContext(),IPropietario.class));
                }else if (tipo.equals("Anfitrion")){
                    startActivity(new Intent(v.getContext(),IAnfitrion.class));
                }else{
                    startActivity(new Intent(v.getContext(),IHuesped.class));
                }
            }
        });
    }
}
