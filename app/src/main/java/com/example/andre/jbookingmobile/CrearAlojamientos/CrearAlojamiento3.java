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
import com.google.firebase.auth.FirebaseAuth;

public class CrearAlojamiento3 extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton addBan,lessBan;
    private EditText edBan;
    private Button nextCrear3;
    private Alojamiento myAlj;
    private RadioButton r1;
    private RadioButton r2;
    private  boolean privado= false;
    private FirebaseAuth mAuth;
    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alojamiento3);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initElementos();
        initEventos();
    }

    private void initElementos() {
        addBan=findViewById(R.id.butAddBan);
        lessBan=findViewById(R.id.butDelBan);
        edBan=findViewById(R.id.eTBanNUM);

        r1=(RadioButton)findViewById(R.id.radioButton);
        r2=(RadioButton)findViewById(R.id.radioButton2);
        nextCrear3= findViewById(R.id.butAloj3);

        Bundle bundle=getIntent().getBundleExtra("bundle");
        myAlj= (Alojamiento) bundle.getSerializable("ALOJ");
    }

    private void initEventos() {
        //BANHOS
        addBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempaHu=Integer.parseInt(String.valueOf(edBan.getText()));
                edBan.setText(String.valueOf(tempaHu+1));
            }
        });
        lessBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempaHu=Integer.parseInt(String.valueOf(edBan.getText()));
                edBan.setText(String.valueOf(tempaHu-1));
            }
        });

        nextCrear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarForma()){
                    myAlj.setBanhos(Integer.parseInt(String.valueOf(edBan.getText())));
                    myAlj.setBanhosPriv(privado);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("ALOJ",  myAlj);
                    Intent intent = new Intent(CrearAlojamiento3.this, CrearAlojamiento5.class);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                }

            }
        });
    }

    private boolean validarForma() {
        int banhos = Integer.parseInt(String.valueOf(edBan.getText()));
        if (r1.isChecked()==true) {
            privado=true;
        } else
        if (r2.isChecked()==true) {
            privado= false;
        }
        if(banhos<=0 &&(r1.isChecked()==false&&r2.isChecked()==false) ){
            Toast.makeText(CrearAlojamiento3.this,"No tiene sentido la seleccion actual",Toast.LENGTH_SHORT).show();
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
