package com.example.andre.jbookingmobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Comentario;
import com.example.andre.jbookingmobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.StringTokenizer;

public class ComentarioAdaptador extends BaseAdapter {

    Context context;
    List<Comentario> comentarios;

    public ComentarioAdaptador(Context context, List<Comentario> comentarios){
        this.context = context;
        this.comentarios = comentarios;
    }

    @Override
    public int getCount() {
        return comentarios.size();
    }

    @Override
    public Object getItem(int position) {
        return comentarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View nuevaVista = inflater.inflate(R.layout.view_item_comentario,null);
        TextView username = nuevaVista.findViewById(R.id.textViewusernamei);
        TextView calificacion = nuevaVista.findViewById(R.id.textViewcalificacionlist);
        TextView descripcion = nuevaVista.findViewById(R.id.textViewDescripcioni);


        Comentario comentarioactual = comentarios.get(position);
        username.setText(comentarioactual.getNombreUsuario());
        calificacion.setText(comentarioactual.getPuntuacion()+"");
        descripcion.setText(comentarioactual.getComentario());
        return nuevaVista;
    }
}
