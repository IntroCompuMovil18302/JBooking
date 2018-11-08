package com.example.andre.jbookingmobile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MisAlojamientosFragment extends Fragment {

    private Button buttonAgregar;

    public MisAlojamientosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_alojamientos, container, false);
        buttonAgregar = view.findViewById(R.id.buttonMisAlojamientosAdd);
        initEvents();
        return view;
    }

    private void initEvents(){
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
