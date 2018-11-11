package com.example.andre.jbookingmobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.Entities.Lugar;
import com.example.andre.jbookingmobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.StringTokenizer;

public class LugarAdaptador extends BaseAdapter {
    Context context;
    List<Lugar> lugares;

    public LugarAdaptador(Context context, List<Lugar> lugares){
        this.context = context;
        this.lugares = lugares;
    }

    @Override
    public int getCount() {
        return lugares.size();
    }

    @Override
    public Object getItem(int position) {
        return lugares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View nuevaVista = inflater.inflate(R.layout.view_item_alojamiento,null);
        ImageView imagen = nuevaVista.findViewById(R.id.imageViewIAlojamientoAdaptador);
        TextView lugar = nuevaVista.findViewById(R.id.textViewAlojaminetoAdapterUbicacion);
        TextView nombre = nuevaVista.findViewById(R.id.textViewAlojaminetoAdapterNombre);
        TextView precio = nuevaVista.findViewById(R.id.textViewAlojaminetoAdapterPrecio);

        Lugar lugarActual = lugares.get(position);
        lugar.setText(lugarActual.getUbicacion().getNombre());
        nombre.setText(lugarActual.getNombre());
        precio.setText("COP");

        StringTokenizer st = new StringTokenizer(lugarActual.getFotos(),";");
        String urlPrimeraFoto = st.nextToken();
        Picasso.with(context).load(urlPrimeraFoto).into(imagen);
        return nuevaVista;
    }
}
