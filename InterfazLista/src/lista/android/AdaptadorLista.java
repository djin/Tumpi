/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lista.android.conexion.ConnectionManager;

/**
 *
 * @author 66785320
 */
public class AdaptadorLista extends BaseAdapter {

    Context mContext;
    private ArrayList<Cancion> datos;
    int estiloFila;
    ConnectionManager conex;

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
    public void setDatos(ArrayList<Cancion> datos_aux){
        datos=datos_aux;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
//        View v = convertView;
//        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.rowstyle, parent, false);
            TextView txt = (TextView) v.findViewById(R.id.songName);
            txt.setText(getDatos().get(position).getNombreCancion());
            ImageButton btn = (ImageButton) v.findViewById(R.id.btnVotar);
            Boolean flag = getDatos().get(position).getVotado();
            if (flag) {
                btn.setImageResource(R.raw.ico_small_star_focus);
                btn.setEnabled(false);
            } else {
                btn.setImageResource(R.raw.ico_medium_star);
                btn.setOnClickListener(new ClickListener(position));
            }
      //  }
        return v;
    }

    /**
     * @return the datos
     */
    public ArrayList<Cancion> getDatos() {
        return datos;
    }

    private class ClickListener implements View.OnClickListener {

        private int position;

        public ClickListener(int position) {
            this.position = position;
        }

        public void onClick(View v) {
            try {
                conex.conexion.enviarMensaje(Integer.toString(getDatos().get(position).getId()));
            } catch (Exception ex) {
                
            }
//            ImageButton votado = (ImageButton) v.findViewById(R.id.btnVotar);
//            votado.setImageResource(R.raw.ico_small_star_focus);
//            votado.setEnabled(false);
//            getDatos().get(position).setVotado(true);
        }
    }
}