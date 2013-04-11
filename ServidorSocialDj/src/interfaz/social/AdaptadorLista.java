/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import android.content.Context;
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
        TextView txt = (TextView) v.findViewById(R.id.textRow);

        txt.setText(getDatos().get(position).getNombreCancion());
        txt.setSelected(true);
        return v;
    }
    
    public void cambioEstilo(boolean bol){
        estilo = bol;
    }
}
