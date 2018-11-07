package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar appbar;
    private FirebaseAuth mAuth;
    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_view_headline);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.navview);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.content_frame, new Fragment1());
        getSupportActionBar().setTitle("Alojamientos");
        tx.commit();

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                fragment = new Fragment1();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                fragment = new Fragment2();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_3:
                                fragment = new Fragment3();
                                fragmentTransaction = true;
                                break;
                            case R.id.logout:
                                mAuth.signOut();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                        }

                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cargaralojamientos(){
        /*Ubicacion ubi = new Ubicacion(4.660557, -74.090749, 0.0, "Parque Simón Bolivar");
        Ubicacion ubi2 = new Ubicacion(4.628308, -74.064929, 0.0, "Pontificia Universidad Javeriana");
        Ubicacion ubi3 = new Ubicacion(4.596862, -74.072810, 0.0, "Biblioteca Luis Angel Arango");
        Ubicacion ubi4 = new Ubicacion(4.651711, -74.055819, 0.0, "Zona Gastronómica de Bogotá");

        String fotos = "https://img.olx.com.br/images/73/735830037100629.jpg;http://www.icesi.edu.co/blogs_estudiantes/hogarydecoracion/files/2017/04/inmueble.jpg";
        String desc = "at com.google.firebase.database.core.utilities.encoding.CustomClassMappergoogle.firebase.database.core.utilities.encoding.CustomClassMapper.convertToCustomClassat com.google.firebase.database.core.utilities.encoding.CustomClassMapper.convertToCustomClassat com.google.firebase.database.core.utilities.encoding.CustomClassMapper.convertToCustomClass";
        Alojamiento aloj = new Alojamiento("1","La rosa",desc,"Apartamento","Apartamento","Alojamiento Entero",10,2,5,2,true,"TV;Computador","Detector de humo",5000.0,fotos,ubi,null,"1",null,null,null);
        Alojamiento aloj2 = new Alojamiento("2","guadalupe",desc,"Casa","Casa","Alojamiento Entero",10,2,5,2,true,"TV;Computador","Detector de humo",5000.0,fotos,ubi2,null,"1",null,null,null);
        Alojamiento aloj3 = new Alojamiento("3","La perra",desc,"Apartamento","Apartamento","Alojamiento Entero",10,2,5,2,true,"TV;Computador","Detector de humo",5000.0,fotos,ubi3,null,"1",null,null,null);
        Alojamiento aloj4 = new Alojamiento("4","El castillo",desc,"Casa","Casa","Alojamiento Entero",10,2,5,2,true,"TV;Computador","Detector de humo",5000.0,fotos,ubi4,null,"1",null,null,null);



        myRef=database.getReference().child(PATH_ALOJAMIENTOS);
        String key = myRef.push().getKey();
        myRef=database.getReference().child(PATH_ALOJAMIENTOS).child(key);
        myRef.setValue(aloj);

        key = myRef.push().getKey();
        myRef=database.getReference().child(PATH_ALOJAMIENTOS).child(key);
        myRef.setValue(aloj2);

        key = myRef.push().getKey();
        myRef=database.getReference().child(PATH_ALOJAMIENTOS).child(key);
        myRef.setValue(aloj3);

        key = myRef.push().getKey();
        myRef=database.getReference().child(PATH_ALOJAMIENTOS).child(key);
        myRef.setValue(aloj4);*/
    }
}
