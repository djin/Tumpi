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

/**
 *
 * @author 66785320
 */
public class AdaptadorLista extends BaseAdapter {

    Context mContext;
    ArrayList<Cancion> datos;
    int estiloFila;

    public AdaptadorLista(Context c, ArrayList<Cancion> d, int v) {
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

    public View getView(int position, View convertView, ViewGroup parent) {
//        View v = convertView;
//        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.rowstyle, parent, false);
            TextView txt = (TextView) v.findViewById(R.id.songName);
            txt.setText(datos.get(position).getNombreCancion());
            ImageButton btn = (ImageButton) v.findViewById(R.id.btnVotar);
            Boolean flag = datos.get(position).getVotado();
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

    private class ClickListener implements View.OnClickListener {

        private int position;

        public ClickListener(int position) {
            this.position = position;
        }

        public void onClick(View v) {
            ImageButton votado = (ImageButton) v.findViewById(R.id.btnVotar);
            votado.setImageResource(R.raw.ico_small_star_focus);
            votado.setEnabled(false);
            datos.get(position).setVotado(true);
        }
    }
}