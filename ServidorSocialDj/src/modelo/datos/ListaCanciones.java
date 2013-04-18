/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.datos;

import java.util.ArrayList;

/**
 *
 * @author 66785270
 */
public class ListaCanciones {
    private ArrayList<Cancion> canciones;

    public ListaCanciones() {
        canciones = new ArrayList();
    }
    /**
     * @return the canciones
     */
    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    /**
     * @param canciones the canciones to set
     */
    public void addCanciones(ArrayList<Cancion> canciones) {
        this.canciones.addAll(canciones);
    }
    public void removeCanciones(ArrayList<Cancion> canciones){
        this.canciones.removeAll(canciones);
    }
}
