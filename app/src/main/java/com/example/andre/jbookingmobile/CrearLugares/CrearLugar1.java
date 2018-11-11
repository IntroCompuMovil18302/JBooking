package com.example.andre.jbookingmobile.CrearLugares;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andre.jbookingmobile.CrearAlojamientos.CrearAlojamiento6;
import com.example.andre.jbookingmobile.CrearAlojamientos.CrearAlojamiento8;
import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Lugar;
import com.example.andre.jbookingmobile.R;
import com.google.firebase.auth.FirebaseAuth;

public class CrearLugar1 extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText nombre;
    private EditText descripcion;
    private Button siguientel;
    private Lugar myLug;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_lugar1);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initelements();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    public void initelements(){

        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();

        nombre= findViewById(R.id.editnamespace);
        descripcion= findViewById(R.id.editdesc);
        siguientel= findViewById(R.id.butnextlugar);
        siguientel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarForma()) {
                    myLug = new Lugar();
                    Log.i("TAG","Entro al boton");
                    myLug.setNombre(nombre.getText().toString());
                    myLug.setDescripcion(descripcion.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("LUG", myLug);
                    Intent intent = new Intent(CrearLugar1.this, CrearLugar2.class);
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);
                }

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }

    private boolean validarForma() {

        if (nombre.getText().toString().trim().length() > 0 && descripcion.getText().toString().trim().length() > 0)
            return true;
        else {
            Toast.makeText(CrearLugar1.this, "Por favor escribir en ambos campos", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
