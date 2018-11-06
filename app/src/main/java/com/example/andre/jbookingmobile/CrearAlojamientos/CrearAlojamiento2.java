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
import android.widget.Toast;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.R;

public class CrearAlojamiento2 extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton addHu,lessHu,addDorm,lessDorm,addCam,lessCam;
    private EditText edHu,edDorm,edCam;
    Button nextCrear2;
    Alojamiento myAlj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alojamiento2);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initElementos();
        initEventos();
    }

    private void initElementos() {
        addHu=findViewById(R.id.butAddHUes);
        lessHu=findViewById(R.id.butDelHUes);
        edHu=findViewById(R.id.eTHuespedesNUM);
        addDorm=findViewById(R.id.butAddDorm);
        lessDorm=findViewById(R.id.butDelDorm);
        edDorm=findViewById(R.id.eTALDormiNUM);
        addCam=findViewById(R.id.butAddCam);
        lessCam=findViewById(R.id.butDelCam);
        edCam=findViewById(R.id.eTALCamNUM);
        nextCrear2= findViewById(R.id.butAloj2);

        Bundle bundle=getIntent().getBundleExtra("bundle");
        myAlj= (Alojamiento) bundle.getSerializable("ALOJ");
    }

    private void initEventos() {
        //HUESPEDES
        addHu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempaHu=Integer.parseInt(String.valueOf(edHu.getText()));
                edHu.setText(String.valueOf(tempaHu+1));
                }
        });
        lessHu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempaHu=Integer.parseInt(String.valueOf(edHu.getText()));
                edHu.setText(String.valueOf(tempaHu-1));
                }
        });
        //DORMITORIOS
        addDorm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempDorm=Integer.parseInt(String.valueOf(edDorm.getText()));
                edDorm.setText(String.valueOf(tempDorm+1));
                }
        });
        lessDorm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempDorm=Integer.parseInt(String.valueOf(edDorm.getText()));
                edDorm.setText(String.valueOf(tempDorm-1));
                }
        });
        //CAMAS
        addCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempCam=Integer.parseInt(String.valueOf(edCam.getText()));
                edCam.setText(String.valueOf(tempCam+1));
                }
        });
        lessCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempCam=Integer.parseInt(String.valueOf(edCam.getText()));
                edCam.setText(String.valueOf(tempCam-1));
                }
        });


        nextCrear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarForma()){
                    myAlj.setHuespedes(Integer.parseInt(String.valueOf(edHu.getText())));
                    myAlj.setDormitorios(Integer.parseInt(String.valueOf(edDorm.getText())));
                    myAlj.setCamas(Integer.parseInt(String.valueOf(edCam.getText())));
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("ALOJ",  myAlj);
                    Intent intent = new Intent(CrearAlojamiento2.this, CrearAlojamiento3.class);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                }

            }
        });
    }

    private boolean validarForma() {
        int camas = Integer.parseInt(String.valueOf(edCam.getText()));
        int huesp = Integer.parseInt(String.valueOf(edHu.getText()));
        int dorm = Integer.parseInt(String.valueOf(edDorm.getText()));
        if( !(((camas >= (huesp / 2.0)) || (camas == huesp)) && ((dorm > 0) && (huesp > 0) && (camas > 0) && (dorm <= camas)))){
            Toast.makeText(CrearAlojamiento2.this,"No tiene sentido la seleccion actual",Toast.LENGTH_SHORT).show();
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
