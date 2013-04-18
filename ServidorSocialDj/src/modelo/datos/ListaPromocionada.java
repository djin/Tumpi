/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.datos;

import java.util.ArrayList;

/**
 *
 * @author 66785270
 */
public class ListaPromocionada {
    
    private ArrayList<CancionPromocionada> canciones; 
    
    public ListaPromocionada(){
        canciones = new ArrayList();
    }
    
    public ListaPromocionada(ListaCanciones lista_promocionada){
        
         canciones = new ArrayList();
         for(Cancion c : lista_promocionada.getCanciones()){
             
             canciones.add(new CancionPromocionada(c.getId(),c.getNombre(),c.getDisco(),
                     c.getArtista(),c.getDuracion(),c.getPath()));
         }
        
    
    }

    /**
     * @return the canciones
     */
    public ArrayList<CancionPromocionada> getCanciones() {
        return canciones;
    }
    public void removeCanciones(ArrayList<CancionPromocionada> canciones){
        this.canciones.removeAll(canciones);
    }
}