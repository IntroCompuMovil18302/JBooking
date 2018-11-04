package com.example.andre.jbookingmobile;

import android.app.DatePickerDialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


import com.example.andre.jbookingmobile.Entities.Huesped;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.andre.jbookingmobile.Entities.Utils.getAge;

public class Registro2Activity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textViewDate;
    private Calendar cal;
    private int year, month, day;
    private Button regButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Huesped myUsr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textViewDate =  findViewById(R.id.textViewRegistro2Date);
        regButton = findViewById(R.id.butReg2);
        cal = Calendar.getInstance();
        Bundle bundle=getIntent().getBundleExtra("bundle");
        myUsr= (Huesped) bundle.getSerializable("USR");
        Log.i("auth", myUsr.getNombre());

        initEvents();

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

    private void initEvents(){
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Registro2Activity.this,mDateSetListener,year,month,day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String fecha = day + "/" + month +"/"+year;
                textViewDate.setText(fecha);
                try {
                    myUsr.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(year+"-"+month+"-"+day));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("Rol", "HUESPED");
                bundle.putSerializable("USR", (Serializable) myUsr);
                Intent intent = new Intent(Registro2Activity.this, Registro3Activity.class);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
    }

}
