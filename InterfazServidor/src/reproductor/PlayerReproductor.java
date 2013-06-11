package reproductor;

import java.awt.image.BufferedImage;
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
    
    public MediaPlayer getReproductorMediaPlayer() {
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
        cancion = new Cancion(0,parsearDato(metadata.getTitle()), parsearDato(metadata.getAlbum()), 
                parsearDato(metadata.getArtist()), player.getLength(), mrl);
        return cancion;
    }
    
    private String parsearDato (String dato){
        if(dato != null){
            String resultado = dato.replaceAll("[;:\\*\\|]", " ");
            return resultado;
        }
        return null;
    }
}
