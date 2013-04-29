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
    
    @Override
    public String toString() {
        String cadena = "";

        if (!canciones.isEmpty()) {
            for (Cancion c : canciones) {
                cadena = cadena + c + ";";
            }
            cadena = cadena.substring(0, cadena.length() - 1);
        }
        else{
            cadena = "empty";
        }
        return cadena;
    }

    /**
     * @return the canciones
     */
    public ArrayList<CancionPromocionada> getCanciones() {
        return canciones;
    }
}
