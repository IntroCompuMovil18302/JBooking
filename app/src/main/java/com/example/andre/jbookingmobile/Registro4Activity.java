package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.andre.jbookingmobile.Entities.Anfitrion;
import com.example.andre.jbookingmobile.Entities.Huesped;
import com.example.andre.jbookingmobile.Entities.Propietario;
import com.example.andre.jbookingmobile.Entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Registro4Activity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageButton usrFoto;
    private Button buttReg;
    private Object myUser;
    private String Rol;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String PATH_USERS="users";
    public static final String PATH_ANFITRIONES="anfitriones";
    public static final String PATH_HUESPEDES="huespedes";
    public static final String PATH_PRPIETARIOS="propietarios";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro4);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        usrFoto= findViewById(R.id.imageButton);
        buttReg = findViewById(R.id.buttonRegistroCompletarFoto);
        initEventos();
        Bundle bundle=getIntent().getBundleExtra("bundle");
        myUser=  bundle.getSerializable("USR");

        Rol = bundle.getString("Rol");
        mAuth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        buttReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(myUser);
            }
        });

}

    private void initEventos() {
        buttReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro4Activity.this, Registro2Activity.class);
                intent.putExtra("USER", (Serializable) myUser);
                startActivity(intent);
            }
        });
    }
        public void register(final Object myUser){
            String email = null;
        String password = null;

if(Rol.equals("PROPIETARIO")){
    Propietario myPro= (Propietario) myUser;
    email = myPro.getCorreo();
    password = myPro.getContrasena();


}else if(Rol.equals("HUESPED")){
    Huesped myPro= (Huesped) myUser;
    email = myPro.getCorreo();
    password = myPro.getContrasena();


}else if(Rol.equals("ANFITRION")){
    Anfitrion myPro= (Anfitrion) myUser;
    email = myPro.getCorreo();
    password = myPro.getContrasena();


}


        final String finalPassword = password;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("auth", "createUserWithEmail:onComplete:" + task.isSuccessful());
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user!=null){ //Update user Info
                                UserProfileChangeRequest.Builder upcrb = new UserProfileChangeRequest.Builder();
                             //   upcrb.setDisplayName(mUserName.getText().toString()+" "+mUserLastName.getText().toString());
                                upcrb.setPhotoUri(Uri.parse("path/to/pic"));//fake uri, real one coming soon
                                user.updateProfile(upcrb.build());


                                if(Rol.equals("PROPIETARIO")){
                                    Propietario myPro= (Propietario) myUser;
                                    myPro.setContrasena(String.valueOf(finalPassword.hashCode()));
                                    myRef=database.getReference().child(PATH_USERS).child(PATH_PRPIETARIOS);
                                    String key = myRef.push().getKey();
                                    myRef=database.getReference().child(PATH_USERS).child(PATH_PRPIETARIOS).child(key);
                                    myRef.setValue(myPro);
                                }else if(Rol.equals("HUESPED")){
                                    Huesped myPro= (Huesped) myUser;
                                    myPro.setContrasena(String.valueOf(finalPassword.hashCode()));
                                    myRef=database.getReference().child(PATH_USERS).child(PATH_HUESPEDES);
                                    String key = myRef.push().getKey();
                                    myRef=database.getReference().child(PATH_USERS).child(PATH_HUESPEDES).child(key);
                                    myRef.setValue(myPro);
                                }else if(Rol.equals("ANFITRION")){
                                    Anfitrion myPro= (Anfitrion) myUser;
                                    myPro.setContrasena(String.valueOf(finalPassword.hashCode()));
                                    myRef=database.getReference().child(PATH_USERS).child(PATH_ANFITRIONES);
                                    String key = myRef.push().getKey();
                                    myRef=database.getReference().child(PATH_USERS).child(PATH_ANFITRIONES).child(key);
                                    myRef.setValue(myPro);
                                }

                                startActivity(new Intent(Registro4Activity.this, MainActivity.class)); //o en el listener
                            }
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(Registro4Activity.this, R.string.auth_failed+ task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                            Log.e("auth", task.getException().getMessage());
                        }
                    }
                });
    }
}
