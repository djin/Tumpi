package musica;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * The smallest amount of code to play an audio file.
 * <p>
 * Audio is simple since there's no video surface to worry about (unless you
 * want the audio visualisations).
 */
public class Musica {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Specify an MRL to play");
            System.exit(1);
        }
        

        NativeLibrary.addSearchPath(
                RuntimeUtil.getLibVlcLibraryName(), "C:/Program Files/VideoLAN/VLC");

        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

        AudioMediaPlayerComponent reproductor = new AudioMediaPlayerComponent() {
            
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                System.exit(0);
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                System.out.println("Failed to play media");
                System.exit(1);
            }
        };
        reproductor.getMediaPlayer().playMedia(args[0]);
        Thread.currentThread().join();

    }
}