/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.tumpi.servidor.interfaz.social;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import app.tumpi.R;
import app.tumpi.servidor.modelo.datos.CancionPromocionada;

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
        TextView txtVotos = (TextView)rootView.findViewById(R.id.txtNumeroVotos);
        if(datos.get(position).getVotos() == -1){
            txtVotos.setText("*");
        } else {
            txtVotos.setText(datos.get(position).getVotos()+"");
        }
        txtNombreCancionPromocionada.setSelected(true);
        if(!seleccionados.get(position)){
            rootView.setBackgroundColor(mContext.getResources().getColor(R.color.colorFondoRowSinSeleccionar));
        } else {
            rootView.setBackgroundColor(mContext.getResources().getColor(R.color.colorFondoRowSeleccionada));
        }
        return rootView;
    }

}
