/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import modelo.datos.Cancion;

public class AdaptadorLista extends BaseAdapter {

    Context mContext;
    private ArrayList<Cancion> datos;
    int estiloFila;
    Boolean estilo = false;

    public AdaptadorLista(Context c, ArrayList<Cancion> d, int v) {
        mContext = c;
        datos = d;
        estiloFila = v;
    }

    public int getCount() {
        return getDatos().size();
    }

    public Object getItem(int position) {
        return getDatos().get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void setDatos(ArrayList<Cancion> datos_aux) {
        datos = datos_aux;
    }

    public ArrayList<Cancion> getDatos() {
        return datos;
    }

    public void limpiarDatos() {
        int n = datos.size();
        for (int i = 0; i < n; i++) {
            datos.remove(0);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(estiloFila, parent, false);
        }
        TextView txtNombreCancion = (TextView) v.findViewById(R.id.textNombreCancion);
        txtNombreCancion.setText(getDatos().get(position).getNombreCancion());
        TextView txtNombreALbum = (TextView) v.findViewById(R.id.textNombreAlbum);
        txtNombreALbum.setText(getDatos().get(position).getNombreAlbum());
        TextView txtNombreArtista = (TextView) v.findViewById(R.id.textNombreArtista);
        txtNombreArtista.setText(getDatos().get(position).getNombreAutor());

        if (estilo) {
            txtNombreCancion.setTextSize(15);
            txtNombreCancion.setTextColor(Color.parseColor("#445e4e"));
            txtNombreCancion.setTypeface(txtNombreCancion.getTypeface(), Typeface.BOLD);
            txtNombreArtista.setTextSize(13);
            txtNombreArtista.setTextColor(Color.parseColor("#445e4e"));
        }
        return v;
    }

    public void cambioEstilo(boolean bol) {
        estilo = bol;
    }
}
