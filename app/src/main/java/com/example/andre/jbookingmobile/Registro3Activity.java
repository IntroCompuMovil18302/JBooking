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
    private ArrayList<String> paises;
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
        Bundle bundle=getIntent().getBundleExtra("bundle");
        myUsr= (Huesped) bundle.getSerializable("USR");
        paises = new ArrayList<>();
        consumeRESTVolley();

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



        nacionalidades = findViewById(R.id.nacionalidades);
        Log.i("LAG",paises.size()+"");
        HintAdapter hintAdapterTipo=new HintAdapter(this,android.R.layout.simple_list_item_1,this.paises);
        nacionalidades.setAdapter(hintAdapterTipo);
        nacionalidades.setSelection(hintAdapterTipo.getCount());


    }
    public void initactions(){
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gen= (String)spinGen.getSelectedItem();
                String nacionalidad= (String)nacionalidades.getSelectedItem();
                myUsr.setGenero(gen);
                paissel = (String)nacionalidades.getSelectedItem();
                nacionalidad = paissel;

                Log.i("NAC",paissel+"hola");
                myUsr.setNacionalidad(paissel);
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


    public void consumeRESTVolley(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://restcountries.eu/rest/v2/";
        String path = "region/Americas";
        String query = "?fields=name";
        StringRequest req = new StringRequest(Request.Method.GET, url+path+query,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        String data = (String)response;
                        jsonmake(data);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("TAG", "Error handling rest invocation"+error.getCause());
                    }
                }
        );
        queue.add(req);
    }

    protected void jsonmake(String response) {
        JSONArray result;
        List<String> newList = new ArrayList<String>();
        try {
            result = new JSONArray(response);
            for (int i = 0; i < 10/*result.length()*/; i++) {
                JSONObject jo = (JSONObject) result.get(i);
                // Log.d("TAG", "Json Object " + jo.toString());
                String name = (String) jo.get("name");
        //        paisesarray[i] = name;
                Log.i("CAG",name);
                paises.add(name.toString());
            }
            //paises.addAll(newList);
            for (String e:paises){
                Log.i("CCC",e);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
