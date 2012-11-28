/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloLista;

import java.util.ArrayList;

/**
 *
 * @author 66786575
 */
public class ListaCanciones {
    
    private ArrayList<ListaCancionesChangedListener> listeners;
    
    public ListaCanciones(){
    
        listeners = new ArrayList();
    }
    public void addJAlimentoChangedListener(ListaCancionesChangedListener l){
        if(listeners == null) {
            listeners = new ArrayList();
        }
        getListeners().add(l);
    }
    
    public void removeJAlimentoChangedListener(ListaCancionesChangedListener l){
        if(listeners == null) {
            listeners = new ArrayList();
        }
        getListeners().remove(l);
    } 
    
    protected void fireJAlimentoChanged(){
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
}

