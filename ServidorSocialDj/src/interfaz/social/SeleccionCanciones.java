/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import modelo.datos.Cancion;
import modelo.datos.ModeloDatos;
import multimedia.AudioExplorer;

/**
 *
 * @author zellyalgo
 */
public class SeleccionCanciones extends ListActivity {

    private AdaptadorListaSeleccionar adapter;
    private ArrayList<Cancion> datos;
    private ModeloDatos modelo;
    private int numList;
    private AudioExplorer explorer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.style_seleccionar_canciones);
        Bundle inte = getIntent().getExtras();
        numList = inte.getInt("numList");
        final ActionBar actionBar = getActionBar();
        actionBar.setTitle("Seleccionar");
        modelo = ModeloDatos.getInstance();
        datos = new ArrayList<Cancion>();
        explorer=AudioExplorer.getInstance(this.getApplicationContext());
        ArrayList<HashMap> canciones=explorer.searchAudio();
        for (HashMap cancion:canciones) {
            datos.add(new Cancion((String)cancion.get("name"),(String)cancion.get("artist"),(String)cancion.get("album"), 0,Integer.parseInt((String)cancion.get("length"))));
        }
        adapter = new AdaptadorListaSeleccionar(this, datos, R.layout.row_style_seleccion);
        for (Cancion c : datos) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu_seleccionar, menu);
        return true;
    }

    public ArrayList<Cancion> getCancionSeleccionadas() {
        ArrayList<Cancion> canciones = new ArrayList<Cancion>();
        int buscarBorrados = 0;
        for (Boolean b : adapter.seleccionados) {
            if (b) {
                canciones.add(datos.get(buscarBorrados));
            }
            buscarBorrados++;
        }
        return canciones;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAceptarSeleccion:
                modelo.anadirCanciones(numList, getCancionSeleccionadas());
                Intent inte = new Intent(this, ListasCanciones.class);
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(inte);
                return true;
            case R.id.itemCancelarSeleccion:
                adapter.cancelarSeleccion();
                return true;
            case R.id.itemSeleccionarTodo:
                adapter.seleccionarTodo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
