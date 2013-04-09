/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 *
 * @author sdfhgjkltñfdtgf
 */
public class SwipeViewPagerAdapter extends FragmentStatePagerAdapter {
    
    private int numeroListas = 3;

    public SwipeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        ListaFragment fragment = new ListaFragment();
        // Our object is just an integer :-P
        switch (i) {
            case 0:
                fragment.datos.add("Cancion1");
                fragment.datos.add("Cancion2");
                fragment.datos.add("Cancion3");
                fragment.datos.add("Cancion4");
                fragment.datos.add("Cancion5");
                fragment.datos.add("Cancion6");
                fragment.datos.add("Cancion7");
                fragment.datos.add("Cancion8");
                fragment.datos.add("Cancion9");
                fragment.datos.add("Cancion10");
                break;
            case 1:
                fragment.datos.add("Cancioncita1");
                fragment.datos.add("Cancioncita2");
                fragment.datos.add("Cancioncita3");
                fragment.datos.add("Cancioncita4");
                break;
            case 2:
                fragment.datos.add("Los Redondeles1");
                fragment.datos.add("Los Redondeles2");
                fragment.datos.add("Los Redondeles3");
                fragment.datos.add("Los Redondeles4");
                fragment.datos.add("Los Redondeles5");
                fragment.datos.add("Los Redondeles6");
                fragment.datos.add("Los Redondeles7");
                fragment.datos.add("Los Redondeles8");
                fragment.datos.add("Los Redondeles9");
                fragment.datos.add("Los Redondeles10");
                fragment.datos.add("Los Redondeles11");
                fragment.datos.add("Los Redondeles12");
                break;
            default:
                fragment.datos.add("Otra Cancioncita");
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return numeroListas;
    }
    
    public void crearLista(){
        numeroListas++;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Lista " + (position + 1);
    }
}
