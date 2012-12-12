package reproductor;

import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
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
    
}
