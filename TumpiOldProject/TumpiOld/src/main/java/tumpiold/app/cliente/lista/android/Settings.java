/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tumpiold.app.cliente.lista.android;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

import tumpiold.app.R;

/**
 *
 * @author 66785320
 */
public class Settings extends ListActivity {
    ArrayList<HashMap<String,String>> Eventos;

    //Con los siguientes Arrays establecemos la correspondencia
    //entre los elementos del Array de HashMaps de eventos (from)
    //con los elementos del diseño en XML de cada una de las filas (to)
    String[] from=new String[] {"Name"};
    int[] to=new int[]{R.id.txtSetting};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        ArrayList<String[]> lista = new ArrayList<String[]>();
 
        String[] evento1 = {"Ver tus logros", "1"};
        lista.add(evento1);

        String[] evento2 = {"Ver Servidor", "2"};
        lista.add(evento2);

        String[] evento3 = {"Mas opciones","3"};
        lista.add(evento3);

        String[] evento4 = {"Volver a lista", "4"};
        lista.add(evento4);
        
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
        SimpleAdapter ListadoAdapter=new SimpleAdapter(this, Eventos, R.layout.rowstylesettings, from, to);
        setListAdapter(ListadoAdapter);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TextView txt = (TextView)v.findViewById(R.id.txtSetting);
        switch(position){
            case 0: break;
            case 1: break;
            case 2: break;
            case 3: 
                break;
                
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action, menu);
        return true;
    }
}