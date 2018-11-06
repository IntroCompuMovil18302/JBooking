package com.example.andre.jbookingmobile.CrearAlojamientos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Ubicacion;
import com.example.andre.jbookingmobile.R;

public class CrearAlojamiento4 extends AppCompatActivity {
    private Toolbar toolbar;
    private Alojamiento myAlj;
    private Button nextCrear4;
    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alojamiento4);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initElementos();
        initEventos();
    }

    private void initElementos() {

        search= findViewById(R.id.editTextMapaDireccion);
        nextCrear4= findViewById(R.id.butAloj4);

        Bundle bundle=getIntent().getBundleExtra("bundle");
        myAlj= (Alojamiento) bundle.getSerializable("ALOJ");
    }

    private void initEventos() {
        nextCrear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarForma()){
                    Ubicacion myUbic = new Ubicacion();
                    myUbic.setAltitud(11);
                    myUbic.setLatitud(13);
                    myAlj.setUbicacion(myUbic);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("ALOJ",  myAlj);
                    Intent intent = new Intent(CrearAlojamiento4.this, CrearAlojamiento5.class);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                }

            }
        });

    }

    private boolean validarForma() {
    return true;
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
}
