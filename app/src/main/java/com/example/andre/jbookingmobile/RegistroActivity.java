package com.example.andre.jbookingmobile;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.andre.jbookingmobile.Entities.Anfitrion;
import com.example.andre.jbookingmobile.Entities.Huesped;
import com.example.andre.jbookingmobile.Entities.Propietario;


import java.io.Serializable;

public class RegistroActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button buttonNext;


private EditText regNom,regApe,regMail,regPass,regUser;
private Spinner spinRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        buttonNext = findViewById(R.id.buttonRegistroSiguiente);
        regNom= findViewById(R.id.editTextRegistroNombre);
        regApe= findViewById(R.id.editTextRegistroApellido);
        regMail= findViewById(R.id.editTextRegistroCorreo);
        regPass=findViewById(R.id.editTextRegistroConstrasena);
        regUser=findViewById(R.id.editTextRegistroUserName);
        spinRole=findViewById(R.id.spinRegistrarRol);
        initEventos();
    }

    private void initEventos(){
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rol= (String)spinRole.getSelectedItem();

                switch (rol) {
                    case "Propietario": {
                        Propietario myUser = new Propietario();
                        myUser.setNombre(String.valueOf(regNom.getText())+ " "+ regApe.getText().toString());
                        myUser.setCorreo(regMail.getText().toString());
                        myUser.setUserName(regUser.getText().toString());
                        myUser.setContrasena(regPass.getText().toString());

                        Bundle bundle=new Bundle();
                        bundle.putString("Rol", "PROPIETARIO");
                        bundle.putSerializable("USR",myUser);
                        Intent intent = new Intent(RegistroActivity.this, Registro4Activity.class);
                        intent.putExtra("bundle",bundle);
                        startActivity(intent);

                        break;
                    }
                    case "Huésped": {
                        Huesped myUser = new Huesped();
                        myUser.setNombre(String.valueOf(regNom.getText())+ " "+ regApe.getText().toString());
                        myUser.setCorreo(regMail.getText().toString());
                        myUser.setUserName(regUser.getText().toString());
                        myUser.setContrasena(regPass.getText().toString());

                        Bundle bundle=new Bundle();
                        bundle.putString("Rol", "HUESPED");
                        bundle.putSerializable("USR",myUser);

                        Intent intent = new Intent(RegistroActivity.this, Registro2Activity.class);
                        intent.putExtra("bundle",bundle);
                        startActivity(intent);
                        break;
                    }
                    case "Anfitrión": {
                        Anfitrion myUser = new Anfitrion();
                        myUser.setNombre(String.valueOf(regNom.getText())+ " "+ regApe.getText().toString());
                        myUser.setCorreo(regMail.getText().toString());
                        myUser.setUserName(regUser.getText().toString());
                        myUser.setContrasena(regPass.getText().toString());

                        Bundle bundle=new Bundle();
                        bundle.putString("Rol", "ANFITRION");
                        bundle.putSerializable("USR",  myUser);
                        Intent intent = new Intent(RegistroActivity.this, Registro4Activity.class);
                        intent.putExtra("bundle",bundle);
                        startActivity(intent);

                        break;
                    }
                }
            }
        });
    }
}
