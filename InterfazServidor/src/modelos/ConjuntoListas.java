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
        for (ConjuntoListasListener listener : listeners) {
            listener.onInitialize(listas);
        }
    }

    public void addLista(String nombre) {

        getListas().add(new ListaCanciones(nombre));
        for (ConjuntoListasListener listener : listeners) {
            listener.onNewList(nombre);
        }
    }

    public void removeLista(int index) {

        getListas().remove(index);
        for (ConjuntoListasListener listener : listeners) {
            listener.onRemoveList(index);
        }
    }

    public void addCancion(Cancion cancion, int index) {

        getListas().get(index).addCancion(cancion);
        for (ConjuntoListasListener listener : listeners) {
            listener.onAddSong(index, cancion);
        }
    }

    public void removeCanciones(int[] filas, int index) {

        getListas().get(index).removeCanciones(filas);
        boolean vacio = getListas().get(index).getCanciones().isEmpty();
        for (ConjuntoListasListener listener : listeners) {
            listener.onRemoveSongs(index, filas, vacio);
        }
    }

    public void setDuracion(long duracion, int index, int fila) {

        getListas().get(index).getCanciones().get(fila).setDuracion(duracion);
        String duracion_formateada = formatearDuracion(duracion);
        getListas().get(index).getCanciones().get(fila).setDuracion_formateada(duracion_formateada);
        for (ConjuntoListasListener listener : listeners) {
            listener.onUpdatedLength(index, fila, duracion_formateada);
        }
    }

    public ListaCanciones getLista(int index) {
        return getListas().get(index);
    }

    public boolean estaVacia() {
        if (getListas().isEmpty()) {
            return true;
        } else {
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

    public ArrayList<ListaCanciones> getListas() {
        return listas;
    }
}
