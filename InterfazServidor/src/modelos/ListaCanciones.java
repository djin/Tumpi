/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.ArrayList;

/**
 *
 * @author 66786575
 */
public class ListaCanciones {

    private ArrayList<ListaCancionesChangedListener> listeners;
    private ArrayList<Cancion> canciones;
    public String nombreLista;

    public ListaCanciones() {
        
        nombreLista = null;
        listeners = new ArrayList();
        canciones = new ArrayList();
    }

    public void addListaChangedListener(ListaCancionesChangedListener l) {
        if (getListeners() == null) {
            setListeners((ArrayList<ListaCancionesChangedListener>) new ArrayList());
        }
        getListeners().add(l);
    }

    public void removeListaChangedListener(ListaCancionesChangedListener l) {
        if (getListeners() == null) {
            setListeners((ArrayList<ListaCancionesChangedListener>) new ArrayList());
        }
        getListeners().remove(l);
    }

    protected void fireListaChanged() {
        for (ListaCancionesChangedListener l : getListeners()) {
            l.listaChanged(this);
        }
    }

    @Override
    public String toString() {
        String cadena = "";

        if (!canciones.isEmpty()) {
            for (Cancion c : canciones) {
                cadena = cadena + c + ";";
            }
            cadena = cadena.substring(0, cadena.length() - 1);
        }
        else{
            cadena = "empty";
        }
        return cadena;
    }

    public int getMaxId() {
        int max_id = 0;
        for (Cancion c : canciones) {
            if (c.getId() > max_id) {
                max_id = c.getId();
            }
        }
        return max_id;
    }

    /**
     * @return the listeners
     */
    public ArrayList<ListaCancionesChangedListener> getListeners() {
        return listeners;
    }

    /**
     * @param listeners the listeners to set
     */
    public void setListeners(ArrayList<ListaCancionesChangedListener> listeners) {
        this.listeners = listeners;
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
    public void setCanciones(ArrayList<Cancion> canciones) {
        this.canciones = canciones;
    }
}
