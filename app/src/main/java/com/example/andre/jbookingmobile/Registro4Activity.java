package com.example.andre.jbookingmobile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

public class Registro4Activity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageButton usrFoto;
    private Button buttReg;
    private Object myUser;
    private String Rol;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseDatabase database;
    DatabaseReference myRef;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    final int IMAGE_PICKER_REQUEST = 4;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String PATH_USERS="users";
    public static final String PATH_ANFITRIONES="anfitriones";
    public static final String PATH_HUESPEDES="huespedes";
    public static final String PATH_PRPIETARIOS="propietarios";
    public Uri myimage = null;
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

        usrFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Si entro","Si si");
                int permissionCheck =ContextCompat.checkSelfPermission((Activity) v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permissionCheck==PackageManager.PERMISSION_GRANTED){
                    Intent pickImage = new Intent(Intent.ACTION_PICK);
                    pickImage.setType("image/*");
                    startActivityForResult(pickImage, IMAGE_PICKER_REQUEST);
                }else{
                    requestPermission((Activity) v.getContext(),Manifest.permission.READ_EXTERNAL_STORAGE, "BUENA ",  MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }

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


                                if(Rol.equals("PROPIETARIO")){
                                    Propietario aux = (Propietario) myUser;
                                    upcrb.setDisplayName(aux.getNombre());
                                    upcrb.setPhotoUri(Uri.parse("path/to/pic"));//fake uri, real one coming soon
                                    user.updateProfile(upcrb.build());

                                    Propietario myPro= (Propietario) myUser;
                                    myPro.setContrasena(String.valueOf(finalPassword.hashCode()));
                                    myPro.setFoto(myimage.toString());
                                    final InputStream imageStream;
                                    try {
                                        imageStream = getContentResolver().openInputStream(myimage);
                                        final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                                        byte[] data = baos.toByteArray();

                                        String path  = "userimages/propietario/" + myPro.getCorreo() + ".png";
                                        StorageReference userimgref = storage.getReference(path);

                                        StorageMetadata metadata = new StorageMetadata.Builder()
                                                .setCustomMetadata("text",myPro.getId()+"")
                                                .build();
                                        UploadTask uploadTask = userimgref.putBytes(data,metadata);
                                        uploadTask.addOnSuccessListener(Registro4Activity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Log.i("Entro","Exito");
                                            }
                                        });
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }



                                    myRef=database.getReference().child(PATH_USERS).child(PATH_PRPIETARIOS);
                                    String key = myRef.push().getKey();
                                    myRef=database.getReference().child(PATH_USERS).child(PATH_PRPIETARIOS).child(key);
                                    myRef.setValue(myPro);
                                }else if(Rol.equals("HUESPED")){
                                    Huesped aux = (Huesped) myUser;
                                    upcrb.setDisplayName(aux.getNombre());
                                    upcrb.setPhotoUri(Uri.parse("path/to/pic"));//fake uri, real one coming soon
                                    user.updateProfile(upcrb.build());

                                    Huesped myPro= (Huesped) myUser;
                                    myPro.setContrasena(String.valueOf(finalPassword.hashCode()));
                                    myPro.setFoto(myimage.toString());

                                    final InputStream imageStream;
                                    try {
                                        imageStream = getContentResolver().openInputStream(myimage);
                                        final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                                        byte[] data = baos.toByteArray();

                                        String path  = "userimages/huesped/" + myPro.getCorreo() + ".png";
                                        StorageReference userimgref = storage.getReference(path);

                                        StorageMetadata metadata = new StorageMetadata.Builder()
                                                .setCustomMetadata("text",myPro.getId()+"")
                                                .build();
                                        UploadTask uploadTask = userimgref.putBytes(data,metadata);
                                        uploadTask.addOnSuccessListener(Registro4Activity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Log.i("Entro","Exito");
                                            }
                                        });
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    myRef=database.getReference().child(PATH_USERS).child(PATH_HUESPEDES);
                                    String key = myRef.push().getKey();
                                    myRef=database.getReference().child(PATH_USERS).child(PATH_HUESPEDES).child(key);
                                    myRef.setValue(myPro);
                                }else if(Rol.equals("ANFITRION")){
                                    Anfitrion aux = (Anfitrion) myUser;
                                    upcrb.setDisplayName(aux.getNombre());
                                    upcrb.setPhotoUri(Uri.parse("path/to/pic"));//fake uri, real one coming soon
                                    user.updateProfile(upcrb.build());

                                    //Log.i("TAG",user.getDisplayName());
                                    Anfitrion myPro= (Anfitrion) myUser;
                                    myPro.setContrasena(String.valueOf(finalPassword.hashCode()));
                                    myPro.setFoto(myimage.toString());
                                    //Log.i("TAG",myPro.getNombre());
                                    final InputStream imageStream;
                                    try {
                                        imageStream = getContentResolver().openInputStream(myimage);
                                        final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                                        byte[] data = baos.toByteArray();

                                        String path  = "userimages/anfitrion/" + myPro.getCorreo() + ".png";
                                        StorageReference userimgref = storage.getReference(path);

                                        StorageMetadata metadata = new StorageMetadata.Builder()
                                                .setCustomMetadata("text",myPro.getId()+"")
                                                .build();
                                        UploadTask uploadTask = userimgref.putBytes(data,metadata);
                                        uploadTask.addOnSuccessListener(Registro4Activity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Log.i("Entro","Exito");
                                            }
                                        });
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    myRef=database.getReference().child(PATH_USERS).child(PATH_ANFITRIONES);
                                    String key = myRef.push().getKey();
                                    myRef=database.getReference().child(PATH_USERS).child(PATH_ANFITRIONES).child(key);
                                    //Log.i("TAG", myPro.getCorreo());
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

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case IMAGE_PICKER_REQUEST: {
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        myimage = imageUri;
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        usrFoto.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            case REQUEST_IMAGE_CAPTURE:{
                if (resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    usrFoto.setImageBitmap(imageBitmap);
                }
            }
        }
    }

    private void requestPermission(Activity context, String permiso, String justificacion, int idCode){
        if (ContextCompat.checkSelfPermission(context, permiso)!= PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(context, permiso)) {

            }
            ActivityCompat.requestPermissions(context,new String[]{permiso},
                    idCode);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, continue with task related to permission
                    /*Toast.makeText(this,"PERMISO CONCEDIDO",Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(this, activity_camara.class);
                    intent2.putExtra("GRANT", "True");
                    startActivity(intent2);*/
                } else {
                    /*Toast.makeText(this,"PERMISO DENEGADO",Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(this, activity_camara.class);
                    intent2.putExtra("GRANT", "False");
                    startActivity(intent2);*/
                    // permission denied, disable functionality that depends on this permission.
                }

            }

        }
    }
}
