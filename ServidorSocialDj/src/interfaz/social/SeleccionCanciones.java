/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import modelo.datos.Cancion;
import modelo.datos.ModeloDatos;

/**
 *
 * @author zellyalgo
 */
public class SeleccionCanciones extends ListActivity {

    private AdaptadorListaSeleccionar adapter;
    private ArrayList<Cancion> datos;
    private ModeloDatos modelo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.style_seleccionar_canciones);
        modelo = ModeloDatos.getInstance();
        datos = new ArrayList<Cancion>();
        for (int i = 0; i < 10; i++) {
            datos.add(new Cancion("Los Redondeles añadido", "Siempre Fuertes 2", "HUAEx34", 0, 24567, false, false));
        }
        adapter = new AdaptadorListaSeleccionar(this, datos, R.layout.row_style_seleccion);
        for(Cancion c : datos){
            adapter.seleccionados.add(false);
        }
        setListAdapter(adapter);
        ListView lista = (ListView) findViewById(android.R.id.list);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (adapter.seleccionados.get(position)) {
            adapter.seleccionados.set(position, false);
        } else {
            adapter.seleccionados.set(position, true);
        }
        adapter.notifyDataSetChanged();
    }
}
