package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.andre.jbookingmobile.Entities.Huesped;

import java.io.Serializable;

public class Registro3Activity extends AppCompatActivity {

    private Button regButton;
    private Spinner spinGen;
    private EditText regNac;
    private Huesped myUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro3);
        regButton= findViewById(R.id.buttonRegistro3);
        spinGen=findViewById(R.id.spinRegistrarGenero);
        regNac = findViewById(R.id.editTextRegistroUserNac);
        Bundle bundle=getIntent().getBundleExtra("bundle");
        myUsr= (Huesped) bundle.getSerializable("USR");

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gen= (String)spinGen.getSelectedItem();
                String nacionalidad = regNac.getText().toString();
                myUsr.setGenero(gen);
                myUsr.setNacionalidad(nacionalidad);


                Bundle bundle=new Bundle();
                bundle.putString("Rol", "HUESPED");
                bundle.putSerializable("USR",myUsr);
                Intent intent = new Intent(Registro3Activity.this, Registro4Activity.class);
                intent.putExtra("bundle",bundle);
                startActivity(intent);

            }
        });
    }
}
