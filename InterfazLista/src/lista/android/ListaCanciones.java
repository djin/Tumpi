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
@Override
public void onCreate(Bundle savedInstanceState) {
 
    super.onCreate(savedInstanceState);
    //Establecemos el diseño principal de la Actividad
    setContentView(R.layout.main);
    
    // Este método de obtención de elementos puede cambiarse por cualquier otro
    //como leerlos de una BBDD o de un servidor web con JSON
    listadoAdapter = new AdaptadorLista(this, lista, R.layout.rowstyle);
    setListAdapter(listadoAdapter);
    conex=new ConnectionManager();
    
        try {
            conex.conexion.cerrarConexion();
            conex.conectar("192.168.43.87", 2222, this);
            conex.conexion.startListeningServer();
        } catch (Exception ex) {
            Logger.getLogger(ListaCanciones.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
//        Cancion evento1 = new Cancion("Ofrenda de Flores y gonzalez de la juerga padre","", "", 1, false);
//        lista.add(evento1);
//        
//        Cancion evento2 = new Cancion("Los Redondeles","", "", 2, false);
//        lista.add(evento2);
//
//        Cancion evento3 = new Cancion("Futbol","", "", 3, false);
//        lista.add(evento3);
//        
//        Cancion evento4 = new Cancion("Ofrenda de Flores y gonzalez de la juerga padre","", "", 4, false);
//        lista.add(evento4);
//
//        Cancion evento5 = new Cancion("Los Redondeles","", "", 5, false);
//        lista.add(evento5);
//
//        Cancion evento6 = new Cancion("Futbol","", "", 6, false);
//        lista.add(evento6);
//
//        Cancion evento7 = new Cancion("Ofrenda de Flores y gonzalez de la juerga padre","", "", 7, false);
//        lista.add(evento7);
//
//        Cancion evento8 = new Cancion("Los Redondeles","", "", 8, false);
//        lista.add(evento8);
//
//        Cancion evento9 = new Cancion("Futbol","", "", 9, false);
//        lista.add(evento9);
//        
//        Cancion evento10 = new Cancion("Ofrenda de Flores y gonzalez de la juerga padre","", "", 10, false);
//        lista.add(evento10);
//
//        Cancion evento11 = new Cancion("Los Redondeles","", "", 11, false);
//        lista.add(evento11);
//
//        Cancion evento12 = new Cancion("Futbol","", "", 12, false);
//        lista.add(evento12);
//        
//        Cancion evento13 = new Cancion("Ofrenda de Flores y gonzalez de la juerga padre","", "", 13, false);
//        lista.add(evento13);
//
//        Cancion evento14 = new Cancion("Los Redondeles","", "", 14, false);
//        lista.add(evento14);
//
//        Cancion evento15 = new Cancion("Futbol","", "", 15, false);
//        lista.add(evento15);
    }    
    public void onMessageReceive(String message) {
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




