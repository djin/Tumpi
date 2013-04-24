/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.datos;

import java.util.ArrayList;

/**
 * 
 * @author Zellyago
 */
public class RunnableGuardado implements Runnable{
    
    ArrayList<ListaCanciones> listas;
    GuardarListas parent;

    public RunnableGuardado(ArrayList<ListaCanciones> listas,ArrayList<String> nombresLista, GuardarListas _parent) {
        this.listas = listas;
        parent = _parent;
    }

    public void run() {
        
        parent.terminado = true;
    }
    
}
