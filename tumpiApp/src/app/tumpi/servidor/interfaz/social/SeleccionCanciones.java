/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tumpi.servidor.interfaz.social;

import android.R.drawable;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import app.tumpi.R;
import app.tumpi.servidor.modelo.datos.Cancion;
import app.tumpi.servidor.modelo.datos.ListasManager;
import app.tumpi.servidor.multimedia.AudioExplorer;

/**
 * 
 * @author zellyalgo
 */
public class SeleccionCanciones extends ActionBarActivity {

	private AdaptadorListaSeleccionar adapter;
	private ArrayList<Cancion> datos;
	private ListasManager manager;
	private int numList;
	private ListView lista;
	private Menu menuApp;
	private AudioExplorer explorer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.style_seleccionar_canciones);
		Bundle inte = getIntent().getExtras();
		numList = inte.getInt("numList");
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		manager = ListasManager.getInstance();
		datos = new ArrayList<Cancion>();
		explorer = AudioExplorer.getInstance(this.getApplicationContext());
		ArrayList<HashMap> canciones = explorer.searchAudio();
		for (HashMap cancion : canciones) {
			datos.add(new Cancion(0, parsearDato((String) cancion.get("name")),
					parsearDato((String) cancion.get("album")),
					(Integer) cancion.get("album_id"),
					parsearDato((String) cancion.get("artist")), Integer
							.parseInt((String) cancion.get("length")),
					(String) cancion.get("path")));
		}
		lista = (ListView) findViewById(android.R.id.list);
		adapter = new AdaptadorListaSeleccionar(this, datos,
				R.layout.row_style_seleccion);
		lista.setAdapter(adapter);
		
		lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				Cancion cancionSeleccionada = (Cancion) adapter
						.getItem(position);
				if (adapter.isExist(cancionSeleccionada)) {
					adapter.seleccionados.remove(cancionSeleccionada.path);
				} else {
					adapter.seleccionados.add(cancionSeleccionada.path);
				}
				adapter.notifyDataSetChanged();
			}

		});
		View v = findViewById(android.R.id.empty);
		lista.setEmptyView(v);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu_seleccionar, menu);
		menuApp = menu;
		MenuItem item = menu.findItem(R.id.itemBuscarSeleccion);
		final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
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
			if (c.nombreCancion.toUpperCase().contains(query)
					|| c.nombreAutor.toUpperCase().contains(query)
					|| c.nombreAlbum.toUpperCase().contains(query)) {
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

	private String parsearDato(String dato) {
		if (dato != null) {
			String resultado = dato.replaceAll("[;:\\*\\|&]", " ");
			return resultado;
		}
		return null;
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
