package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Anfitrion;
import com.example.andre.jbookingmobile.Entities.Huesped;
import com.example.andre.jbookingmobile.Entities.Propietario;
import com.example.andre.jbookingmobile.Entities.Usuario;
import com.example.andre.jbookingmobile.Services.NotificationService;
import com.example.andre.jbookingmobile.Services.RnotificationService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class Fragment3 extends Fragment {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseDatabase database;
    DatabaseReference myRef;
    private List<Huesped> huespedaux;
    private List<Anfitrion> anfitriondaux;
    private List<Propietario> propietarioaux;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String PATH_USUARIOS = "users/";
    TextView welcoming;
    ImageView userimage;
    String retorno;
    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        welcoming = view.findViewById(R.id.textviewnombre);
        userimage = view.findViewById(R.id.userimage);
        huespedaux = new ArrayList<Huesped>();
        anfitriondaux = new ArrayList<Anfitrion>();
        propietarioaux = new ArrayList<Propietario>();

        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null){
            welcoming.setText("Bienvenido");
        }else{
            welcoming.setText("Bienvenido "+mAuth.getCurrentUser().getDisplayName());
        }
        database = FirebaseDatabase.getInstance();

        loadHuesped();



        Log.i("TAG","Holamundo" + huespedaux.size());
        for (Huesped e : huespedaux){
            Log.i("TAG", e.getCorreo());
        }





        /*String path = "";
        Log.i("Tag","Antes del path");
        String tipo = huespedes();
        if (tipo.equals("Huesped")){
            path ="userimages/huesped/"+mAuth.getCurrentUser().getEmail();
            Log.i("Tag2",path);
        }else{
            if (tipo.equals("Anfitrion")){
                path ="userimages/anfitrion/"+mAuth.getCurrentUser().getEmail();
                Log.i("Tag3",path);
            }
            else{
                if (tipo.equals("Propietario")){
                    path ="userimages/propietario/"+mAuth.getCurrentUser().getEmail();
                    Log.i("Tag4",path);
                }else{
                    Log.i("Tag5",tipo);
                }
            }
        }*/
////////////////////////////////////////////////////////////////
       /* String path1 = "userimages/huesped/"+mAuth.getCurrentUser().getEmail()+".png";
        Log.i("Tag",path1);
        String path2 = "userimages/anfitrion/"+mAuth.getCurrentUser().getEmail()+".png";
        String path3 = "userimages/propietario/"+mAuth.getCurrentUser().getEmail()+".png";

        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = null;


        Log.i("Tag1",storageRef.child(path1).getDownloadUrl()+"");


        islandRef = storageRef.child(path1);

        showimage(islandRef);


        Log.i("Tagu",islandRef.getPath());*/

        return view;
    }

    public void showimage(final StorageReference islandRef){
        final long ONE_MEGABYTE = 1024 * 1024 * 5;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                userimage.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }

    public void loadHuesped() {
        List<Huesped> aux = new ArrayList<Huesped>();
        myRef = database.getReference("/users/huespedes");
        myRef. addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean  yaencontro = false;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Huesped locaciones = singleSnapshot.getValue(Huesped.class);
                    Log.i("TAG1",locaciones.getCorreo());
                    Log.i("TAG1",mAuth.getCurrentUser().getEmail());
                    if (locaciones.getCorreo().equals(mAuth.getCurrentUser().getEmail())){
                        Log.i("TAG1","Encontro un correo");
                        // PONER CODIGO PARA CARGAR LA IMAGEN DESDE HUESPED
                        String path = "userimages/huesped/"+mAuth.getCurrentUser().getEmail()+".png";
                        database = FirebaseDatabase.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference islandRef = storageRef.child(path);
                        showimage(islandRef);
                        yaencontro = true;
                    }
                }
                if (!yaencontro){
                    loadAnfitrion();
                }
                Log.i("TAG1","Holamundo" + huespedaux.size());
                for (Huesped e : huespedaux){
                    Log.i("TAG1", e.getCorreo());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "error en la consulta", databaseError.toException());
            }
        });
    }



    public void loadAnfitrion() {
        List<Huesped> aux = new ArrayList<Huesped>();
        myRef = database.getReference("/users/anfitriones");
        myRef. addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean  yaencontro = false;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Anfitrion locaciones = singleSnapshot.getValue(Anfitrion.class);
                    Log.i("TAG1",locaciones.getCorreo());
                    Log.i("TAG1",mAuth.getCurrentUser().getEmail());
                    if (locaciones.getCorreo().equals(mAuth.getCurrentUser().getEmail())){
                        Log.i("TAG1","Encontro un correo");
                        // PONER CODIGO PARA CARGAR LA IMAGEN DESDE HUESPED
                        String path = "userimages/anfitrion/"+mAuth.getCurrentUser().getEmail()+".png";
                        database = FirebaseDatabase.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference islandRef = storageRef.child(path);
                        showimage(islandRef);
                        yaencontro = true;
                        Intent intent = new Intent(getView().getContext(), RnotificationService.class);
                        getView().getContext().startService(intent);
                    }
                }
                if (!yaencontro){
                    loadPropietario();
                }
                Log.i("TAG1","Holamundo" + huespedaux.size());
                for (Huesped e : huespedaux){
                    Log.i("TAG1", e.getCorreo());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "error en la consulta", databaseError.toException());
            }
        });
    }


    public void loadPropietario() {
        List<Huesped> aux = new ArrayList<Huesped>();
        myRef = database.getReference("/users/propietarios");
        myRef. addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean  yaencontro = false;
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Propietario locaciones = singleSnapshot.getValue(Propietario.class);
                    Log.i("TAG1",locaciones.getCorreo());
                    Log.i("TAG1",mAuth.getCurrentUser().getEmail());
                    if (locaciones.getCorreo().equals(mAuth.getCurrentUser().getEmail())){
                        Log.i("TAG1","Encontro un correo");
                        // PONER CODIGO PARA CARGAR LA IMAGEN DESDE HUESPED
                        String path = "userimages/propietario/"+mAuth.getCurrentUser().getEmail()+".png";
                        database = FirebaseDatabase.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference islandRef = storageRef.child(path);
                        showimage(islandRef);
                        yaencontro = true;
                    }
                }
                if (!yaencontro){
                    Log.i("TAG", "FOTO NO ENCONTRADA");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "error en la consulta", databaseError.toException());
            }
        });
    }



}