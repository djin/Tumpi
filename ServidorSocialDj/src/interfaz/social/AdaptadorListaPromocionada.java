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
public class AdaptadorListaPromocionada extends BaseAdapter{
    
    Context mContext;
    private ArrayList<Cancion> datos;
    int estiloFila;
    
    public AdaptadorListaPromocionada(Context c, ArrayList<Cancion> d, int v) {
        mContext = c;
        datos = d;
        estiloFila = v;
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
    
    public void limpiarDatos() {
        int n = datos.size();
        for (int i = 0; i < n; i++) {
            datos.remove(0);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        if(rootView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(estiloFila, parent, false);
        }
        TextView txtNombreCancionPromocionada = (TextView)rootView.findViewById(R.id.textNombreCancionPromocionada);
        txtNombreCancionPromocionada.setText(datos.get(position).getNombreCancion());
        TextView txtNombreArtistaPromocionada = (TextView)rootView.findViewById(R.id.textNombreArtistaPromocionada);
        txtNombreArtistaPromocionada.setText(datos.get(position).getNombreAutor());
        TextView txtNombreAlbumPromocionada = (TextView)rootView.findViewById(R.id.textNombreAlbumPromocionada);
        txtNombreAlbumPromocionada.setText(datos.get(position).getNombreAlbum());
        if(rootView.isSelected()){
            rootView.setBackgroundColor(Color.parseColor("#ffFF0000"));
        }
        else {
            rootView.setBackgroundColor(Color.parseColor("#00000000"));
        }
        return rootView;
    }

}
