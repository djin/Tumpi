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
public class ListaCanciones {

    private ArrayList<Cancion> canciones;
    public String nombre_lista;

    public ListaCanciones(String _nombre_lista) {
        
        nombre_lista = _nombre_lista;
        canciones = new ArrayList();
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

    public int getMaxId() {
        int max_id = 0;
        for (Cancion c : canciones) {
            if (c.getId() > max_id) {
                max_id = c.getId();
            }
        }
        return max_id;
    }
    /**
     * @return the canciones
     */
    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    /**
     * @param canciones the canciones to set
     */
    public void setCanciones(ArrayList<Cancion> canciones) {
        this.canciones = canciones;
    }
}
