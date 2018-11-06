package com.example.andre.jbookingmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.andre.jbookingmobile.CrearAlojamientos.CrearAlojamiento1;

import java.io.Serializable;


public class Fragment2 extends Fragment {

    private  Button newAlj ;

    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment2,
                container, false);

        newAlj = view.findViewById(R.id.butNewAloj);
        newAlj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CrearAlojamiento1.class);
                startActivity(intent);
            }
        });
        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }
}