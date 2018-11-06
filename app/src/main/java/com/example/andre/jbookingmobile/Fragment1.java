package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;


public class Fragment1 extends Fragment {

    TextView usernameview;
    TextView usernamemenu;
    private FirebaseAuth mAuth;
    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthListener;

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fragment1, container, false);
        View rootView = (View) inflater.inflate(R.layout.fragment_fragment1, container, false);
        mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        usernameview = (TextView) rootView.findViewById(R.id.usernameview);
        //usernamemenu = findViewById(R.id.textviewusermenu);
        Button consultaralojamiento = rootView.findViewById(R.id.buttonconsultarAlojamientos);
        if (mAuth.getCurrentUser().getDisplayName() != null){
            usernameview.setText("Bienvenido: "+mAuth.getCurrentUser().getDisplayName());
            //usernamemenu.setText(mAuth.getCurrentUser().getDisplayName());
            //Log.i("TAG",usernamemenu.getText().toString());
        }else{
            usernameview.setText("Bienvenido");
        }

        consultaralojamiento.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), ConsultarAlojamientoActivity.class));
            }
        });

        return rootView;
    }
}
