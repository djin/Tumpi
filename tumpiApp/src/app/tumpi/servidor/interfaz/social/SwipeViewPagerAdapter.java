/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tumpi.servidor.interfaz.social;

import app.tumpi.servidor.Manejador.ManejadorAcciones;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.tumpi.R;
import android.widget.EditText;
import java.util.ArrayList;
import app.tumpi.servidor.modelo.datos.ListaCanciones;
import app.tumpi.servidor.modelo.datos.ListasManager;

/**
 *
 * @author sdfhgjkltñfdtgf
 */
public class SwipeViewPagerAdapter extends FragmentStatePagerAdapter {

    private int numeroListas;
    private ArrayList<String> nombresListas;
    private ListaFragment primerFragment;
    private ArrayList<Object> listasCreadas;
    private ListasManager manager;
    private ManejadorAcciones manejador;

    public SwipeViewPagerAdapter(FragmentManager fm) {
        super(fm);
        manager = ListasManager.getInstance();
        nombresListas = manager.nombreLista;
        listasCreadas = new ArrayList<Object>();
        manejador = ManejadorAcciones.getInstance();
        manager.listasCanciones.add(new ListaCanciones());
        numeroListas = nombresListas.size();
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
//        final EditText input = new EditText(container.getContext());
        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.style_alert_crear_lista, container, false);
        alert.setView(v);
//        alert.setView(input);
//        alert.setTitle("Nombre Lista");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                EditText input = (EditText)v.findViewById(R.id.txtInputNombreLista);
                String value = input.getText().toString();
                if (!value.equals("")) {
                    if (!nombresListas.isEmpty()) {
                        numeroListas++;
                    } else {
                        primerFragment.notificarPrimeraListaCreada();
                    }
                    nombresListas.add(value);
                    manager.listasCanciones.add(new ListaCanciones());
                    notifyDataSetChanged();
                    ViewPager vp = (ViewPager) container;
                    vp.setCurrentItem(numeroListas);
                    ((ListaFragment)listasCreadas.get(numeroListas)).datos = manager.getLista(numeroListas).getCanciones();
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
        if (hasLists()) {
            nombresListas.remove(position);
            Fragment aBorrar = (Fragment) listasCreadas.get(position);
            listasCreadas.remove(aBorrar);
            numeroListas--;
        } else {
            ((ListaFragment) listasCreadas.get(position)).notificarUltimaListaBorrada();
            nombresListas.remove(0);
            manejador.finModoSeleccion();
        }
        manager.listasCanciones.remove(position);
        manager.guardar();
    }
    
    private boolean hasLists() {
    	return nombresListas.size() > 1;
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
