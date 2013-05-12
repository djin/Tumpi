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
    private ArrayList<ListaPromocionadaListener> listeners;

    public ListaPromocionada() {

        canciones = new ArrayList();
        listeners = new ArrayList();
    }

    public void addListaPromocionadaListener(ListaPromocionadaListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void NuevasCanciones(ListaCanciones lista_promocionada) {
        
        canciones.clear();
        for (Cancion c : lista_promocionada.getCanciones()) {

            canciones.add(new CancionPromocionada(c.getId(), c.getNombre(), c.getDisco(),
                    c.getArtista(), c.getDuracion(), c.getPath()));
        }
        for (ListaPromocionadaListener listener : listeners) {
            listener.OnNewList(canciones);
        }
    }

    public boolean añadirVoto(int _id, boolean tipo) {

        int id, x = 0;
        boolean played;
        for (CancionPromocionada c : canciones) {
            id = c.getId();
            played = c.getReproducida();
            if (id == _id && !played) {
                if (tipo) {
                    c.setVotos(c.getVotos() + 1);
                } else {
                    c.setVotos(c.getVotos() - 1);
                }
                for (ListaPromocionadaListener listener : listeners) {
                    listener.OnSongVoted(x, tipo);
                }
                return true;
            }
            x++;
        }
        return false;
    }

    public CancionPromocionada reproducirCancion() {
        int x = 0, max_votos = 0, cancion_max_votos = -1;
        for (CancionPromocionada c : canciones) {
            if (!c.getReproducida() && c.getVotos() >= max_votos) {

                max_votos = c.getVotos();
                cancion_max_votos = x;
            }
            x++;
        }
        if (cancion_max_votos != -1) {

            canciones.get(cancion_max_votos).setReproducida(true);
            for (ListaPromocionadaListener listener : listeners) {
                listener.OnSongPlayed(cancion_max_votos);
            }
        } 
        else {
            cancion_max_votos = 0;
        }
        return canciones.get(cancion_max_votos);
    }

    @Override
    public String toString() {
        String cadena = "";

        if (!canciones.isEmpty()) {
            for (Cancion c : canciones) {
                cadena = cadena + c + ";";
            }
            cadena = cadena.substring(0, cadena.length() - 1);
        } else {
            cadena = "empty";
        }
        return cadena;
    }
}
