/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reproductor;

import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;


/**
 *
 * @author 66785379
 */
public class PlayerReproductor extends configVlcj {
    
    private static AudioMediaPlayerComponent reproductor;
    
    PlayerReproductor(){
        reproductor = new AudioMediaPlayerComponent();
        
    }
    
    public void reproducir(String mrl){
        reproductor.getMediaPlayer().playMedia(mrl);
    }
    
    
    
}
