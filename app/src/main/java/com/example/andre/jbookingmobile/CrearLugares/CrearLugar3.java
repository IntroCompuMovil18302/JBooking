package com.example.andre.jbookingmobile.CrearLugares;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.andre.jbookingmobile.CrearAlojamientos.CrearAlojamiento7;
import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Anfitrion;
import com.example.andre.jbookingmobile.Entities.Lugar;
import com.example.andre.jbookingmobile.Entities.Propietario;
import com.example.andre.jbookingmobile.MainActivity;
import com.example.andre.jbookingmobile.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Thread.sleep;

public class CrearLugar3 extends AppCompatActivity {

    private Toolbar toolbar;
    private Lugar myLug;
    private Button nextCrear7;

    private LinearLayout linearLayoutGaleriaReg;
    private LayoutInflater inflaterReg;
    private ImageButton addImg;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String PATH_ALOJAMIENTOS="lugares";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    final int IMAGE_PICKER_REQUEST = 4;
    public Uri myimage = null;
    ArrayList<Bitmap> usrFotos;
    private int imagsCont;
    String path;
    private int contaux;
    String fotosHTTP = "";
    String aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_lugar3);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imagsCont = 0;
        initElementos();
        initEventos();
    }


    private void initElementos() {

        addImg= findViewById(R.id.agregarIMG2);
        nextCrear7= findViewById(R.id.butLugAdd);
        mAuth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();

        Bundle bundle=getIntent().getBundleExtra("bundle");
        myLug= (Lugar) bundle.getSerializable("LUG");
        linearLayoutGaleriaReg = findViewById(R.id.linearLayoutGaleriaReg2);
        inflaterReg = LayoutInflater.from(this);
    }


    private void initEventos() {
        nextCrear7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    publicar();
                    startActivity(new Intent(CrearLugar3.this, MainActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Si entro","Si si");
                int permissionCheck = ContextCompat.checkSelfPermission((Activity) v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permissionCheck== PackageManager.PERMISSION_GRANTED){
                    Intent pickImage = new Intent(Intent.ACTION_PICK);
                    pickImage.setType("image/*");
                    startActivityForResult(pickImage, IMAGE_PICKER_REQUEST);
                }else{
                    requestPermission((Activity) v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE, "BUENA ",  MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }

            }
        });

    }

    private void publicar() throws InterruptedException {

        subirimagenes();
        sleep(6000);
        getURISfotos();


    }

    private void subirimagenes() {
        InputStream imageStream;
        try {
            List<String> imagenes = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(myLug.getFotos(), ";");
            while (st.hasMoreTokens()) {
                imagenes.add(st.nextToken());
            }
            int cont=1;
            for (String img : imagenes) {
                imageStream = getContentResolver().openInputStream(Uri.parse(img));
                final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
                byte[] data = baos.toByteArray();
                path = "lugares/" + myLug.getNombre().replaceAll(" ","") +"/img";

                final StorageReference userimgref = storage.getReference(path+cont + ".png");
                Log.i("TagPth",path+cont+".png");

                StorageMetadata metadata = new StorageMetadata.Builder()
                        .setCustomMetadata("text", myLug.getId() + "")
                        .build();
                UploadTask uploadTask = userimgref.putBytes(data, metadata);
                imagsCont++;
                uploadTask.addOnSuccessListener(CrearLugar3.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        userimgref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Log.i("-------ON SUCCESS FIRST","-"+path+"--"+imagsCont);
                            }
                        });}
                });
                cont++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
                    final Uri imageUri = data.getData();
                    String fotos = ((myLug.getFotos() == null) ? "": myLug.getFotos());
                    fotos+=imageUri.toString()+";";
                    myLug.setFotos(fotos);
                    View view = inflaterReg.inflate(R.layout.imagen_alojamiento_vis, linearLayoutGaleriaReg,false);
                    ImageView imagenActual = view.findViewById(R.id.imageViewAlojamientoDetalleFotoReg);
                    Picasso.with(CrearLugar3.this).load(imageUri).into(imagenActual);
                    Toast.makeText(this,myLug.getFotos(),Toast.LENGTH_LONG).show();
                    linearLayoutGaleriaReg.addView(view);
                }
                return;
            }
            case REQUEST_IMAGE_CAPTURE:{
                if (resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    if(imageBitmap!=null || usrFotos.contains(imageBitmap))
                        usrFotos.add(imageBitmap);
                    addImg.setImageBitmap(imageBitmap);
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

    public void getURISfotos() {
        /*Log.i("-------ON SUCCESS"+fotosHTTP+".",path+"--"+imagsCont);
        StorageReference userimgref ;
        StorageReference island;
        //String npath = "alojamientos/andyflow/img1.png";
        int i = 1;

        String npath = "alojamientos/"+myAlj.getNombre().toString()+"/img".concat(Integer.toString(i)).concat(".png");
        userimgref = storage.getReference();
        island = userimgref.child(npath);
        Log.i("------------ON FOR"+fotosHTTP+"--","Si si ");
        island.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                fotosHTTP=fotosHTTP + uri.toString()+";";
                Log.i("Tag",fotosHTTP);
                Log.i("----ON SUCCESS SECOND"+fotosHTTP+".","Entro al on succes");
                myAlj.setFotos(fotosHTTP);
                Log.i("Tag3",myAlj.getFotos());
                myRef=database.getReference().child(PATH_ALOJAMIENTOS);
                String key = myRef.push().getKey();
                myRef=database.getReference().child(PATH_ALOJAMIENTOS).child(key);
                myRef.setValue(myAlj);
            }

        });*/

        StorageReference userimgref;
        StorageReference island;
        fotosHTTP="";
        contaux = 1;
        for (int i=1;i<=imagsCont;i++){
            //Log.i("-------ON SUCCESS"+fotosHTTP+".",path+"--"+imagsCont);

            //String npath = "alojamientos/andyflow/img1.png";
            String npath = "lugares/"+myLug.getNombre().toString()+"/img".concat(Integer.toString(i)).concat(".png");
            Log.i("npath",npath);
            userimgref = storage.getReference().child(npath);
            Log.i("------------ON FOR"+fotosHTTP+"--","Si si ");
            userimgref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    fotosHTTP=fotosHTTP + uri.toString()+";";
                    Log.i("Tag",fotosHTTP);
                    Log.i("----ON SUCCESS SECOND"+fotosHTTP+".","Entro al on succes");

                    if (contaux >= 4){

                        myLug.setFotos(fotosHTTP);
                        cargarContenido();


                        //myRef.setValue(myAlj);

                    }
                    contaux++;

                }

            });

        }
        Log.i("Tag3",myLug.getFotos());



    }

    public void cargarContenido(){
        myRef = database.getReference("/users/propietarios");
        myRef. addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Propietario locaciones = singleSnapshot.getValue(Propietario.class);
                    Log.i("TAG1",locaciones.getCorreo());
                    Log.i("TAG1",mAuth.getCurrentUser().getEmail());
                    if (locaciones.getCorreo().equals(mAuth.getCurrentUser().getEmail())){
                        Log.i("TAG1","Encontro un correo");
                        // PONER CODIGO PARA CARGAR LA IMAGEN DESDE HUESPED

                        myLug.setPropietario(locaciones);

                        FirebaseDatabase database2= FirebaseDatabase.getInstance();
                        DatabaseReference myRef2 = database.getReference().child(PATH_ALOJAMIENTOS);
                        String key = myRef2.push().getKey();
                        myRef2=database2.getReference().child(PATH_ALOJAMIENTOS).child(key);
                        myRef2.setValue(myLug);

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "error en la consulta", databaseError.toException());
            }
        });

    }
}
