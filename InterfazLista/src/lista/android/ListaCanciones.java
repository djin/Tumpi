package lista.android;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lista.android.conexion.*;

public class ListaCanciones extends ListActivity implements ServerMessageListener
{
static ArrayList<Cancion> lista = new ArrayList<Cancion>();
AdaptadorLista listadoAdapter;
static Boolean noReiniciar = true;
ConnectionManager conex;
public static TextView t;
@Override
public void onCreate(Bundle savedInstanceState) {
 
    super.onCreate(savedInstanceState);
    //Establecemos el diseño principal de la Actividad
    setContentView(R.layout.main);
    
    // Este método de obtención de elementos puede cambiarse por cualquier otro
    //como leerlos de una BBDD o de un servidor web con JSON
    listadoAdapter = new AdaptadorLista(this, lista, R.layout.rowstyle);
    setListAdapter(listadoAdapter);
    t=(TextView) findViewById(R.id.txtPlaying);
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
        
        //ArrayList<Cancion> lista = new ArrayList<Cancion>();
        
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
                switch(tipo){
                    case 1:
                        int n = listadoAdapter.getDatos().size();
                        for(int i=0; i < n ; i++){
                            listadoAdapter.getDatos().remove(0);
                        }
                        String[] canciones = message.split("\\;");
                        interpretarLista(canciones);
                        listadoAdapter.notifyDataSetChanged();

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
    public void cambiarTxt(View v){
        int n = listadoAdapter.getDatos().size();
        for(int i=0; i < n ; i++){
            listadoAdapter.getDatos().remove(0);
        }
        String canciones = "1*Los Redondeles*caca*pis;2*Los Redondeles*caca*pis;3*Los Redondeles*caca*pis;4*Los Redondeles*caca*pis";
        interpretarLista(canciones.split("\\;"));
        listadoAdapter.notifyDataSetChanged();
    }
    public void cambiarTxt1(View v){
        int n = listadoAdapter.getDatos().size();
        for(int i=0; i < n ; i++){
            listadoAdapter.getDatos().remove(0);
        }
        String canciones = "1*Arribas*caca*pis;2*en tu puta cara*caca*pis;3*me rio*caca*pis;4*Fdo: Sergio*caca*pis";
        interpretarLista(canciones.split("\\;"));
        listadoAdapter.notifyDataSetChanged();
    }
}




