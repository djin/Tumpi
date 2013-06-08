/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tumpi.servidor.modelo.datos;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author 66785270
 */
public class ListaPromocionada {

    private ArrayList<CancionPromocionada> canciones;

    public ListaPromocionada() {
        canciones = new ArrayList();
    }

    public ListaPromocionada(ListaCanciones lista_promocionada) {
        canciones = new ArrayList();
        int cont = 0;
        for (Cancion c : lista_promocionada.getCanciones()) {
            cont++;
            canciones.add(new CancionPromocionada(cont, c.nombreCancion, c.nombreAlbum, c.album_id, c.nombreAutor, c.duracion, c.path));
        }
    }

    /**
     * @return the canciones
     */
    public ArrayList<CancionPromocionada> getCanciones() {
        return canciones;
    }

    public CancionPromocionada getCancionById(int id) {
        for (CancionPromocionada cancion : canciones) {
            if (cancion.id == id) {
                return cancion;
            }
        }
        return null;
    }

    public void removeCanciones(ArrayList<CancionPromocionada> canciones) {
        this.canciones.removeAll(canciones);
    }

    public CancionPromocionada getMaxVoto() {
        int i = 0;
        ArrayList<CancionPromocionada> maxCancion = new ArrayList<CancionPromocionada>();
        for (CancionPromocionada c : canciones) {
            if (i <= c.getVotos()) {
                i = c.getVotos();
            }
        }
        for (CancionPromocionada c : canciones) {
            if (i == c.getVotos() && c.getReproducida() == 0) {
                maxCancion.add(c);
            }
        }
        if (maxCancion.size() > 1) {
            Random r = new Random();
            i = r.nextInt(maxCancion.size());
        } else {
            i = 0;
        }
        if(maxCancion.isEmpty()){
            for(CancionPromocionada c : canciones){
                c.setVotos(0);
                c.setReproducida(0);
            }
            return null;
        }
        maxCancion.get(i).setReproducida(1);
        maxCancion.get(i).setVotos(-1);
        return maxCancion.get(i);
    }

    @Override
    public String toString() {
        String cadena = "";

        if (!canciones.isEmpty()) {
            for (CancionPromocionada c : canciones) {
                cadena = cadena + c + ";";
            }
            cadena = cadena.substring(0, cadena.length() - 1);
        } else {
            cadena = "empty";
        }
        return cadena;
    }
}