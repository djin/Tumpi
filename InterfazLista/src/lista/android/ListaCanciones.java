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
public static TextView text_playing;
@Override
public void onCreate(Bundle savedInstanceState) {
 
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    listadoAdapter = new AdaptadorLista(this, lista, R.layout.rowstyle);
    setListAdapter(listadoAdapter);
    text_playing=(TextView) findViewById(R.id.txtPlaying);
    conex=new ConnectionManager();        
    conex.conexion.addServerMessageListener(this);
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
    
    public void interpretarLista (String[] canciones){
        
        for(String cancion : canciones){
            String[] datos_cancion=cancion.split("\\*");
            lista.add(new Cancion(datos_cancion[1],datos_cancion[2], datos_cancion[3], Integer.parseInt(datos_cancion[0]), false));
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
                                }
                                else if(c.getVotado())
                                    c.setVotado(false);
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
                        text_playing.setText(cancion.getNombreCancion()+"\n"+cancion.getNombreAutor()+" - "+cancion.getNombreAlbum());
                        break;
                }
            }
        });
        
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            conex.conexion.removeServerMessageListener(this);
            conex.conexion.cerrarConexion();
        } catch (Exception ex) {
        }
    }
}