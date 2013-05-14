/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author 66786575
 */
public interface ConjuntoListasListener {
    public void onNewList(String nombre);
    public void onRemoveList(int index);
    public void onAddSong(int index, Cancion cancion);
    public void onRemoveSongs(int index, int[] filas, boolean vacio);
    public void onUpdatedLength(int index, int fila, String duracion);
}
