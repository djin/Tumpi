/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.EditText;
import java.util.ArrayList;
import modelo.datos.ModeloDatos;

/**
 *
 * @author sdfhgjkltñfdtgf
 */
public class SwipeViewPagerAdapter extends FragmentStatePagerAdapter {

    private int numeroListas = 1;
    private ArrayList<String> nombresListas;
    private ListaFragment primerFragment;
    private ArrayList<Object> listasCreadas;
    private ModeloDatos modelo;

    public SwipeViewPagerAdapter(FragmentManager fm) {
        super(fm);
        modelo = ModeloDatos.getInstance();
        nombresListas = modelo.nombreLista;
        listasCreadas = new ArrayList<Object>();
        
    }

    @Override
    public Fragment getItem(int i) {
        ListaFragment fragment;
        if (nombresListas.isEmpty()) {
            fragment = new ListaFragment(true);
            primerFragment = fragment;
        } else {
            fragment = new ListaFragment(false);
        }
        // Our object is just an integer :-P
//        switch (i) {
//            case 0:
//                fragment.datos.add("Cancion1");
//                fragment.datos.add("Cancion2");
//                fragment.datos.add("Cancion3");
//                fragment.datos.add("Cancion4");
//                fragment.datos.add("Cancion5");
//                fragment.datos.add("Cancion6");
//                fragment.datos.add("Cancion7");
//                fragment.datos.add("Cancion8");
//                fragment.datos.add("Cancion9");
//                fragment.datos.add("Cancion10");
//                break;
//            case 1:
//                fragment.datos.add("Cancioncita1");
//                fragment.datos.add("Cancioncita2");
//                fragment.datos.add("Cancioncita3");
//                fragment.datos.add("Cancioncita4");
//                break;
//            case 2:
//                fragment.datos.add("Los Redondeles1");
//                fragment.datos.add("Los Redondeles2");
//                fragment.datos.add("Los Redondeles3");
//                fragment.datos.add("Los Redondeles4");
//                fragment.datos.add("Los Redondeles5");
//                fragment.datos.add("Los Redondeles6");
//                fragment.datos.add("Los Redondeles7");
//                fragment.datos.add("Los Redondeles8");
//                fragment.datos.add("Los Redondeles9");
//                fragment.datos.add("Los Redondeles10");
//                fragment.datos.add("Los Redondeles11");
//                fragment.datos.add("Los Redondeles12");
//                break;
//            default:
//                fragment.datos.add("Otra Cancioncita");
//        }
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
                    notifyDataSetChanged();
                    ViewPager vp = (ViewPager) container;
                    vp.setCurrentItem(numeroListas);
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
        }
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
