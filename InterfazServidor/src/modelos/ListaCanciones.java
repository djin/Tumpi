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
    
    public ListaCanciones(){
    
        listeners = new ArrayList();
        canciones = new ArrayList();
    }
    
    public void addListaChangedListener(ListaCancionesChangedListener l){
        if(getListeners() == null) {
            setListeners((ArrayList<ListaCancionesChangedListener>) new ArrayList());
        }
        getListeners().add(l);
    }
    
    public void removeListaChangedListener(ListaCancionesChangedListener l){
        if(getListeners() == null) {
            setListeners((ArrayList<ListaCancionesChangedListener>) new ArrayList());
        }
        getListeners().remove(l);
    } 
    
    protected void fireListaChanged(){
        for(ListaCancionesChangedListener l:getListeners()){
            l.listaChanged(this);
        }
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

