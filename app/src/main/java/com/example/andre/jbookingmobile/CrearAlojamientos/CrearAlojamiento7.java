package com.example.andre.jbookingmobile.CrearAlojamientos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CrearAlojamiento7 extends AppCompatActivity {
    private Toolbar toolbar;
    private Alojamiento myAlj;
    private Button nextCrear7;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String PATH_USERS="users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alojamiento7);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initElementos();
        initEventos();
    }


    private void initElementos() {


        nextCrear7= findViewById(R.id.butAloj7);
        mAuth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();

        Bundle bundle=getIntent().getBundleExtra("bundle");
        myAlj= (Alojamiento) bundle.getSerializable("ALOJ");
    }


    private void initEventos() {
        nextCrear7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   if (validarForma()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    user.g
                    if(user!=null) {
                        myAlj.setAnfitrion();
                    }

                }*/

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
