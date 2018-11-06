package com.example.andre.jbookingmobile.CrearAlojamientos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.R;

import java.util.ArrayList;


public class CrearAlojamiento5 extends AppCompatActivity {
    private Button mServ;
    private  TextView mServSelected;
    private String[] listItems;
    private  boolean[] checkedItems;
    private  ArrayList<Integer> mUserItems = new ArrayList<>();
    private  Button mServSec;
    private  TextView mServSelectedSec;
    private  String[] listItemsSec;
    boolean[] checkedItemsSec;
    private ArrayList<Integer> mUserItemsSec = new ArrayList<>();
    private Toolbar toolbar;
    private Alojamiento myAlj;
    private Button nextCrear5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alojamiento5);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initElementos();
        initEventos();

    }

    private void initEventos() {

        nextCrear5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarForma()){
                    myAlj.setServicios(mServSelected.getText().toString());
                    myAlj.setServiciosSec(mServSelectedSec.getText().toString());
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("ALOJ",  myAlj);
                    Intent intent = new Intent(CrearAlojamiento5.this, CrearAlojamiento6.class);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                }

            }
        });

        mServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CrearAlojamiento5.this);
                mBuilder.setTitle(R.string.dialog_services_title);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + "; ";
                            }
                        }
                        mServSelected.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mServSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        mServSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CrearAlojamiento5.this);
                mBuilder.setTitle(R.string.dialog_services_title);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            mUserItemsSec.add(position);
                        }else{
                            mUserItemsSec.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItemsSec.size(); i++) {
                            item = item + listItemsSec[mUserItemsSec.get(i)];
                            if (i != mUserItemsSec.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mServSelectedSec.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItemsSec.length; i++) {
                            checkedItemsSec[i] = false;
                            mUserItemsSec.clear();
                            mServSelectedSec.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }

    private boolean validarForma() {
        return true;
    }

    private void initElementos() {
        mServ =  findViewById(R.id.butSelecServ);
        mServSelected = findViewById(R.id.textViewA52);
        listItems = getResources().getStringArray(R.array.ServiciosStrings);
        checkedItems = new boolean[listItems.length];

        mServSec =  findViewById(R.id.butSelecServSec);
        mServSelectedSec = findViewById(R.id.textViewA54);
        listItemsSec = getResources().getStringArray(R.array.ServiciosStringsSec);
        checkedItemsSec = new boolean[listItems.length];
        nextCrear5= findViewById(R.id.butAloj5);

        Bundle bundle=getIntent().getBundleExtra("bundle");
        myAlj= (Alojamiento) bundle.getSerializable("ALOJ");
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