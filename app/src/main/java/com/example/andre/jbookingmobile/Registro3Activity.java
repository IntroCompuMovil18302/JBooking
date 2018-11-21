package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.andre.jbookingmobile.Adapters.HintAdapter;
import com.example.andre.jbookingmobile.Entities.Huesped;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.os.SystemClock.sleep;

public class Registro3Activity extends AppCompatActivity {

    private Button regButton;
    private Spinner spinGen;
    private EditText regNac;
    private Huesped myUsr;
    ArrayList<String> countries = new ArrayList<String>();
    private Spinner nacionalidades;
    String paissel = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro3);

        initvariables();
        initactions();



    }

    public void initvariables(){
        regButton= findViewById(R.id.buttonRegistro3);
        spinGen=findViewById(R.id.spinRegistrarGenero);
        nacionalidades = findViewById(R.id.nacionalidades);
        Bundle bundle=getIntent().getBundleExtra("bundle");
        myUsr= (Huesped) bundle.getSerializable("USR");
        countries.add("Seleccione un país");
        nacionalityREST();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, countries);
        nacionalidades.setAdapter(adapter);
        //consumeRESTVolley();

        /*paises.add("Argentina");
        paises.add("Bolivia");
        paises.add("Brasil");
        paises.add("Chile");
        paises.add("Colombia");
        paises.add("Costa Rica");
        paises.add("Cuba");
        paises.add("Ecuador");
        paises.add("El salvador");
        paises.add("Guatemala");
        paises.add("Haiti");
        paises.add("Honduras");
        paises.add("Mexico");
        paises.add("Nicaragua");
        paises.add("Panama");
        paises.add("Paraguay");
        paises.add("Peru");
        paises.add("Republica Dominicana");
        paises.add("Uruguay");
        paises.add("Venezuela");*/

        Log.i("LAG",countries.size()+"");


    }
    public void initactions(){
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gen= (String)spinGen.getSelectedItem();
                String nacionalidad= (String)nacionalidades.getSelectedItem();
                myUsr.setGenero(gen);
                //paissel = (String)nacionalidades.getSelectedItem();
                //nacionalidad = paissel;

                Log.i("NAC",nacionalidad+"hola");
                myUsr.setNacionalidad(nacionalidad);
                Log.i("NAC",myUsr.getNacionalidad()+"hola");


                Bundle bundle=new Bundle();
                bundle.putString("Rol", "HUESPED");
                bundle.putSerializable("USR",myUsr);
                Intent intent = new Intent(Registro3Activity.this, Registro4Activity.class);
                intent.putExtra("bundle",bundle);
                startActivity(intent);

            }
        });
    }

    private void nacionalityREST () {
        RequestQueue queue = Volley.newRequestQueue(this);
        String query = "https://restcountries.eu/rest/v2/all?fields=name";
        StringRequest req = new StringRequest(Request.Method.GET, query,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        String data = (String)response;
                        try {
                            JSONArray nacionalities = new JSONArray(data);
                            for(int i=0; i<nacionalities.length(); i++)
                            {
                                JSONObject jo = (JSONObject) nacionalities.get(i);
                                String n = (String)jo.get("name");
                                countries.add(n);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("TAG", "Error handling rest invocation"+error.getCause());
                    }
                });
        queue.add(req);
    }
}
