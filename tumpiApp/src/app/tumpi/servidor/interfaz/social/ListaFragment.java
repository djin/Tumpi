/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tumpi.servidor.interfaz.social;

import app.tumpi.servidor.Manejador.ManejadorAcciones;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import app.tumpi.R;
import java.util.ArrayList;
import app.tumpi.servidor.modelo.datos.Cancion;
import app.tumpi.servidor.modelo.datos.ListasManager;

/**
 *
 * @author fdgbgbnesad
 */
public class ListaFragment extends ListFragment {

    public ArrayList<Cancion> datos = new ArrayList<Cancion>();
    private ArrayList<Boolean> seleccionados = new ArrayList<Boolean>();
    private Boolean primera;
    private AdaptadorLista adapter;
    private ViewPager viewPager;
    private Boolean modoSeleccion = false;
    private ManejadorAcciones manejador;
    private ListaFragment lf = this;
    private int posicion;
    private ListasManager manager;

    public ListaFragment(Boolean pri, int posi) {
        primera = pri;
        manejador = ManejadorAcciones.getInstance();
        posicion = posi;
        manager = ListasManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        viewPager = (ViewPager) container;
        View rootView = inflater.inflate(R.layout.style_listas_preparacion, container, false);
        ListView listaCanciones = (ListView) rootView.findViewById(android.R.id.list);
        if (!primera) {
            datos = manager.getLista(posicion).getCanciones();
            for (Cancion c : datos) {
                seleccionados.add(false);
            }
        }
        adapter = new AdaptadorLista(this.getActivity(), datos, seleccionados, R.layout.row_style_preparation);
        if (!primera) {
            if (adapter.seleccionados.isEmpty()) {
            }
            listaCanciones.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    modoSeleccion = true;
                    manejador.setListaFragment(lf);
                    adapter.seleccionados.set(position, true);
                    adapter.notifyDataSetChanged();
                    manejador.modoSeleccion();
                    return true;
                }
            });
        } else {
            notificarUltimaListaBorrada();
        }
        listaCanciones.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (primera) {
            ((SwipeViewPagerAdapter) viewPager.getAdapter()).crearLista(viewPager);
        } else {
            if (modoSeleccion) {
                if (adapter.seleccionados.get(position)) {
                    adapter.seleccionados.set(position, false);
                } else {
                    adapter.seleccionados.set(position, true);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void cancelarSeleccion() {
        int i = 0;
        for (Boolean b : adapter.seleccionados) {
            b = false;
            adapter.seleccionados.set(i, b);
            i++;
        }
        modoSeleccion = false;
        adapter.notifyDataSetChanged();
    }

    public void notificarPrimeraListaCreada() {
        datos.set(0, new Cancion(0, "Los Redondeles", "Siempre Fuertes", 1, "HUAE", 1234, "C:/"));
        adapter.seleccionados.add(false);
        adapter.notifyDataSetChanged();
    }

    public void notificarUltimaListaBorrada() {
        adapter.limpiarDatos();
        datos.add(new Cancion(0, "Crea una Lista para EMPEZAR", "Puedes pulsando el +", 1,"o... PULSAME", 0, "C:/"));
        adapter.seleccionados.add(false);
        adapter.cambioEstilo(true);
        adapter.notifyDataSetChanged();
    }

    public void borrarCanciones() {
        ArrayList<Cancion> nueva = new ArrayList<Cancion>();
        ArrayList<Boolean> seleccion = new ArrayList<Boolean>();
        int posicionABorrar = 0;
        for (Boolean b : adapter.seleccionados) {
            if (!b) {
                nueva.add(datos.get(posicionABorrar));
            }
            posicionABorrar++;
        }
        modoSeleccion = false;
        adapter.limpiarDatos();
        datos = nueva;
        for(Cancion c: datos){
            adapter.anadirCancion(c);
            seleccion.add(false);
        }
        adapter.seleccionados = seleccion;
        adapter.notifyDataSetChanged();
    }
}
