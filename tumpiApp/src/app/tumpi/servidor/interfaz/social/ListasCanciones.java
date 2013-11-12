package app.tumpi.servidor.interfaz.social;

import app.tumpi.servidor.Manejador.ManejadorAcciones;
import android.app.ActionBar;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.tumpi.R;
import app.tumpi.servidor.modelo.datos.ListasManager;
import app.tumpi.servidor.multimedia.AudioExplorer;
import app.tumpi.servidor.multimedia.PlayerListener;

public class ListasCanciones extends FragmentActivity implements PlayerListener {

	ActionBar.TabListener tabListener;
	// When requested, this adapter returns a DemoObjectFragment,
	// representing an object in the collection.
	SwipeViewPagerAdapter mSwipeViewPagerAdapter;
	ViewPager mViewPager;
	private Menu menuApp;
	private ManejadorAcciones manejador;
	private ListasManager modelo = ListasManager.getInstance();
	private AudioExplorer explorer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		manejador = ManejadorAcciones.getInstance();
		manejador.setListaCanciones(this);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Tus listas");
		// ViewPager and its adapters use support library
		// fragments, so use getSupportFragmentManager.
		mSwipeViewPagerAdapter = new SwipeViewPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSwipeViewPagerAdapter);
		Bundle inte = getIntent().getExtras();
		if (inte != null) {
			int numList = inte.getInt("numList");
			boolean pulsado = inte.getBoolean("crearLista");
			if (numList != 0) {
				mViewPager.setCurrentItem(numList);
			}
			if (pulsado) {
				mSwipeViewPagerAdapter.crearLista(mViewPager);
			}
		}
		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					public void onPageScrolled(int i, float f, int i1) {
						cancelarSeleccion();
					}

					public void onPageSelected(int i) {
						cancelarSeleccion();
					}

					public void onPageScrollStateChanged(int i) {
						cancelarSeleccion();
					}
				});
		explorer = AudioExplorer.getInstance(getApplicationContext());
		modelo.player.addPlayerListener(this);
		updatePlayer();
	}

	public void irPromocionada() {
		Intent inte = new Intent(ListasCanciones.this, ListaPromocionada.class);
		inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(inte);
	}

	public void cancelarSeleccion() {
		manejador.cancelarSeleccion();
		desapareceMenuSeleccion();
	}

	public void borrarCanciones() {
		manejador.borrarCanciones();
		desapareceMenuSeleccion();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menuApp = menu;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menulistas, menu);
		menu.getItem(0).getSubMenu().clear();
		menu.add(13, 58, 6, "Promocionar");
		menu.add(12, 57, 5, "Añadir Canciones");
		desapareceMenuSeleccion();
		return true;
	}

	public void anadirCancion(View v) {
		Intent inte = new Intent(ListasCanciones.this, SeleccionCanciones.class);
		inte.putExtra("numList", mViewPager.getCurrentItem());
		startActivity(inte);
	}

	public void desapareceMenuSeleccion() {
		menuApp.getItem(0).setVisible(true);
		menuApp.getItem(1).setVisible(true);
		menuApp.getItem(2).setVisible(false);
		menuApp.getItem(3).setVisible(false);
	}

	public void apareceMenuSeleccion() {
		menuApp.getItem(0).setVisible(false);
		menuApp.getItem(1).setVisible(false);
		menuApp.getItem(2).setVisible(true);
		menuApp.getItem(3).setVisible(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case 57:
			anadirCanciones();
			break;
		case R.id.itemCrearLista:
			mSwipeViewPagerAdapter.crearLista(mViewPager);
			break;
		case R.id.itemBorrar:
			borrarCanciones();
			Toast.makeText(this, "Canciones Borradas", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.itemCancelar:
			cancelarSeleccion();
			break;
		case R.id.itemBuscarLista:
			buscarLista(item);
			break;
		case R.id.itemBorrarLista:
			borrarListaSeleccionada();
			break;
		case 58:
			promocionarListaSeleccionada();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void promocionarListaSeleccionada() {
		if (mSwipeViewPagerAdapter.getNombresListas().isEmpty()) {
			Toast.makeText(this, "No tienes listas creadas", Toast.LENGTH_SHORT)
					.show();
		} else {
			modelo.promocionar(mViewPager.getCurrentItem());
			irPromocionada();
		}
	}

	private void borrarListaSeleccionada() {
		if (mSwipeViewPagerAdapter.getNombresListas().isEmpty()) {
			Toast.makeText(this, "No tienes listas creadas", Toast.LENGTH_SHORT)
					.show();
		} else {
			int pestanaBorrar = mViewPager.getCurrentItem();
			mViewPager.setCurrentItem(pestanaBorrar - 1, true);
			mSwipeViewPagerAdapter.borrarLista(mViewPager, pestanaBorrar);
			mViewPager.setOffscreenPageLimit(mSwipeViewPagerAdapter.getCount());
			mSwipeViewPagerAdapter.notifyDataSetChanged();
		}
		Toast.makeText(this, "Lista Borrada", Toast.LENGTH_SHORT).show();
	}

	private void anadirCanciones() {
		if (mSwipeViewPagerAdapter.getNombresListas().isEmpty()) {
			Toast.makeText(this, "No tienes listas creadas", Toast.LENGTH_SHORT)
					.show();
		} else {
			Intent inte = new Intent(ListasCanciones.this,
					SeleccionCanciones.class);
			inte.putExtra("numList", mViewPager.getCurrentItem());
			startActivity(inte);
		}
	}

	private void buscarLista(MenuItem item) {
		SubMenu sMenu = item.getSubMenu();
		sMenu.clear();
		if (mSwipeViewPagerAdapter.getNombresListas().isEmpty()) {
			Toast.makeText(this, "No tienes listas creadas", Toast.LENGTH_SHORT)
					.show();
		} else {
			ArrayList<String> listas = mSwipeViewPagerAdapter
					.getNombresListas();
			int groupId = 1, id = 1, orden = 0;
			for (String nombre : listas) {
				sMenu.add(groupId, id, orden, nombre);
				MenuItem sItem = sMenu.getItem(orden);
				sItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						mViewPager.setCurrentItem(item.getOrder(), true);
						return true;
					}
				});
				groupId++;
				id++;
				orden++;
			}
		}
	}

	public void updatePlayer() {
		if (modelo.getCancionReproduciendo() != null) {
			TextView txtNombreCancionReproduciendo = (TextView) findViewById(R.id.txtNombreCancionReproduciendo);
			txtNombreCancionReproduciendo.setText(modelo
					.getCancionReproduciendo().nombreCancion);
			TextView txtNombreAlbumReproduciendo = (TextView) findViewById(R.id.txtNombreAlbumReproduciendo);
			txtNombreAlbumReproduciendo.setText(modelo
					.getCancionReproduciendo().nombreAutor);
			ImageView imagen = (ImageView) findViewById(R.id.caratulaDisco);
			imagen.setImageBitmap(explorer.getAlbumImage(modelo
					.getCancionReproduciendo().album_id));
			ImageButton boton = (ImageButton) findViewById(R.id.btnPlay);
			txtNombreCancionReproduciendo.setSelected(true);
			txtNombreAlbumReproduciendo.setSelectAllOnFocus(true);
			if (modelo.player.isPlaying()) {
				boton.setImageResource(R.drawable.image_pause);
			} else {
				boton.setImageResource(R.drawable.image_play);
			}
		}
	}

	public void clickPlay(View v) {
		try {
			modelo.player.pause();
		} catch (Exception ex) {
			clickNext(null);
		}
		ImageButton boton = (ImageButton) v;
		if (modelo.player.isPlaying()) {
			boton.setImageResource(R.drawable.image_pause);
		} else {
			boton.setImageResource(R.drawable.image_play);
		}
	}

	public void clickNext(View v) {
		modelo.procesarVotos();
		findViewById(R.id.btnPlay).post(new Runnable() {
			public void run() {
				updatePlayer();
			}
		});
	}

	public void onPrepared(MediaPlayer mp) {
		ImageButton boton = (ImageButton) findViewById(R.id.btnPlay);
		boton.setImageResource(R.drawable.image_pause);
	}

	public void onCompletion(MediaPlayer mp) {
		clickNext(null);
	}

	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		Log.i("Multimedia", "Informacion de parte del reproductor");
		return false;
	}

	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.e("Multimedia", "Error al reproducir cancion");
		return false;
	}

	@Override
	public void onBackPressed() {
		irPromocionada();
	}
}
