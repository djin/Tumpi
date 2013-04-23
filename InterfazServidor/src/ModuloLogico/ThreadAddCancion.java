/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ModuloLogico;

import modelos.Cancion;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 *
 * @author Juan
 */
public class ThreadAddCancion extends Thread {

    private String mrl;
    private AudioMediaPlayerComponent identificador;
    private Cancion cancion;
    private MediaMeta metadata;
    private long dur;
    private MediaPlayer player;
    private boolean acabado;

    public ThreadAddCancion(String path, AudioMediaPlayerComponent _identificador) {
        mrl = path;
        identificador = _identificador;
        dur = 0;
        player = identificador.getMediaPlayer();
        acabado = false;
    }

    public Cancion formatearCancion() {

        start();
        while (!acabado) {
        }
        return cancion;
    }

    public Boolean checkAcabado() {
        return acabado;
    }

    @Override
    public synchronized void run() {
        player.prepareMedia(mrl);
        player.parseMedia();
        metadata = player.getMediaMeta();
        player.mute();
        player.playMedia(mrl);
        try {
            wait(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        player.stop();
        dur = player.getLength();
        cancion = new Cancion(0, metadata.getTitle(), metadata.getAlbum(), metadata.getArtist(), dur, mrl);
        acabado = true;
    }
}
