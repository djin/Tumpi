package reproductor;

import modelos.Cancion;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayer;


/**
 *
 * @author 66785379
 */
public class PlayerReproductor extends configVlcj{
    
    private static AudioMediaPlayerComponent reproductor=new AudioMediaPlayerComponent();
    
    public PlayerReproductor(){
        
    }
    
    public void reproducir(String mrl){
        reproductor.getMediaPlayer().playMedia(mrl);
    }
        
    public static void pausar(){
        reproductor.getMediaPlayer().pause();
    }
    
    public MediaPlayer getMediaPlayer(){
        return reproductor.getMediaPlayer();
    }
    
    public Cancion getCancion(String mrl){
        Cancion cancion;
        MediaMeta metadata;
        String duracion="";
        long dur=0,min=0;
        reproductor.getMediaPlayer().prepareMedia(mrl);
        reproductor.getMediaPlayer().parseMedia();
        metadata = reproductor.getMediaPlayer().getMediaMeta();
//        dur = reproductor.getMediaPlayer().getLength();
//        System.out.println("dentro"+dur);
//        dur = dur/1000;
//        while(dur>59){
//            dur = dur-60;
//            min++;
//        }
//        duracion = Long.toString(min)+":"+ Long.toString(dur);
        cancion = new Cancion(0,metadata.getTitle(), metadata.getAlbum(),metadata.getArtist(),duracion, mrl);
        return cancion; 
    }
    
}
