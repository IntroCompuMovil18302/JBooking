package com.example.andre.jbookingmobile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Comentario;
import com.example.andre.jbookingmobile.Entities.Lugar;
import com.example.andre.jbookingmobile.Entities.Reserva;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TourActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder mGeocoder;
    private FirebaseAuth mAuth;
    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthListener;
    private EditText mapaDireccion;
    private Marker usuario;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private LocationCallback locationCallback;
    private EditText editTextDireccion;
    private TextView bienvenido;
    private LatLng ubicacionusuario;
    private LatLng newlocation;
    private Lugar lugar;
    private Alojamiento alojamiento;
    private Reserva reserva;
    LatLng obecjtrute;
    LatLng origena;
    Boolean drawer = false;
    Map<String,Boolean> dobleclicklugar;
    private List<Lugar> lugaresmapa = new ArrayList<Lugar>();

    public static final double lowerLeftLatitude = 4.486388;
    public static final double lowerLeftLongitude = -74.227082;
    public static final double upperRightLatitude = 4.771100;
    public static final double upperRightLongitude = -74.015581;
    public static final double RADIUS_OF_EARTH_KM = 6371;
    private boolean firstTime = true;

    FirebaseDatabase database;
    DatabaseReference myRef;
    private Boolean entro = false;

    private final static int LOCALIZATION_PERMISSION = 0;
    private final static String justificacion = "Acepte porfavor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION,justificacion,LOCALIZATION_PERMISSION);
        mapFragment.getMapAsync(this);
        reserva = (Reserva) getIntent().getExtras().getSerializable("reserva");
        origena = new LatLng(reserva.getAlojamiento().getUbicacion().getLatitud(),reserva.getAlojamiento().getUbicacion().getLongitud());
        mGeocoder = new Geocoder(getBaseContext());
        mLocationRequest = createLocationRequest();
        database= FirebaseDatabase.getInstance();
        dobleclicklugar = new HashMap<String,Boolean>();
        initEventos();
        initLocationUpdates();
        newlocation = null;



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


        LatLng bogota = new LatLng(reserva.getAlojamiento().getUbicacion().getLatitud(), reserva.getAlojamiento().getUbicacion().getLongitud());
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
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
    protected void onResume() {
        super.onResume();
        initLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }
    private void requestPermission(Activity context, String permission, String explanation, int requestId ){
        if (ContextCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?Â  Â
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,permission)) {
                Toast.makeText(context, explanation, Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permission}, requestId);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case LOCALIZATION_PERMISSION : {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Aqui el significa que ya ha habido respuesta, y el permiso fue concedido
                }
                break;
            }
        }
    }
    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);//tasa de refresco en milisecon
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void initLocationUpdates(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest,locationCallback,null);
            initEventos();
        }
    }

    private void initEventos() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                LatLng ubicacionactual = new LatLng(reserva.getAlojamiento().getUbicacion().getLatitud(), reserva.getAlojamiento().getUbicacion().getLongitud());
                mMap.addMarker(new MarkerOptions().position(ubicacionactual).icon(BitmapDescriptorFactory.fromResource(R.drawable.housedr)));

                loadTouristicPlaces();
                pintlugares(ubicacionactual);

            }
        };
    }

    private void stopLocationUpdates(){
        //fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private LatLng obtenerPosByDireccion(String direccion){
        if (!direccion.isEmpty()){
            LatLng position = null;
            try{
                List<Address> addresses = mGeocoder.getFromLocationName(direccion,2);//lowerLeftLatitude,lowerLeftLongitude,upperRightLatitude,upperRightLongitude);
                if (addresses !=  null && !addresses.isEmpty()){
                    Address addressResult = addresses.get(0);
                    position = new LatLng(addressResult.getLatitude(),addressResult.getLongitude());
                }else {
                    Toast.makeText(TourActivity.this, "Lugar no encontrado", Toast.LENGTH_SHORT).show();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return position;
        }else {
            Toast.makeText(TourActivity.this, "La direccion esta vacia", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private boolean esDireccionValida(String dir){
        return !dir.isEmpty();
    }

    private void pintarYMoverCasa(LatLng pos){
        if (mMap != null && pos != null){
            MarkerOptions lugar =  new MarkerOptions().position(pos).icon(BitmapDescriptorFactory.fromResource(R.drawable.casitaperro));
            lugar.title(editTextDireccion.getText().toString());
            mMap.addMarker(lugar);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        }
    }

    private void drawPath(LatLng origin, LatLng dest){
        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(origin, dest);
        TourActivity.ReadTask downloadTask = new TourActivity.ReadTask();

        downloadTask.execute(url);

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        //      String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
//        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        //    String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        //  String output = "json";

        // Building the url to the web service
        // String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters+"&mode=driving&key=AIzaSyC23CO3mVtybzOu9Z1aX66d506XEWi1tCA";


        //return url;
        return "https://maps.googleapis.com/maps/api/directions/json?origin="+origin.latitude+","+origin.longitude+"&destination="+dest.latitude+","+dest.longitude+"&sensor=false&mode=driving&key=AIzaSyAchBP3BNVvqSHTbu02DA0PQur2d_RKE_M";

    }

    private class ReadTask extends AsyncTask<String, Void , String> {

        @Override
        protected String doInBackground(String... url) {
            // TODO Auto-generated method stub
            String data = "";
            try {
                MapHttpConnection http = new MapHttpConnection();
                data = http.readUr(url[0]);


            } catch (Exception e) {
                // TODO: handle exception
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new TourActivity.ParserTask().execute(result);
        }

    }



    private class ParserTask extends AsyncTask<String,Integer, List<List<HashMap<String , String >>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {
            // TODO Auto-generated method stub
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(4);
                polyLineOptions.color(Color.RED);
            }
            if (mMap!=null && polyLineOptions != null){
                mMap.addPolyline(polyLineOptions);
            }

        }}

    public double distance(double lat1, double long1, double lat2, double long2) {
        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(long1 - long2);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double result = RADIUS_OF_EARTH_KM * c;
        return Math.round(result*100.0)/100.0;
    }

    public void loadTouristicPlaces() {
        myRef = database.getReference("/lugares");
        myRef. addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Lugar lugares = singleSnapshot.getValue(Lugar.class);

                    LatLng ubicacionActual = new LatLng(lugares.getUbicacion().getLatitud(), lugares.getUbicacion().getLongitud());
                    if (distance(reserva.getAlojamiento().getUbicacion().getLatitud(),reserva.getAlojamiento().getUbicacion().getLongitud(),ubicacionActual.latitude,ubicacionActual.longitude)<5.0){
                        List<Comentario> comentarios = lugares.getComentarios();
                        int sum = 0;
                        for (Comentario c : comentarios){
                            sum += c.getPuntuacion();
                        }
                        if (comentarios.size() == 0){
                            sum = sum;
                        }else{
                            sum = sum/comentarios.size();
                        }
                        MarkerOptions lugar =  new MarkerOptions().position(ubicacionActual)
                                .title(lugares.getUbicacion().getNombre())
                                .snippet("Calificacion: "+sum)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.tourist));
                        //lugar.title(lugares.getUbicacion().getNombre());
                        mMap.addMarker(lugar);
                        //lugaresmapa.add(lugares);
                        dobleclicklugar.put(lugares.getNombre(),Boolean.FALSE);
                        addtolist(lugares);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "error en la consulta", databaseError.toException());
            }
        });
    }

    public void pintlugares(LatLng origen){
        if (!entro && lugaresmapa.size()>0){
            int count = 1;
            LatLng origin = origen;
            Log.i("TAGA",lugaresmapa.size()+"");
            for (Lugar l:this.lugaresmapa){
                LatLng destino = new LatLng(l.getUbicacion().getLatitud(),l.getUbicacion().getLongitud());
                drawPath(origin, destino);
                origin = destino;
            }
            entro = true;
        }
    }

    public void addtolist(Lugar lugar){
        if (lugaresmapa.size()!=0){
            double first = distance(origena.latitude,origena.longitude,lugar.getUbicacion().getLatitud(),lugar.getUbicacion().getLongitud());
            double second = distance(origena.latitude,origena.longitude,lugaresmapa.get(0).getUbicacion().getLatitud(),lugaresmapa.get(0).getUbicacion().getLongitud());
            if (first <= second){
                this.lugaresmapa.add(0,lugar);
            }
            else{
                this.lugaresmapa.add(lugar);
            }
        }else{
            this.lugaresmapa.add(lugar);
        }



    }
}
