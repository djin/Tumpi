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
public interface ConjuntoListasListener {
    public void onNewList(ArrayList <CancionPromocionada> canciones);
    public void onAddSongs(int fila, boolean tipo);
    public void onRemoveSongs(int fila);
}
