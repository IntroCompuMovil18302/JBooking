package com.example.andre.jbookingmobile.CrearAlojamientos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.R;

public class CrearAlojamiento6 extends AppCompatActivity {


    private Toolbar toolbar;
    private EditText edTitle;
    private EditText edDesc;
    private Alojamiento myAlj;
    private Button nextCrear6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alojamiento6);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initElementos();
        initEventos();
    }


    private void initElementos() {

        edTitle= findViewById(R.id.edAlName);
        edDesc= findViewById(R.id.edALDesc);
        nextCrear6= findViewById(R.id.butAloj6);


        Bundle bundle=getIntent().getBundleExtra("bundle");
        myAlj= (Alojamiento) bundle.getSerializable("ALOJ");
    }


    private void initEventos() {
        nextCrear6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarForma()) {

                    myAlj.setNombre(edTitle.getText().toString());
                    myAlj.setDescripcion(edDesc.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ALOJ", myAlj);
                    Intent intent = new Intent(CrearAlojamiento6.this, CrearAlojamiento7.class);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }



    private boolean validarForma() {

        if (edTitle.getText().toString().trim().length() > 0 && edDesc.getText().toString().trim().length() > 0)
            return true;
        else {
            Toast.makeText(CrearAlojamiento6.this, "Por favor escribir en ambos campos", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
