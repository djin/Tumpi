/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import modelo.datos.Cancion;
import modelo.datos.ModeloDatos;

/**
 *
 * @author Zellyalgo
 */
public class ListaPromocionada extends ListActivity {
    
    private ArrayList<Cancion> datosListaPromocionada;
    private ModeloDatos modelo;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.style_lista_promocionada);
        final ActionBar actionBar = getActionBar();
        modelo = ModeloDatos.getInstance();
        // Specify that a dropdown list should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter(actionBar.getThemedContext(), R.layout.style_row_spinner,
                R.id.textSpinner, new String[]{"Listas Promocionada", "Listas Pendientes"}),
                // Provide a listener to be called when an item is selected.
                new ActionBar.OnNavigationListener() {
            public boolean onNavigationItemSelected(int position, long id) {
                // Take action here, e.g. switching to the
                // corresponding fragment.
                if(position ==1){
                    actionBar.setSelectedNavigationItem(0);
                    Intent inte = new Intent(ListaPromocionada.this, ListasCanciones.class);
                    startActivity(inte);
                }
                return true;
            }
        });
        actionBar.setDisplayShowTitleEnabled(false);
        
        datosListaPromocionada = modelo.listaPromocionada;
        datosListaPromocionada.add(new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
        datosListaPromocionada.add(new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
        datosListaPromocionada.add(new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
        datosListaPromocionada.add(new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
        datosListaPromocionada.add(new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
        datosListaPromocionada.add(new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
        datosListaPromocionada.add(new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
        datosListaPromocionada.add(new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
        datosListaPromocionada.add(new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
        datosListaPromocionada.add(new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
        AdaptadorListaPromocionada adapter = new AdaptadorListaPromocionada(this, datosListaPromocionada , R.layout.row_style_promocionada);
        setListAdapter(adapter);
        ListView lista = (ListView)findViewById(android.R.id.list);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        v.setSelected(true);
    }
    
}
