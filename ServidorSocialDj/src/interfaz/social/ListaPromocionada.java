/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
    private AdaptadorListaPromocionada adapter;
    private Boolean modoSeleccion = false;
    private Menu menuApp;

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
                R.id.textSpinner, new String[]{"Lista Promocionada", "Listas Pendientes"}),
                // Provide a listener to be called when an item is selected.
                new ActionBar.OnNavigationListener() {
            public boolean onNavigationItemSelected(int position, long id) {
                // Take action here, e.g. switching to the
                // corresponding fragment.
                if (position == 1) {
                    actionBar.setSelectedNavigationItem(0);
                    Intent inte = new Intent(ListaPromocionada.this, ListasCanciones.class);
                    inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(inte);
                }
                return true;
            }
        });
        actionBar.setDisplayShowTitleEnabled(false);

        datosListaPromocionada = modelo.listaPromocionada;
        adapter = new AdaptadorListaPromocionada(this, datosListaPromocionada, R.layout.row_style_promocionada);
        for (Cancion c : datosListaPromocionada) {
            adapter.seleccionados.add(false);
        }
        setListAdapter(adapter);
        ListView lista = (ListView) findViewById(android.R.id.list);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.seleccionados.set(position, true);
                adapter.notifyDataSetChanged();
                modoSeleccion = true;
                apareceMenu();
                return true;
            }
        });
        if (modelo.getCancionReproduciendo() != null) {
            TextView txtNombreCancionReproduciendo = (TextView) findViewById(R.id.txtNombreCancionReproduciendo);
            txtNombreCancionReproduciendo.setText(modelo.getCancionReproduciendo().getNombreCancion());
            TextView txtNombreAlbumReproduciendo = (TextView) findViewById(R.id.txtNombreAlbumReproduciendo);
            txtNombreAlbumReproduciendo.setText(modelo.getCancionReproduciendo().getNombreAlbum());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuApp = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu_promocionada, menu);
        desapareceMenu();
        return true;
    }

    public void desapareceMenu() {
        menuApp.getItem(0).setVisible(false);
        menuApp.getItem(1).setVisible(false);
    }

    public void apareceMenu() {
        menuApp.getItem(0).setVisible(true);
        menuApp.getItem(1).setVisible(true);
    }

    public void borrarCanciones() {
        ArrayList<Cancion> nuevaLista = new ArrayList<Cancion>();
        int buscarBorrados = 0;
        for (Boolean b : adapter.seleccionados) {
            if (!b) {
                nuevaLista.add(datosListaPromocionada.get(buscarBorrados));
            }
            buscarBorrados++;
        }
        adapter.limpiarDatos();
        datosListaPromocionada = nuevaLista;

        ArrayList<Boolean> seleccion = new ArrayList<Boolean>();
        for (Cancion c : datosListaPromocionada) {
            adapter.anadirCancion(c);
            seleccion.add(false);
        }
        adapter.seleccionados = seleccion;
        adapter.notifyDataSetChanged();
        desapareceMenu();
        modoSeleccion = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemBorrarPromocionada:
                borrarCanciones();
                Toast.makeText(this, "Canciones Borradas", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemCancelarPromocionada:
                int i = 0;
                for (Boolean b : adapter.seleccionados) {
                    b = false;
                    adapter.seleccionados.set(i, b);
                    i++;
                }
                adapter.notifyDataSetChanged();
                modoSeleccion = false;
                desapareceMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (modoSeleccion) {
            if (adapter.seleccionados.get(position)) {
                adapter.seleccionados.set(position, false);
            } else {
                adapter.seleccionados.set(position, true);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
