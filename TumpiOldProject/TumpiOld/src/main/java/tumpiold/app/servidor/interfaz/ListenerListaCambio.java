/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tumpiold.app.servidor.interfaz;

import java.util.ArrayList;
import tumpiold.app.servidor.modelos.ListaCanciones;

/**
 *
 * @author Zellyalgo
 */
public interface ListenerListaCambio {
    public void ListasChanged(ArrayList<ListaCanciones> listas, ArrayList<String> nombres);
}
