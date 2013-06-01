/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tumpi.servidor.Manejador;

import app.tumpi.servidor.interfaz.social.ListaFragment;
import app.tumpi.servidor.interfaz.social.ListasCanciones;

/**
 *
 * @author zellyalgo
 */
public class ManejadorAcciones {

    private static ManejadorAcciones INSTANCE = new ManejadorAcciones();
    private ListasCanciones listasCanciones;
    private ListaFragment listaFragment;

    private ManejadorAcciones() {
    }

    public static ManejadorAcciones getInstance() {
        return INSTANCE;
    }

    public void setListaCanciones(ListasCanciones lc) {
        listasCanciones = lc;
    }

    public void setListaFragment(ListaFragment lf) {
        listaFragment = lf;
    }

    public void modoSeleccion() {
        listasCanciones.apareceMenuSeleccion();
    }

    public void cancelarSeleccion() {
        if (listaFragment != null) {
            listaFragment.cancelarSeleccion();
        }
    }
    
    public void finModoSeleccion(){
        listasCanciones.desapareceMenuSeleccion();
    }
    
    public void borrarCanciones (){
        listaFragment.borrarCanciones();
    }
}
