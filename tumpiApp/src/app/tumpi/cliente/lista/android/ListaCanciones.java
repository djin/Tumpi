package app.tumpi.cliente.lista.android;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import app.tumpi.R;

import java.util.ArrayList;
import java.util.HashSet;

import app.tumpi.cliente.lista.android.conexion.*;
import app.tumpi.util.Installation;

public class ListaCanciones extends ActionBarActivity implements ServerMessageListener {

    static ArrayList<Cancion> lista = new ArrayList<Cancion>();
    AdaptadorLista listadoAdapter;
    static Boolean noReiniciar = true;
    ListaCanciones p;
    ConnectionManager conex;
    static Cancion cancion_sonando;
    public static TextView text_playing;
    public static TextView text_autorPlaying;
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_cliente);

        //Es para quitar los botones de compartir y favorito, para que no se vean en la interfaz
        ImageButton btn = (ImageButton) findViewById(R.id.btnFav);
        btn.setImageDrawable(null);
        btn.setEnabled(false);
        ImageButton btn1 = (ImageButton) findViewById(R.id.btnshare);
        btn1.setImageDrawable(null);
        btn1.setEnabled(false);
        //////////////////////////////////////////////////////////////////////////////////////
        lista.clear();
        listadoAdapter = new AdaptadorLista(this, lista, R.layout.rowstyle);
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setAdapter(listadoAdapter);
        text_playing = (TextView) findViewById(R.id.txtPlaying);
        text_playing.setSelected(true);
        text_autorPlaying = (TextView) findViewById(R.id.txtPlayingAutor);
        conex = new ConnectionManager();
        conex.conexion.addServerMessageListener(this);
        if (cancion_sonando == null) {
            cancion_sonando = new Cancion("Nombre canción", "Artista", "Album", 0, 0, false, false);
            text_playing.post(new Runnable() {
                public void run() {
                    try {
                        conex.conexion.enviarMensaje("0|" + Installation.id(getApplicationContext()) + "&" + 0);
                    } catch (Exception ex) {
                    }
                }
            });
        }
        refrescarCancionSonando();
        p = this;
    }
    
    // QUITO EL MENU, PORQUE NO QUEREMOS MENU DE OPCIONES POR AHORA
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuactionbar, menu);
        MenuItem btn = menu.findItem(R.id.btnSettings);
        btn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ListaCanciones.this, Settings.class);

                startActivity(intent);
                return true;
            }
        });
        return true;
    }
*/
    
    ///////////////////////////////////////////////////////////////////////
    private void refrescarCancionSonando() {
        text_playing.post(new Runnable() {
            public void run() {
                text_playing.setText(cancion_sonando.getNombreCancion());
                text_autorPlaying.setText(cancion_sonando.getNombreAutor());
            }
        });
    }

    public void interpretarLista(String[] canciones) {
        if (canciones[0].equals("empty")) {
            lista.add(new Cancion("El Dj no ha propuesto canciones", "En breves minutos aparecerá", " ", 0, 100, false, true));
        } else {
            ArrayList<Cancion> lista_aux = new ArrayList();
            for (String cancion : canciones) {
                String[] datos_cancion = cancion.split("\\*");
                if (!"1".equals(datos_cancion[5])) {
                    lista.add(new Cancion(datos_cancion[1], datos_cancion[2], datos_cancion[3], Integer.parseInt(datos_cancion[0]), Long.parseLong(datos_cancion[4]), false, false));
                } else {
                    lista_aux.add(new Cancion(datos_cancion[1], datos_cancion[2], datos_cancion[3], Integer.parseInt(datos_cancion[0]), Long.parseLong(datos_cancion[4]), false, true));
                }
            }
            if (lista_aux.size() > 0) {
                lista.addAll(lista_aux);
            }
        }
    }
    
    private void voteSongs(HashSet<Integer> votedSongs){
    	for(Integer songId : votedSongs){
    		// TO DO, usar un mapa!
            for (Cancion c : listadoAdapter.getDatos()) {
                if (c.getId() == songId) {
                    c.setVotado(true);
                    c.votando=false;
                    break;
                }
            }
    	}
    	listadoAdapter.notifyDataSetChanged();
    }

    public void onMessageReceive(final String men) {

    	text_playing.post(new Runnable() {
            public void run() {
                String message = men;
                if (!"exit".equals(message)) {
                    int tipo = Integer.parseInt(message.split("\\|")[0]);
                    message = message.split("\\|")[1];
                    int n = 0;
                    switch (tipo) {
                        case 0:
                        	final int delimiterIndex = message.indexOf("&");
                            final String votedSongsData = message.substring(0, delimiterIndex);
                            final String promotedListData = message.substring(delimiterIndex + 1);
                            listadoAdapter.limpiarDatos();
                            HashSet<Integer> votedSongs = parseVotedSongs(votedSongsData);
                            String[] canciones = promotedListData.split("\\;");
                            interpretarLista(canciones);
                            voteSongs(votedSongs);
                            listadoAdapter.notifyDataSetChanged();
                            break;
                        case 1:
                            n = Integer.parseInt(message);
                            if (n != 0) {
                                for (Cancion c : listadoAdapter.getDatos()) {
                                    if (c.getId() == n) {
                                        c.setVotado(true);
                                        c.votando=false;
                                        break;
                                    }
                                }
                                listadoAdapter.notifyDataSetChanged();
                            }
                            break;
                        case 2:
                            n = Integer.parseInt(message);
                            Cancion cancion = null;
                            for (Cancion c : listadoAdapter.getDatos()) {
                                if (c.getId() == n) {
                                    cancion = c;
                                    break;
                                }
                            }
                            cancion_sonando = cancion;
                            listadoAdapter.getDatos().remove(cancion);
                            cancion.setSonado(true);
                            listadoAdapter.getDatos().add(cancion);
                            listadoAdapter.notifyDataSetChanged();
                            refrescarCancionSonando();
                            break;
                        case 3:
                            n = Integer.parseInt(message);
                            if (n != 0) {
                                for (Cancion c : listadoAdapter.getDatos()) {
                                    if (c.getId() == n) {
                                        c.setVotado(false);
                                        c.votando=false;
                                        break;
                                    }
                                }
                                listadoAdapter.notifyDataSetChanged();
                            }
                            break;
                        case 4:
                            String[] datos_cancion = message.split("\\*");
                            cancion_sonando = new Cancion(datos_cancion[1], datos_cancion[2], datos_cancion[3], Integer.parseInt(datos_cancion[0]), Long.parseLong(datos_cancion[4]), false, "1".equals(datos_cancion[5]));
                            refrescarCancionSonando();
                            break;
                    }
                } else {
                    Toast.makeText(p, "Perdida la conexión con el servidor", Toast.LENGTH_LONG).show();
                    try {
                        cancion_sonando = null;
                        conex.conexion.removeServerMessageListener(p);
                        conex.conexion.cerrarConexion();
                    } catch (Exception ex) {
                    } finally {
                        p.finish();
                    }
                }
            }

			private HashSet<Integer> parseVotedSongs(String votedSongsString) {
				HashSet<Integer> votedSongsSet = new HashSet<Integer>();
				if(thereAreVotedSongs(votedSongsString)){
					String[] votedSongsIds = votedSongsString.split("\\,");
					for(String songId : votedSongsIds){
						votedSongsSet.add(Integer.parseInt(songId));
					}
				}
				return votedSongsSet;
			}
			
			private boolean thereAreVotedSongs(String votedSongsString){
				return votedSongsString != null && !votedSongsString.isEmpty();
			}
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}