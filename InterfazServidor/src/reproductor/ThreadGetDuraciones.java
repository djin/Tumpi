/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reproductor;

import java.util.ArrayList;
import modelos.Cancion;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 *
 * @author Juan
 */
public class ThreadGetDuraciones extends Thread {

    private ArrayList<Cancion> canciones;
    private MediaPlayer player;
    private Long dur;

    public ThreadGetDuraciones(ArrayList _canciones, MediaPlayer _player) {

        canciones = _canciones;
        player = _player;
    }

    @Override
    public synchronized void run() {

        for (Cancion c : canciones) {
            player.mute();
            player.playMedia(c.getPath());
            try {
                wait(150);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            player.stop();
            dur = player.getLength();
            c.setDuracion(dur);
        }
    }
}
