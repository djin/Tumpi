package lista.android;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;

public class ListaCanciones extends ListActivity
{
    //Mantenemos un Array de elementos en la que podremos guardar
//más información de la que mostraremos en el listado
ArrayList<HashMap<String,String>> Eventos;

//Con los siguientes Arrays establecemos la correspondencia
//entre los elementos del Array de HashMaps de eventos (from)
//con los elementos del diseño en XML de cada una de las filas (to)
String[] from=new String[] {"Name"};
int[] to=new int[]{R.id.songName};

@Override
public void onCreate(Bundle savedInstanceState) {
 
    super.onCreate(savedInstanceState);
    //Establecemos el diseño principal de la Actividad
    setContentView(R.layout.main);
 
    // Este método de obtención de elementos puede cambiarse por cualquier otro
    //como leerlos de una BBDD o de un servidor web con JSON
    ArrayList<String[]> lista = new ArrayList<String[]>();
 
    String[] evento1 = {"Ofrenda de Flores y gonzalez de la juerga padre", "1"};
    lista.add(evento1);
 
    String[] evento2 = {"Los Redondeles", "2"};
    lista.add(evento2);
 
    String[] evento3 = {"Futbol","3"};
    lista.add(evento3);
    
    String[] evento4 = {"Ofrenda de Flores y gonzalez de la juerga padre", "4"};
    lista.add(evento4);
 
    String[] evento5 = {"Los Redondeles", "5"};
    lista.add(evento5);
 
    String[] evento6 = {"Futbol","6"};
    lista.add(evento6);
    
    String[] evento7 = {"Ofrenda de Flores y gonzalez de la juerga padre", "7"};
    lista.add(evento7);
 
    String[] evento8 = {"Los Redondeles", "8"};
    lista.add(evento8);
 
    String[] evento9 = {"Futbol","9"};
    lista.add(evento9);
 
    // Transformamos los elementos String[] en HashMap para
    //posteriormente incluirlos en el Array Global que se utilizará
    //para rellenar la lista
    Eventos = new ArrayList<HashMap<String, String>>();
    for(String[] evento:lista){
        HashMap<String,String> datosEvento=new HashMap<String, String>();
 
        // Aquí es dónde utilizamos las referencias creadas inicialmente
        //en el elemento "from"
        datosEvento.put("Name", evento[0]);
        datosEvento.put("id", evento[1]);
 
        Eventos.add(datosEvento);
    }
    // Una vez tenemos toda la información necesaria para rellenar la lista
    //creamos un elemento que nos facilitará la tarea:
    //SimpleAdapter(Actividad, Array de HashMap con elementos, Fichero XML del
    //diseño de cada fila, Cadenas del HashMap, Ids del Fichero XML del diseño de cada fila)
    SimpleAdapter listadoAdapter=new SimpleAdapter(this, Eventos, R.layout.rowstyle, from, to);
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
    
    public void votado(View v){
        ImageButton img = (ImageButton)v.findViewById(R.id.btnVotar);
        img.setImageResource(R.raw.ico_small_star_focus);
        img.setEnabled(false);
    }
    
}




