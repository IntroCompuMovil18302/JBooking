package com.example.andre.jbookingmobile.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andre.jbookingmobile.Entities.Alojamiento;
import com.example.andre.jbookingmobile.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

public class AlojamientoAdaptador extends BaseAdapter {
    Context context;
    List<Alojamiento> alojamientos;

    public AlojamientoAdaptador(Context context, List<Alojamiento> alojamientos){
        this.context = context;
        this.alojamientos = alojamientos;
    }

    @Override
    public int getCount() {
        return alojamientos.size();
    }

    @Override
    public Object getItem(int position) {
        return alojamientos.get(position);
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

        Alojamiento alojamientoActual = alojamientos.get(position);
        lugar.setText(alojamientoActual.getUbicacion().getNombre());
        nombre.setText(alojamientoActual.getNombre());
        precio.setText("COP "+alojamientoActual.getValorNoche());

        StringTokenizer st = new StringTokenizer(alojamientoActual.getFotos(),";");
        String urlPrimeraFoto = st.nextToken();
        Picasso.with(context).load(urlPrimeraFoto).into(imagen);
        return nuevaVista;
    }
}
