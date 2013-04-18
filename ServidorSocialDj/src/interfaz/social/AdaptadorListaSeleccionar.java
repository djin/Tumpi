/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import modelo.datos.Cancion;

/**
 *
 * @author Zellyalgo
 */
public class AdaptadorListaSeleccionar extends BaseAdapter {

    Context mContext;
    private ArrayList<Cancion> datos;
    ArrayList<Boolean> seleccionados;
    int estiloFila;

    public AdaptadorListaSeleccionar(Context c, ArrayList<Cancion> d, int v) {
        mContext = c;
        datos = d;
        estiloFila = v;
        seleccionados = new ArrayList<Boolean>();
    }

    public int getCount() {
        return datos.size();
    }

    public Object getItem(int position) {
        return datos.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void anadirCancion(Cancion c) {
        datos.add(c);
    }

    public void limpiarDatos() {
        int n = datos.size();
        for (int i = 0; i < n; i++) {
            datos.remove(0);
        }
    }

    public void cancelarSeleccion() {
        int i = 0;
        for (Boolean b : seleccionados) {
            seleccionados.set(i, false);
            i++;
        }
        notifyDataSetChanged();
    }

    public void seleccionarTodo() {
        int i = 0;
        for (Boolean b : seleccionados) {
            seleccionados.set(i, true);
            i++;
        }
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        if (rootView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(estiloFila, parent, false);
        }
        TextView txtNombreCancionSeleccionada = (TextView) rootView.findViewById(R.id.textNombreCancionSeleccion);
        txtNombreCancionSeleccionada.setText(datos.get(position).getNombreCancion());
        TextView txtNombreArtistaSeleccionada = (TextView) rootView.findViewById(R.id.textNombreArtistaSeleccion);
        txtNombreArtistaSeleccionada.setText(datos.get(position).getNombreAutor());
        TextView txtNombreAlbumSeleccionada = (TextView) rootView.findViewById(R.id.textNombreAlbumSeleccion);
        txtNombreAlbumSeleccionada.setText(datos.get(position).getNombreAlbum());
        TextView txtDuracionSeleccionada = (TextView) rootView.findViewById(R.id.txtDuracionSeleccion);
        txtDuracionSeleccionada.setText(datos.get(position).getLengthString());
        if (!seleccionados.get(position)) {
            rootView.setBackgroundColor(Color.parseColor("#00000000"));
        } else {
            rootView.setBackgroundColor(Color.parseColor("#55fbb74b"));
        }
        return rootView;
    }
}
