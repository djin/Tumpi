package lista.android;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.util.ArrayList;

public class ListaCanciones extends ListActivity
{
static ArrayList<Cancion> listaCanciones;
static Boolean noReiniciar = true;
@Override
public void onCreate(Bundle savedInstanceState) {
 
    super.onCreate(savedInstanceState);
    //Establecemos el diseño principal de la Actividad
    setContentView(R.layout.main);
    
    // Este método de obtención de elementos puede cambiarse por cualquier otro
    //como leerlos de una BBDD o de un servidor web con JSON
    if(noReiniciar){
        listaCanciones = interpretarLista();
        noReiniciar = false;
    }
    AdaptadorLista listadoAdapter=new AdaptadorLista(this, listaCanciones, R.layout.rowstyle);
    setListAdapter(listadoAdapter);
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
    
    public ArrayList<Cancion> interpretarLista (){
        
        ArrayList<Cancion> lista = new ArrayList<Cancion>();
        
        Cancion evento1 = new Cancion("Ofrenda de Flores y gonzalez de la juerga padre","", "", 1, false);
        lista.add(evento1);

        Cancion evento2 = new Cancion("Los Redondeles","", "", 2, false);
        lista.add(evento2);

        Cancion evento3 = new Cancion("Futbol","", "", 3, false);
        lista.add(evento3);
        
        Cancion evento4 = new Cancion("Ofrenda de Flores y gonzalez de la juerga padre","", "", 4, false);
        lista.add(evento4);

        Cancion evento5 = new Cancion("Los Redondeles","", "", 5, false);
        lista.add(evento5);

        Cancion evento6 = new Cancion("Futbol","", "", 6, false);
        lista.add(evento6);

        Cancion evento7 = new Cancion("Ofrenda de Flores y gonzalez de la juerga padre","", "", 7, false);
        lista.add(evento7);

        Cancion evento8 = new Cancion("Los Redondeles","", "", 8, false);
        lista.add(evento8);

        Cancion evento9 = new Cancion("Futbol","", "", 9, false);
        lista.add(evento9);
        
        Cancion evento10 = new Cancion("Ofrenda de Flores y gonzalez de la juerga padre","", "", 10, false);
        lista.add(evento10);

        Cancion evento11 = new Cancion("Los Redondeles","", "", 11, false);
        lista.add(evento11);

        Cancion evento12 = new Cancion("Futbol","", "", 12, false);
        lista.add(evento12);
        
        Cancion evento13 = new Cancion("Ofrenda de Flores y gonzalez de la juerga padre","", "", 13, false);
        lista.add(evento13);

        Cancion evento14 = new Cancion("Los Redondeles","", "", 14, false);
        lista.add(evento14);

        Cancion evento15 = new Cancion("Futbol","", "", 15, false);
        lista.add(evento15);
        
        return lista;
    }
    
}




