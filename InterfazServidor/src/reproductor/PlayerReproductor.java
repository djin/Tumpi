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

    public static AudioMediaPlayerComponent reproductor = new AudioMediaPlayerComponent();
    public static AudioMediaPlayerComponent identificador = new AudioMediaPlayerComponent();
    private MediaMeta metadata;
    private MediaPlayer player;
    private Cancion cancion;

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
    
    public Cancion getCancion(String mrl){
        
        player = identificador.getMediaPlayer();
        player.prepareMedia(mrl);
        player.parseMedia();
        metadata = player.getMediaMeta();
        cancion = new Cancion(0, metadata.getTitle(), metadata.getAlbum(), metadata.getArtist(), player.getLength(), mrl);
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
