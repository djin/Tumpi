package reproductor;

import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;
import modelos.Cancion;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 *
 * @author 66785379
 */
public class PlayerReproductor {

    private static AudioMediaPlayerComponent reproductor = new AudioMediaPlayerComponent();
    private static AudioMediaPlayerComponent identificador = new AudioMediaPlayerComponent();

    public PlayerReproductor() {
    }

    public boolean reproducir(String mrl) {
        return reproductor.getMediaPlayer().playMedia(mrl);
    }

    public static void pausar() {
        reproductor.getMediaPlayer().pause();
    }

    public MediaPlayer getMediaPlayer() {
        return reproductor.getMediaPlayer();
    }

    public static BufferedImage getImage() {
        identificador.getMediaPlayer().prepareMedia(reproductor.getMediaPlayer().mrl());
        identificador.getMediaPlayer().parseMedia();
        return identificador.getMediaPlayer().getMediaMeta().getArtwork();
    }

    public synchronized Cancion getCancion(String mrl) {

        Cancion cancion;
        MediaMeta metadata;
        String duracion = "";
        long dur = 0;

    //    SwingUtilities.invokeLater(new Runnable() {
    //        @Override
    //        public void run() {
                MediaPlayer player = identificador.getMediaPlayer();
                player.prepareMedia(mrl);
                player.parseMedia();
                metadata = player.getMediaMeta();

                player.mute();
                //futuro thread
                player.playMedia(mrl);
                try {
                    wait(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                dur = player.getLength();

                cancion = new Cancion(0, metadata.getTitle(), metadata.getAlbum(), metadata.getArtist(), dur, mrl);
                player.stop();
    //        }});
        return cancion;
        }
    

    public String formatearDuracion(long duracion) {

        String minutos = "" + duracion / 60000;
        String segundos = "" + ((duracion - ((duracion / 60000) * 60000)) / 1000);
        if (segundos.length() == 1) {
            segundos = "0" + segundos;
        }
        String duracionFormateada = "" + minutos + ":" + segundos + " ";
        return duracionFormateada;
    }
}
