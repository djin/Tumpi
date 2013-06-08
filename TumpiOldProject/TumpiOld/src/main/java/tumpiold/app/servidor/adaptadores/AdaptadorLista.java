package tumpiold.app.servidor.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tumpiold.app.R;
import tumpiold.app.servidor.modelos.Cancion;
import tumpiold.app.servidor.modelos.ListasManager;

/**
 * Created by 66785320 on 3/06/13.
 */
public class AdaptadorLista extends BaseAdapter {
    private ArrayList<Cancion> canciones;
    private Context mContext;

    public AdaptadorLista(ArrayList<Cancion> canciones, Context mContext){
        this.canciones = canciones;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return canciones.size();
    }

    @Override
    public Object getItem(int i) {
        return canciones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void cambiarDatos (ArrayList<Cancion> nuevasCanciones){
        canciones = nuevasCanciones;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_style_preparation, viewGroup);
        }
        TextView txtNombreCancion = (TextView) v.findViewById(R.id.textNombreCancion);
        txtNombreCancion.setText(canciones.get(i).nombreCancion);
        TextView txtNombreALbum = (TextView) v.findViewById(R.id.textNombreAlbum);
        txtNombreALbum.setText(canciones.get(i).nombreAlbum);
        TextView txtNombreArtista = (TextView) v.findViewById(R.id.textNombreArtista);
        txtNombreArtista.setText(canciones.get(i).nombreAutor);
        TextView txtDuracion = (TextView) v.findViewById(R.id.txtDuracion);
        txtDuracion.setText(canciones.get(i).getLengthString());
        txtNombreCancion.setSelected(true);
        /*if (estilo) {
            txtNombreCancion.setTextSize(15);
            txtNombreCancion.setTextColor(Color.parseColor("#ff8f5d0d"));
            txtNombreCancion.setTypeface(txtNombreCancion.getTypeface(), Typeface.BOLD);
            txtNombreArtista.setTextSize(13);
            txtNombreArtista.setTextColor(Color.parseColor("#ff8f5d0d"));
            txtDuracion.setText("");
        }*/
        return v;
    }
}
