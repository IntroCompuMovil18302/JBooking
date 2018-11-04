package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private TextView textViewRegistrar;
    private Button buttonIngresar;
    private Toolbar toolbar;
    private EditText editTextUser;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;

    private static final String miTag = "Auth";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar =  findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        editTextPassword = findViewById(R.id.editTextLoginPssw);
        editTextUser = findViewById(R.id.editTextLoginUser);
        textViewRegistrar = findViewById(R.id.textViewLoginResgistro);

        inicializarEventos();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        verificarSesion(currentUser);
    }

    private void inicializarEventos(){

        textViewRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
        buttonIngresar = findViewById(R.id.buttonLoginIngresar);
        buttonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userString = editTextUser.getText().toString();
                String userPassword = editTextPassword.getText().toString();
                if (verificarAuth(userString, userPassword)){
                    iniciarSesion(userString,userPassword);
                }else{
                    Toast.makeText(LoginActivity.this,"Complete correctamente los datos",Toast.LENGTH_SHORT).show();
                }
            }
        });

        editTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String userString = editTextUser.getText().toString();
                String userPassword = editTextPassword.getText().toString();
                if (verificarAuth(userString, userPassword)){
                    iniciarSesion(userString,userPassword);
                }else{
                    Toast.makeText(LoginActivity.this,"Complete correctamente los datos",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private boolean verificarAuth(String userMail, String userPassword){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(userMail);
        return !userPassword.isEmpty() && !userMail.isEmpty() && matcher.find();
    }

    private void verificarSesion(FirebaseUser userActual){
        if(userActual != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    private void iniciarSesion(String userMail, String userPassword){
        mAuth.signInWithEmailAndPassword(userMail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(miTag, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            verificarSesion(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(miTag, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Correo o contraseña incorrectos.",
                                    Toast.LENGTH_SHORT).show();
                            editTextPassword.setText("");
                        }
                    }
                });
    }
}
