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
    public void onNewListPromoted(ArrayList <CancionPromocionada> canciones);
    public void onSongVoted(int fila, boolean tipo);
    public void onSongPlayed(int fila);
}

