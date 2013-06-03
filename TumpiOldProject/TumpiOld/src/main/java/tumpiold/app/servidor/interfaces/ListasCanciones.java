package tumpiold.app.servidor.interfaces;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import tumpiold.app.R;
import tumpiold.app.servidor.adaptadores.AdaptadorLista;
import tumpiold.app.servidor.modelos.ListaCanciones;
import tumpiold.app.servidor.modelos.ListasManager;

/**
 * Created by 66785320 on 3/06/13.
 */
public class ListasCanciones extends ListActivity {

    AdaptadorLista adapter;
    ListasManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listas_canciones);
        manager = ListasManager.getInstance();
        ListaCanciones canciones = manager.getLista(0);
        if(canciones == null){
            canciones = new ListaCanciones();
        }
        adapter = new AdaptadorLista(canciones.getCanciones(), this);
        setListAdapter(adapter);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerListas);
        ArrayAdapter spAdapter = new ArrayAdapter(this, R.layout.style_row_spinner,
                R.id.textSpinner, new String[]{""});
        spinner.setAdapter(spAdapter);
    }

    public void refresh (View v){
        if(manager.getLista(0) == null){
            Toast.makeText(this, "PUTO NULL", Toast.LENGTH_SHORT).show();
        }else
            adapter.cambiarDatos(manager.getLista(0).getCanciones());
    }
}
