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
    ArrayList<String> seleccionados;
    int estiloFila;

    public AdaptadorListaSeleccionar(Context c, ArrayList<Cancion> d, int v) {
        mContext = c;
        datos = d;
        estiloFila = v;
        seleccionados = new ArrayList<String>();
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
    
    public void cambiarDatos (ArrayList<Cancion> nuevosDatos){
        datos = nuevosDatos;
        for (Cancion c : datos){
            
        }
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
        int i = seleccionados.size();
        for (int n = 0; n< i; n++) {
            seleccionados.remove(0);
        }
        notifyDataSetChanged();
    }
    
    public boolean isExist(Cancion cancion){
        boolean seleccionado = false;
        for (String texto : seleccionados){
            if(texto.equals(cancion.path)){
                seleccionado = true;
                return seleccionado;
            }
        }
        return seleccionado;
    }

    public void seleccionarTodo() {
        for (Cancion c : datos) {
            seleccionados.add(c.path);
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
        txtNombreCancionSeleccionada.setText(datos.get(position).nombreCancion);
        TextView txtNombreArtistaSeleccionada = (TextView) rootView.findViewById(R.id.textNombreArtistaSeleccion);
        txtNombreArtistaSeleccionada.setText(datos.get(position).nombreAutor);
        TextView txtNombreAlbumSeleccionada = (TextView) rootView.findViewById(R.id.textNombreAlbumSeleccion);
        txtNombreAlbumSeleccionada.setText(datos.get(position).nombreAlbum);
        TextView txtDuracionSeleccionada = (TextView) rootView.findViewById(R.id.txtDuracionSeleccion);
        txtDuracionSeleccionada.setText(datos.get(position).getLengthString());
        if (!isExist(datos.get(position))) {
            rootView.setBackgroundResource(R.drawable.background_row_seleccionar);
        } else {
            rootView.setBackgroundColor(Color.parseColor("#55fbb74b"));
        }
        return rootView;
    }
}
