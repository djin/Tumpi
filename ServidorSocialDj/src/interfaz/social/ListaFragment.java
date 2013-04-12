/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import modelo.datos.Cancion;

/**
 *
 * @author fdgbgbnesad
 */
public class ListaFragment extends ListFragment {

    public ArrayList<Cancion> datos = new ArrayList<Cancion>();
    private Boolean primera;
    private AdaptadorLista adapter;
    private ViewPager viewPager;

    public ListaFragment(Boolean pri) {
        primera = pri;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        viewPager = (ViewPager) container;
        View rootView = inflater.inflate(R.layout.style_listas_preparacion, container, false);
        ListView listaCanciones = (ListView) rootView.findViewById(android.R.id.list);
        adapter = new AdaptadorLista(this.getActivity(), datos, R.layout.row_style_preparation);
        if (!primera) {
            datos.add(new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
        } else {
            notificarUltimaListaBorrada();
        }
        listaCanciones.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (primera) {
            ((SwipeViewPagerAdapter) viewPager.getAdapter()).crearLista(viewPager);
        }
    }

    public void notificarPrimeraListaCreada() {
        datos.set(0, new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
        adapter.notifyDataSetChanged();
    }

    public void notificarUltimaListaBorrada() {
        adapter.limpiarDatos();
        datos.add(new Cancion("Crea una Lista para EMPEZAR!!", "Puedes crearla en Opciones -> Crear Lista", "o sino.. PULSAME", 0, 0, false, false));
        adapter.cambioEstilo(true);
        adapter.notifyDataSetChanged();
    }
}
