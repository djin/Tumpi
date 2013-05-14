/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reproductor;

import modelos.Cancion;
import modelos.ConjuntoListas;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;

/**
 *
 * @author Juan
 */
public class ThreadGetDuracion extends Thread {

    private AudioMediaPlayerComponent identificador;
    private ConjuntoListas listas_canciones;
    private Cancion cancion;
    private Long dur;
    int index, fila;

    public ThreadGetDuracion(ConjuntoListas _listas_canciones, Cancion _cancion, int _index, int _fila) {

        cancion = _cancion;
        index = _index;
        listas_canciones = _listas_canciones;
        fila = _fila;
        identificador = new AudioMediaPlayerComponent();
    }

    @Override
    public synchronized void run() {

        identificador.getMediaPlayer().prepareMedia(cancion.getPath());
        identificador.getMediaPlayer().parseMedia();
        identificador.getMediaPlayer().setVolume(0);
        identificador.getMediaPlayer().playMedia(cancion.getPath());
        try {
            wait(150);
        } catch (InterruptedException ex) {
            System.err.println("Error en el thread que obtiene las duraciones" + ex);
        }
        dur = identificador.getMediaPlayer().getLength();
        identificador.getMediaPlayer().stop();
        listas_canciones.setDuracion(dur, index, fila);
    }
}
