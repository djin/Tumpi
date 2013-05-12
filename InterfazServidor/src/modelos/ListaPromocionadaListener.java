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
public interface ListaPromocionadaListener {
    public void OnNewList(ArrayList <CancionPromocionada> canciones);
    public void OnSongVoted(int fila, boolean tipo);
    public void OnSongPlayed(int fila);
}

