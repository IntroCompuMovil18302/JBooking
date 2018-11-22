package com.example.andre.jbookingmobile.CrearLugares;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.andre.jbookingmobile.CrearAlojamientos.CrearAlojamiento4_1;
import com.example.andre.jbookingmobile.CrearAlojamientos.CrearAlojamiento7;
import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Lugar;
import com.example.andre.jbookingmobile.Entities.Ubicacion;
import com.example.andre.jbookingmobile.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;

public class CrearLugar2 extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button nextCrear2;
    private Toolbar toolbar;
    private Lugar myLug;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_lugar2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle=getIntent().getBundleExtra("bundle");
        myLug= (Lugar) bundle.getSerializable("LUG");


        nextCrear2=findViewById(R.id.butLug2);
        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        nextCrear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng current = mMap.getCameraPosition().target;
                //mMap.addMarker(new MarkerOptions().position(current).title("Marker in Sydney"));
                Ubicacion currentUb= new Ubicacion(current.latitude,current.longitude,0.0,myLug.getNombre());
                myLug.setUbicacion(currentUb);

                //Toast.makeText(CrearLugar2.this,current.latitude+" "+current.longitude + " "+ mAuth.getCurrentUser().getDisplayName(),Toast.LENGTH_SHORT).show();

                Bundle bundle=new Bundle();
                bundle.putSerializable("LUG",  myLug);
                Intent intent = new Intent(CrearLugar2.this, CrearLugar3.class); // TODO
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng bogota = new LatLng(4.65, -74.05);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bogota));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        Date currentTime = Calendar.getInstance().getTime();
        int hour = currentTime.getHours();
        if (hour>=18){
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.night));
        }else{
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.light));
        }

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
