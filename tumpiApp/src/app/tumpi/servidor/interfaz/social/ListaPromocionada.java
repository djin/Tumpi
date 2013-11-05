/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tumpi.servidor.interfaz.social;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import app.tumpi.R;
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
public class ListaPromocionada extends ListActivity implements CambiarListaListener, PlayerListener {

    private ArrayList<CancionPromocionada> datosListaPromocionada;
    private ListasManager manager;
    private AdaptadorListaPromocionada adapter;
    private Boolean modoSeleccion = false;
    private Menu menuApp;
    private AudioExplorer explorer;

	@Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.style_lista_promocionada);
        final ActionBar actionBar = getActionBar();
        manager = ListasManager.getInstance();
        //manager.abrirConexion(getApplicationContext());
        manager.addModeloChangedListener(this);
        manager.player.addPlayerListener(this);
        explorer = AudioExplorer.getInstance(getApplicationContext());
        // Specify that a dropdown list should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter(actionBar.getThemedContext(), R.layout.style_row_spinner,
                R.id.textSpinner, new String[]{"Promocionada", "Tus Listas"}),
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

        datosListaPromocionada = manager.promotedList.getCanciones();
        adapter = new AdaptadorListaPromocionada(this, datosListaPromocionada, R.layout.row_style_promocionada);
        for (Cancion c : datosListaPromocionada) {
            adapter.seleccionados.add(false);
        }
        setListAdapter(adapter);//itemConectarServidor          
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
        updatePlayer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuApp = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu_promocionada, menu);
        desapareceMenu();
        if (manager.conectado) {
            menuApp.getItem(2).setIcon(R.drawable.conectado);
        }
        return true;
    }

    public void desapareceMenu() {
        menuApp.getItem(0).setVisible(false);
        menuApp.getItem(1).setVisible(false);
        menuApp.getItem(2).setVisible(true);
    }

    public void apareceMenu() {
        menuApp.getItem(0).setVisible(true);
        menuApp.getItem(1).setVisible(true);
        menuApp.getItem(2).setVisible(false);
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
        desapareceMenu();
        modoSeleccion = false;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
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
            case R.id.itemConectarServidor:
                if (!manager.conectado) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View v = inflater.inflate(R.layout.style_view_nombre_servidor, this.getListView(), false);
                    alert.setView(v);
                    alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            EditText input = (EditText) v.findViewById(R.id.txtInputNombreServidor);
                            final String uuid = Installation.id(getApplicationContext());
                            String value = input.getText().toString();
                            if (!value.equals("")) {
                                if (manager.logInBridge(value, uuid)) {
                                    Toast.makeText(alert.getContext(), "Servidor conectado", Toast.LENGTH_SHORT).show();
                                    manager.conectado = true;
                                    item.setIcon(R.drawable.conectado);
                                } else {
                                    Toast.makeText(alert.getContext(), "Error al publicar el servidor", Toast.LENGTH_SHORT).show();
                                    manager.cerrarConexion();
                                }
                            } else {
                                Toast.makeText(alert.getContext(), "Escriba un nombre para el servidor", Toast.LENGTH_SHORT).show();
                                //dialog.cancel();
                            }
                        }
                    });

                    alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Nombre del Tumpi: " + manager.nick, Toast.LENGTH_SHORT).show();
                }
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
                if (!adapter.seleccionados.contains(true)) {
                    modoSeleccion = false;
                    desapareceMenu();
                }
            } else {
                adapter.seleccionados.set(position, true);
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void modeloCambio() {
        getListView().post(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void updatePlayer() {
        if (manager.getCancionReproduciendo() != null) {
            TextView txtNombreCancionReproduciendo = (TextView) findViewById(R.id.txtNombreCancionReproduciendo);
            txtNombreCancionReproduciendo.setText(manager.getCancionReproduciendo().nombreCancion);
            TextView txtNombreAlbumReproduciendo = (TextView) findViewById(R.id.txtNombreAlbumReproduciendo);
            txtNombreAlbumReproduciendo.setText(manager.getCancionReproduciendo().nombreAutor);
            ImageView imagen = (ImageView) findViewById(R.id.caratulaDisco);
            imagen.setImageBitmap(explorer.getAlbumImage(manager.getCancionReproduciendo().album_id));
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
        getListView().post(new Runnable() {
            public void run() {
                updatePlayer();
            }
        });
    }

    public void crearLista(View v) {
        Intent inte = new Intent(ListaPromocionada.this, ListasCanciones.class);
        inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        inte.putExtra("crearLista", true);
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
}