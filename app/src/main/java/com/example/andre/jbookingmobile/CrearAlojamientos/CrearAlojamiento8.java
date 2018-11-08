package com.example.andre.jbookingmobile.CrearAlojamientos;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.R;

public class CrearAlojamiento8 extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton addPrec,lessPrec;
    private EditText edPrec;
    private Button nextCrear8;
    private Alojamiento myAlj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alojamiento8);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initElementos();
        initEventos();
    }

    private void initElementos() {
        addPrec=findViewById(R.id.butAddPrec);
        lessPrec=findViewById(R.id.butDelPrec);
        edPrec=findViewById(R.id.eTPrecNum);


        nextCrear8= findViewById(R.id.butAloj8);

        Bundle bundle=getIntent().getBundleExtra("bundle");
        myAlj= (Alojamiento) bundle.getSerializable("ALOJ");
    }

    private void initEventos() {
        //BANHOS
        addPrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempaHu=Integer.parseInt(String.valueOf(edPrec.getText()));
                edPrec.setText(String.valueOf(tempaHu+1000));
            }
        });
        lessPrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempaHu=Integer.parseInt(String.valueOf(edPrec.getText()));
                edPrec.setText(String.valueOf(tempaHu-1000));
            }
        });
        nextCrear8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarForma()){
                    myAlj.setValorNoche(Double.parseDouble(String.valueOf(edPrec.getText())));

                    Bundle bundle=new Bundle();
                    bundle.putSerializable("ALOJ",  myAlj);
                    Intent intent = new Intent(CrearAlojamiento8.this, CrearAlojamiento7.class);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                }

            }
        });
    }
    private boolean validarForma() {
        Double price = Double.parseDouble(String.valueOf(edPrec.getText()));
        if(price<0){
            Toast.makeText(CrearAlojamiento8.this,"No tiene sentido la seleccion actual",Toast.LENGTH_SHORT).show();
            return false;
        }
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
