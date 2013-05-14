/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.ArrayList;

/**
 *
 * @author Juan
 */
public class ConjuntoListas {

    private ArrayList<ListaCanciones> listas;
    private ArrayList<ConjuntoListasListener> listeners;

    public ConjuntoListas() {
        listas = new ArrayList();
        listeners = new ArrayList();
    }

    public void addConjuntoListasListener(ConjuntoListasListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void inicializar(ArrayList _listas) {
        listas = _listas;
    }

    public void addLista(String nombre) {
        
        listas.add(new ListaCanciones(nombre));
        for (ConjuntoListasListener listener : listeners) {
            listener.onNewList(nombre);
        }
    }

    public void removeLista(int index) {
        
        listas.remove(index);
        for (ConjuntoListasListener listener : listeners) {
            listener.onRemoveList(index);
        }
    }
    
    public void addCancion(Cancion cancion, int index){
        
        listas.get(index).addCancion(cancion);
        for (ConjuntoListasListener listener : listeners) {
            listener.onAddSong(index, cancion);
        }
    }
    public void removeCanciones(int[] filas, int index){
        
        listas.get(index).removeCanciones(filas);
        boolean vacio = listas.get(index).getCanciones().isEmpty();
        for (ConjuntoListasListener listener : listeners) {
            listener.onRemoveSongs(index, filas, vacio);
        }
    }
    
    public void setDuracion(long duracion, int index, int fila) {
        
        listas.get(index).getCanciones().get(fila).setDuracion(duracion);
        String duracion_formateada = formatearDuracion(duracion);
        for(ConjuntoListasListener listener : listeners){
            listener.onUpdatedLength(index, fila, duracion_formateada);
        }
    }
    
    public ListaCanciones getLista(int index){
        return listas.get(index);
    }
    
    public boolean estaVacia(){
        if(listas.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    
    public String formatearDuracion(long duracion) {

        String minutos = "" + duracion / 60000;
        String segundos = "" + ((duracion - ((duracion / 60000) * 60000)) / 1000);
        if (segundos.length() == 1) {
            segundos = "0" + segundos;
        }
        String duracionFormateada = "" + minutos + ":" + segundos + " ";
        return duracionFormateada;
    }
}
