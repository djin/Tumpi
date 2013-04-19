/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import modelo.datos.Cancion;
import modelo.datos.ListasManager;
import multimedia.AudioExplorer;

/**
 *
 * @author zellyalgo
 */
public class SeleccionCanciones extends ListActivity {

    private AdaptadorListaSeleccionar adapter;
    private ArrayList<Cancion> datos;
    private ListasManager manager;
    private int numList;
    private Menu menuApp;
    private AudioExplorer explorer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.style_seleccionar_canciones);
        Bundle inte = getIntent().getExtras();
        numList = inte.getInt("numList");
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        manager = ListasManager.getInstance();
        datos = new ArrayList<Cancion>();
        explorer = AudioExplorer.getInstance(this.getApplicationContext());
        ArrayList<HashMap> canciones = explorer.searchAudio();
        for (HashMap cancion : canciones) {
            datos.add(new Cancion(0, (String) cancion.get("name"), (String) cancion.get("album"), (Integer) cancion.get("album_id"),
                    (String) cancion.get("artist"), Integer.parseInt((String) cancion.get("length")), (String) cancion.get("path")));
        }
        adapter = new AdaptadorListaSeleccionar(this, datos, R.layout.row_style_seleccion);
        setListAdapter(adapter);
        ListView lista = (ListView) findViewById(android.R.id.list);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cancion cancionSeleccionada = (Cancion) adapter.getItem(position);
        if (adapter.isExist(cancionSeleccionada)) {
            adapter.seleccionados.remove(cancionSeleccionada.path);
        } else {
            adapter.seleccionados.add(cancionSeleccionada.path);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menuApp = menu;
        inflater.inflate(R.layout.menu_seleccionar, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.itemBuscarSeleccion).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            public boolean onQueryTextChange(String newText) {
                buscarQuery(newText);
                return true;
            }
        });
        return true;
    }
    
    public void buscarQuery(String query) {
        ArrayList<Cancion> cancionesEncontradas = new ArrayList<Cancion>();
        query = query.toUpperCase();
        for (Cancion c : datos) {
            if (c.nombreCancion.toUpperCase().contains(query) || c.nombreAutor.toUpperCase().contains(query) || c.nombreAlbum.toUpperCase().contains(query)) {
                cancionesEncontradas.add(c);
            }
        }
        adapter.cambiarDatos(cancionesEncontradas);
        adapter.notifyDataSetChanged();
    }

    public ArrayList<Cancion> getCancionSeleccionadas() {
        ArrayList<Cancion> canciones = new ArrayList<Cancion>();
        for (Cancion c : datos) {
            if (adapter.isExist(c)) {
                canciones.add(c);
            }
        }
        return canciones;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAceptarSeleccion:
                manager.anadirCanciones(numList, getCancionSeleccionadas());
                Intent inte = new Intent(this, ListasCanciones.class);
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                inte.putExtra("numList", numList);
                startActivity(inte);
                return true;
            case R.id.itemCancelarSeleccion:
                adapter.cancelarSeleccion();
                return true;
            case R.id.itemSeleccionarTodo:
                adapter.seleccionarTodo();
                return true;
            case R.id.itemBuscarSeleccion:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
