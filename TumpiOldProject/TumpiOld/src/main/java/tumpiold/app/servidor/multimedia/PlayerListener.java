/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tumpiold.app.servidor.multimedia;
import android.media.MediaPlayer;
import android.media.MediaPlayer.*;

/**
 *
 * @author 66785270
 */
public interface PlayerListener extends OnInfoListener,OnErrorListener,OnPreparedListener,OnCompletionListener{

    public void onPrepared(MediaPlayer mp);
    public void onCompletion(MediaPlayer mp);
    public boolean onInfo(MediaPlayer mp, int what, int extra);
    public boolean onError(MediaPlayer mp, int what, int extra);
    
}
