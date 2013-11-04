/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tumpi.cliente.lista.android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import app.tumpi.R;
import java.util.ArrayList;
import app.tumpi.cliente.lista.android.conexion.ConnectionManager;

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
            v = inflater.inflate(R.layout.rowstyle, parent, false);
        }
        TextView txt = (TextView) v.findViewById(R.id.songName);

        txt.setText(getDatos().get(position).getNombreCancion());
        txt.setSelected(true);
        TextView txtAutor = (TextView) v.findViewById(R.id.autorName);
        txtAutor.setText(getDatos().get(position).getNombreAutor());
        ImageButton btn = (ImageButton) v.findViewById(R.id.btnVotar);
        btn.setOnClickListener(new ClickListener(position));

        if (getDatos().get(position).getSonado()) {
            txt.setTextColor(mContext.getResources().getColor(R.color.colorTextSonadoCliente));
            txtAutor.setTextColor(mContext.getResources().getColor(R.color.colorTextSonadoCliente));
            btn.setImageDrawable(null);
            btn.setEnabled(false);
        } else {
            txt.setTextColor(mContext.getResources().getColor(R.color.colorLetraRowCancionesCliente));
            txtAutor.setTextColor(mContext.getResources().getColor(R.color.colorLetraRowCancionesClienteSecundaria));
            if (getDatos().get(position).getVotado()) {
                btn.setImageResource(R.raw.ico_small_star_focus);
//                btn.setBackgroundColor(Color.BLACK);
            } else {
                btn.setImageResource(R.raw.estrella_nada_llena);
//                btn.setBackgroundColor(Color.BLACK);
            }
            btn.setEnabled(true);
        }
        return v;
    }

    private class ClickListener implements View.OnClickListener {

        private int position;

        public ClickListener(int position) {
            this.position = position;
        }

        public void onClick(View v) {
            if (getDatos().get(position).getVotado()) {
                try {
                    if(!getDatos().get(position).votando){
                        conex.conexion.enviarMensaje("3|" + Integer.toString(getDatos().get(position).getId()));
                        ImageButton btn = (ImageButton)v;
                        btn.setImageBitmap(null);
                        btn.setBackgroundResource(R.drawable.gif_desvotando);
                        AnimationDrawable frameAnimation = (AnimationDrawable) btn.getBackground();
                        frameAnimation.start();
                        getDatos().get(position).votando=true;
                    }
                } catch (Exception ex) {
                }
            } else {
                try {
                    if(!getDatos().get(position).votando){
                        conex.conexion.enviarMensaje("1|" + Integer.toString(getDatos().get(position).getId()));
                        ImageButton btn = (ImageButton)v;
                        btn.setImageBitmap(null);
                        btn.setBackgroundResource(R.drawable.gif_votando);
                        AnimationDrawable frameAnimation = (AnimationDrawable) btn.getBackground();
                        frameAnimation.start();
                        getDatos().get(position).votando=true;
                    }
                } catch (Exception ex) {
                }
            }
        }
    }
}