package com.example.andre.jbookingmobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andre.jbookingmobile.Entities.Lugar;
import com.example.andre.jbookingmobile.Entities.Reserva;
import com.example.andre.jbookingmobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.StringTokenizer;

public class ReservaAdaptador extends BaseAdapter {
    Context context;
    List<Reserva> reservas;

    public ReservaAdaptador(Context context, List<Reserva> reservas){
        this.context = context;
        this.reservas = reservas;
    }

    @Override
    public int getCount() {
        return reservas.size();
    }

    @Override
    public Object getItem(int position) {
        return reservas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View nuevaVista = inflater.inflate(R.layout.view_item_reserva,null);
        ImageView imagen = nuevaVista.findViewById(R.id.imageViewIReservaAdaptador);
        TextView lugar = nuevaVista.findViewById(R.id.textViewReservaAdapterUbicacion);
        TextView nombre = nuevaVista.findViewById(R.id.textViewReservaAdapterNombre);
        TextView precio = nuevaVista.findViewById(R.id.textViewReservaAdapterPrecio);

        Reserva reservaActual = reservas.get(position);
        lugar.setText(reservaActual.getAlojamiento().getUbicacion().getNombre());
        nombre.setText(reservaActual.getAlojamiento().getNombre());
        precio.setText("COP" + reservaActual.getValor());

        StringTokenizer st = new StringTokenizer(reservaActual.getAlojamiento().getFotos(),";");
        String urlPrimeraFoto = st.nextToken();
        Picasso.with(context).load(urlPrimeraFoto).into(imagen);
        return nuevaVista;
    }

}
