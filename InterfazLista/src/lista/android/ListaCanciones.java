package lista.android;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.ArrayList;
import lista.android.conexion.*;

public class ListaCanciones extends ListActivity implements ServerMessageListener
{
static ArrayList<Cancion> lista = new ArrayList<Cancion>();
AdaptadorLista listadoAdapter;
static Boolean noReiniciar = true;
ConnectionManager conex;
static Cancion cancion_sonando;
public static TextView text_playing;
public static TextView text_autorPlaying;
@Override
public void onCreate(Bundle savedInstanceState) {
 
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    listadoAdapter = new AdaptadorLista(this, lista, R.layout.rowstyle);
    setListAdapter(listadoAdapter);
    text_playing=(TextView) findViewById(R.id.txtPlaying);
    text_autorPlaying = (TextView)findViewById(R.id.txtPlayingAutor);
    conex=new ConnectionManager();        
    conex.conexion.addServerMessageListener(this);
    if(cancion_sonando==null)
        cancion_sonando=new Cancion("Nombre canción", "Artista", "Album", 0, false, false);
    refrescarCancionSonando();
    }
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
    private void refrescarCancionSonando(){
        text_playing.post(new Runnable(){
            public void run() {
                text_playing.setText(cancion_sonando.getNombreCancion());
                text_autorPlaying.setText(cancion_sonando.getNombreAutor());
            }
        });
    }
    public void interpretarLista (String[] canciones){
        
        for(String cancion : canciones){
            String[] datos_cancion=cancion.split("\\*");
            lista.add(new Cancion(datos_cancion[1],datos_cancion[2], datos_cancion[3], Integer.parseInt(datos_cancion[0]), false, false));
        }
    }    
    public void onMessageReceive(final String men) {
        
        this.getListView().post(new Runnable(){
            public void run() {
                String message=men;
                int tipo=Integer.parseInt(message.split("\\|")[0]);
                message=message.split("\\|")[1];
                int n=0;
                switch(tipo){
                    case 0:
                        listadoAdapter.limpiarDatos();
                        String[] canciones = message.split("\\;");
                        interpretarLista(canciones);
                        listadoAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        n=Integer.parseInt(message);
                        if(n!=0){
                            for(Cancion c : listadoAdapter.getDatos()){
                                if(c.getId() == n ){
                                    c.setVotado(true);
                                    break;
                                }
                            }
                            listadoAdapter.notifyDataSetChanged();
                        }
                        break;
                    case 2:
                        n=Integer.parseInt(message);
                        Cancion cancion=null;
                        for(Cancion c : listadoAdapter.getDatos()){
                            if(c.getId() == n){
                                cancion=c;
                                break;
                            }
                        }
                        cancion_sonando=cancion;
                        listadoAdapter.getDatos().remove(cancion);
                        cancion.setSonado(true);
                        listadoAdapter.getDatos().add(cancion);
                        listadoAdapter.notifyDataSetChanged();
                        refrescarCancionSonando();
                        break;
                    case 3:
                        n=Integer.parseInt(message);
                        if(n!=0){
                            for(Cancion c : listadoAdapter.getDatos()){
                                if(c.getId() == n ){
                                    c.setVotado(false);
                                    break;
                                }
                            }
                            listadoAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        });
        
    }
    /*@Override
    public void onConfigurationChanged (Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        super.onBackPressed();
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            conex.conexion.removeServerMessageListener(this);
            conex.conexion.cerrarConexion();
        } catch (Exception ex) {
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
            
    }
}