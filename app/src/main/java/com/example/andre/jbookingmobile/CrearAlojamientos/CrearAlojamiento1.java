package com.example.andre.jbookingmobile.CrearAlojamientos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andre.jbookingmobile.Adapters.HintAdapter;
import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.R;
import com.example.andre.jbookingmobile.RegistroActivity;


public class CrearAlojamiento1 extends AppCompatActivity {
    Spinner spinTipo;
    Spinner spinTipoProp;
    FloatingActionButton helpDispo;
    Button nextCrear1;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alojamiento1);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinTipo = findViewById(R.id.spinAlojTipoPropiedad);
        HintAdapter hintAdapterTipo=new HintAdapter(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.PropiedadesStrings));
        spinTipo.setAdapter(hintAdapterTipo);
        spinTipo.setSelection(hintAdapterTipo.getCount());
        spinTipoProp = findViewById(R.id.spinReAlojTipoPropiedad);
        HintAdapter hintAdapterTipoProp=new HintAdapter(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.DisposicionStrings));
        spinTipoProp.setAdapter(hintAdapterTipoProp);
        spinTipoProp.setSelection(hintAdapterTipoProp.getCount());
        helpDispo = findViewById(R.id.floatingActionButton);
        nextCrear1= findViewById(R.id.butAloj1);
        initEventos();




    }

    private void initEventos() {
        helpDispo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(CrearAlojamiento1.this).create();
                alertDialog.setTitle("Disposicion");
                alertDialog.setMessage(getString(R.string.DisposicionInfoStrings));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
        nextCrear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarForma()){
                    Alojamiento myAlj = new Alojamiento();
                    myAlj.setTipo((String)spinTipo.getSelectedItem());
                   myAlj.setDisposicion((String)spinTipoProp.getSelectedItem());
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("ALOJ",  myAlj);
                    Intent intent = new Intent(CrearAlojamiento1.this, CrearAlojamiento2.class);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                }

            }
        });
    }

    private boolean validarForma() {
  if ((spinTipo.getSelectedItemPosition()==spinTipo.getCount())||(spinTipoProp.getSelectedItemPosition()==spinTipoProp.getCount()) ){
            Toast.makeText(CrearAlojamiento1.this,"Seleccione correctamente los datos",Toast.LENGTH_SHORT).show();
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
