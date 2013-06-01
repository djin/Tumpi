/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tumpi.servidor.interfaces;

import java.util.ArrayList;
import app.tumpi.servidor.modelo.datos.ListaCanciones;

/**
 *
 * @author Zellyalgo
 */
public interface ListenerListaCambio {
    public void ListasChanged(ArrayList<ListaCanciones> listas, ArrayList<String> nombres);
}
