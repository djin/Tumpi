package app.tumpi.servidor.interfaz.social;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import app.tumpi.R;
import app.tumpi.SeleccionAplicacion;
import android.widget.TextView;
import android.widget.Toast;
import app.tumpi.servidor.interfaces.CambiarListaListener;

import java.util.ArrayList;

import app.tumpi.servidor.modelo.datos.Cancion;
import app.tumpi.servidor.modelo.datos.CancionPromocionada;
import app.tumpi.servidor.modelo.datos.ListasManager;
import app.tumpi.servidor.multimedia.AudioExplorer;
import app.tumpi.servidor.multimedia.PlayerListener;
import app.tumpi.util.Installation;

/**
 * 
 * @author Zellyalgo
 */
public class ListaPromocionada extends ActionBarActivity implements
		CambiarListaListener, PlayerListener {

	private ArrayList<CancionPromocionada> datosListaPromocionada;
	private ListasManager manager;
	private AdaptadorListaPromocionada adapter;
	private Boolean modoSeleccion = false;
	private Menu menuApp;
	private AudioExplorer explorer;
	private boolean showCreateListaDialog = false;
	private AlertDialog.Builder alertBuilder;
	private MenuItem conectarItem;
	private MenuItem logoutItem;
	private ListView lista;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.style_lista_promocionada);
		final ActionBar actionBar = getSupportActionBar();
		manager = ListasManager.getInstance();

		alertBuilder = new AlertDialog.Builder(this);

		actionBar.setDisplayHomeAsUpEnabled(true);

		manager.addModeloChangedListener(this);
		manager.player.addPlayerListener(this);
		explorer = AudioExplorer.getInstance(getApplicationContext());
		actionBar.setTitle("Promocionada");

		datosListaPromocionada = manager.promotedList.getCanciones();
		adapter = new AdaptadorListaPromocionada(this, datosListaPromocionada,
				R.layout.row_style_promocionada);
		for (Cancion c : datosListaPromocionada) {
			adapter.seleccionados.add(false);
		}
		lista = (ListView) findViewById(android.R.id.list);
		lista.setAdapter(adapter);// itemConectarServidor
		lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter.seleccionados.set(position, true);
				adapter.notifyDataSetChanged();
				modoSeleccion = true;
				ocultarMenuSeleccionCanciones();
				return true;
			}
		});

		lista.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				if (modoSeleccion) {
					if (adapter.seleccionados.get(position)) {
						adapter.seleccionados.set(position, false);
						if (!adapter.seleccionados.contains(true)) {
							modoSeleccion = false;
							mostrarMenuSeleccionCanciones();
						}
					} else {
						adapter.seleccionados.set(position, true);
					}
					adapter.notifyDataSetChanged();
				}
			}
		});
		View v = findViewById(android.R.id.empty);
		lista.setEmptyView(v);
		checkNoListsAvailable();
		updatePlayer();
	}

	private void checkNoListsAvailable() {
		if (datosListaPromocionada.isEmpty() && manager.noListsAvailable()) {
	//		TextView goToListasBtn = (TextView) findViewById(R.id.go_to_listas_btn);
	//		goToListasBtn.setText("¡Crea tus listas para empezar!");
			showCreateListaDialog = true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menuApp = menu;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu_promocionada, menu);
		conectarItem = menu.findItem(R.id.itemConectarServidor);
		logoutItem = menu.findItem(R.id.itemLogout);
		mostrarMenuSeleccionCanciones();
		if (manager.conectado) {
			conectarItem.setIcon(R.drawable.conectado);
			logoutItem.setVisible(true);
		} else {
			logoutItem.setVisible(false);
			conectarItem.setIcon(R.drawable.image_conectar);
		}
		return true;
	}

	public void mostrarMenuSeleccionCanciones() {
		menuApp.findItem(R.id.itemCancelarPromocionada).setVisible(false);
		menuApp.findItem(R.id.itemBorrarPromocionada).setVisible(false);
		menuApp.findItem(R.id.itemIrTusListas).setVisible(true);
		logoutItem.setVisible(true);
		conectarItem.setVisible(true);
	}

	public void ocultarMenuSeleccionCanciones() {
		conectarItem.setVisible(false);
		logoutItem.setVisible(false);
		menuApp.findItem(R.id.itemIrTusListas).setVisible(false);
		menuApp.findItem(R.id.itemCancelarPromocionada).setVisible(true);
		menuApp.findItem(R.id.itemBorrarPromocionada).setVisible(true);

	}

	public void borrarCanciones() {
		ArrayList<CancionPromocionada> nuevaLista = new ArrayList<CancionPromocionada>();
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
		for (CancionPromocionada c : datosListaPromocionada) {
			adapter.anadirCancion(c);
			seleccion.add(false);
		}
		adapter.seleccionados = seleccion;
		adapter.notifyDataSetChanged();
		mostrarMenuSeleccionCanciones();
		modoSeleccion = false;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.itemBorrarPromocionada:
			borrarCanciones();
			Toast.makeText(this, "Canciones Borradas", Toast.LENGTH_SHORT)
					.show();
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
			mostrarMenuSeleccionCanciones();
			return true;
		case R.id.itemLogout:
			if (manager.conectado) {
				showLogoutDialog();
			}
			return true;

		case R.id.itemIrTusListas:
			Intent inte = new Intent(ListaPromocionada.this,
					ListasCanciones.class);
			inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(inte);
			return true;
		case R.id.itemConectarServidor:
			if (!manager.conectado) {

				LayoutInflater inflater = (LayoutInflater) this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View v = inflater.inflate(
						R.layout.style_view_nombre_servidor,
						lista, false);
				alertBuilder.setView(v);
				alertBuilder.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								EditText input = (EditText) v
										.findViewById(R.id.txtInputNombreServidor);
								final String uuid = Installation
										.id(getApplicationContext());
								String value = input.getText().toString();
								if (!value.equals("")) {
									login(value, uuid);
								} else {
									Toast.makeText(
											lista.getContext(),
											"Escriba un nombre para el servidor",
											Toast.LENGTH_SHORT).show();
									// dialog.cancel();
								}
							}
						});

				alertBuilder.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
							}
						});
				alertBuilder.show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Nombre del Tumpi: " + manager.nick, Toast.LENGTH_SHORT)
						.show();
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
						ListaPromocionada.this)
						.setSmallIcon(R.drawable.logo_tumpi)
						.setLargeIcon(
								(((BitmapDrawable) ListaPromocionada.this
										.getResources().getDrawable(
												R.drawable.logo_tumpi))
										.getBitmap()))
						.setContentTitle("Nueva lista de reproducción")
						.setContentText("Pulsa aqui aqui entrar a votar!")
						.setTicker("El dj ha publicado una nueva lista!");

				Intent notIntent = new Intent(ListaPromocionada.this,
						SeleccionAplicacion.class);

				PendingIntent contIntent = PendingIntent.getActivity(
						ListaPromocionada.this, 0, notIntent, 0);

				mBuilder.setContentIntent(contIntent);

				NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

				mNotificationManager.notify(1, mBuilder.build());

			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void logout() {
		if (manager.cerrarConexion()) {
			irSeleccionApp();
		} else {
			Toast.makeText(lista.getContext(),
					"Error al salir del Tumpi", Toast.LENGTH_SHORT).show();
		}
	}

	private void login(String value, String uuid) {
		if (manager.logInBridge(value, uuid)) {
			Toast.makeText(lista.getContext(), "Servidor conectado",
					Toast.LENGTH_SHORT).show();
			conectarItem.setIcon(R.drawable.conectado);
			logoutItem.setVisible(true);
		} else {
			Toast.makeText(lista.getContext(),
					"Error al publicar el servidor", Toast.LENGTH_SHORT).show();
			manager.cerrarConexion();
		}
	}

	private void irSeleccionApp() {
		Intent inte = new Intent(ListaPromocionada.this,
				SeleccionAplicacion.class);
		inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(inte);
	}

	public void modeloCambio() {
		TextView txtNombreCancionReproduciendo = (TextView) findViewById(R.id.txtNombreCancionReproduciendo);
		txtNombreCancionReproduciendo.post(new Runnable() {
			public void run() {
				adapter.notifyDataSetChanged();
			}
		});
	}

	public void updatePlayer() {
		if (manager.getCancionReproduciendo() != null) {
			TextView txtNombreCancionReproduciendo = (TextView) findViewById(R.id.txtNombreCancionReproduciendo);
			txtNombreCancionReproduciendo.setText(manager
					.getCancionReproduciendo().nombreCancion);
			TextView txtNombreAlbumReproduciendo = (TextView) findViewById(R.id.txtNombreAlbumReproduciendo);
			txtNombreAlbumReproduciendo.setText(manager
					.getCancionReproduciendo().nombreAutor);
			ImageView imagen = (ImageView) findViewById(R.id.caratulaDisco);
			imagen.setImageBitmap(explorer.getAlbumImage(manager
					.getCancionReproduciendo().album_id));
			txtNombreCancionReproduciendo.setSelected(true);
			txtNombreAlbumReproduciendo.setSelectAllOnFocus(true);
			ImageButton boton = (ImageButton) findViewById(R.id.btnPlay);
			if (manager.player.isPlaying()) {
				boton.setImageResource(R.drawable.image_pause);
			} else {
				boton.setImageResource(R.drawable.image_play);
			}
		}
	}

	public void clickPlay(View v) {
		manager.player.pause();
		ImageButton boton = (ImageButton) v;
		if (manager.player.isPlaying()) {
			boton.setImageResource(R.drawable.image_pause);
		} else {
			boton.setImageResource(R.drawable.image_play);
		}
	}

	public void clickNext(View v) {
		manager.procesarVotos();
		TextView txtNombreCancionReproduciendo = (TextView) findViewById(R.id.txtNombreCancionReproduciendo);
		txtNombreCancionReproduciendo.post(new Runnable() {
			public void run() {
				updatePlayer();
			}
		});
	}

	public void crearLista(View v) {
		Intent inte = new Intent(ListaPromocionada.this, ListasCanciones.class);
		inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		inte.putExtra("crearLista", showCreateListaDialog);
		startActivity(inte);
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

	private void showLogoutDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("¿Estas seguro de que quieres de este Tumpi?")
				.setPositiveButton("Si", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						logout();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

	@Override
	public void onBackPressed() {
		irSeleccionApp();
	}
}