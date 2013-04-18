/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import Manejador.ManejadorAcciones;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.EditText;
import java.util.ArrayList;
import modelo.datos.Cancion;
import modelo.datos.ListaCanciones;
import modelo.datos.ListasManager;

/**
 *
 * @author sdfhgjkltñfdtgf
 */
public class SwipeViewPagerAdapter extends FragmentStatePagerAdapter {

    private int numeroListas = 1;
    private ArrayList<String> nombresListas;
    private ListaFragment primerFragment;
    private ArrayList<Object> listasCreadas;
    private ListasManager modelo;
    private ManejadorAcciones manejador;

    public SwipeViewPagerAdapter(FragmentManager fm) {
        super(fm);
        modelo = ListasManager.getInstance();
        nombresListas = modelo.nombreLista;
        listasCreadas = new ArrayList<Object>();
        manejador = ManejadorAcciones.getInstance();
        modelo.listasCanciones.add(new ListaCanciones());
    }

    @Override
    public Fragment getItem(int i) {
        ListaFragment fragment;
        if (nombresListas.isEmpty()) {
            fragment = new ListaFragment(true, i);
            primerFragment = fragment;
        } else {
            fragment = new ListaFragment(false, i);
        }
        listasCreadas.add(fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        if (nombresListas.isEmpty()) {
            return 1;
        } else {
            return nombresListas.size();
        }
    }

    public void crearLista(final ViewGroup container) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(container.getContext());
        final EditText input = new EditText(container.getContext());
        alert.setView(input);
        alert.setTitle("Nombre Lista");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                if (!value.equals("")) {
                    if (!nombresListas.isEmpty()) {
                        numeroListas++;
                    } else {
                        primerFragment.notificarPrimeraListaCreada();
                    }
                    nombresListas.add(value);
                    modelo.listasCanciones.add(new ArrayList<Cancion>());
                    notifyDataSetChanged();
                    ViewPager vp = (ViewPager) container;
                    vp.setCurrentItem(numeroListas);
                    ((ListaFragment)listasCreadas.get(numeroListas)).datos = modelo.getLista(numeroListas);
                } else {
                    dialog.cancel();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    public void borrarLista(ViewGroup container, int position) {
        if (nombresListas.size() > 1) {
            nombresListas.remove(position);
            Fragment aBorrar = (Fragment) listasCreadas.get(position);
            listasCreadas.remove(aBorrar);
            numeroListas--;
        } else {
            ((ListaFragment) listasCreadas.get(position)).notificarUltimaListaBorrada();
            nombresListas.remove(0);
            manejador.finModoSeleccion();
        }
        modelo.listasCanciones.remove(position);
    }
    
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (nombresListas.isEmpty()) {
            return "";
        } else {
            return nombresListas.get(position);
        }
    }

    public ArrayList<String> getNombresListas() {
        return nombresListas;
    }
}
