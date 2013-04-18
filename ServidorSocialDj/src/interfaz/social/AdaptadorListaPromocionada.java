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
import modelo.datos.CancionPromocionada;

/**
 * 
 * @author Zellyalgo
 */
public class AdaptadorListaPromocionada extends BaseAdapter{
    
    Context mContext;
    private ArrayList<CancionPromocionada> datos;
    ArrayList<Boolean> seleccionados;
    int estiloFila;
    
    public AdaptadorListaPromocionada(Context c, ArrayList<CancionPromocionada> d, int v) {
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
    
    public void anadirCancion(CancionPromocionada c){
        datos.add(c);
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
        txtNombreCancionPromocionada.setText(datos.get(position).nombreCancion);
        TextView txtNombreArtistaPromocionada = (TextView)rootView.findViewById(R.id.textNombreArtistaPromocionada);
        txtNombreArtistaPromocionada.setText(datos.get(position).nombreAutor);
        TextView txtNombreAlbumPromocionada = (TextView)rootView.findViewById(R.id.textNombreAlbumPromocionada);
        txtNombreAlbumPromocionada.setText(datos.get(position).nombreAlbum);
        if(!seleccionados.get(position)){
            rootView.setBackgroundColor(Color.parseColor("#00000000"));
        } else {
            rootView.setBackgroundColor(Color.parseColor("#55fbb74b"));
        }
        return rootView;
    }

}
