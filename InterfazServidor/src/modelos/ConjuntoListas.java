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
    
    ArrayList<ListaCanciones> listas;
    
    public ConjuntoListas(){
        listas = new ArrayList();
    }
    
    public void inicializar(ArrayList _listas){
        listas = _listas;
    }
    
    public void addLista(String nombre){
        listas.add(new ListaCanciones(nombre));
    }
    
    public void removeLista(int index){
        listas.remove(index);
    }
}
