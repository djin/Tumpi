package reproductor;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventAdapter;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

/**
 * Example showing how to combine a media list player with an embedded media
 * player.
 */
public class Reproductor extends configVlcj {

  public static void main(String[] args) throws Exception {
    MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
    
    EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
    
    final MediaListPlayer mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();
    
    mediaListPlayer.addMediaListPlayerEventListener(new MediaListPlayerEventAdapter() {
      @Override
      public void nextItem(MediaListPlayer mediaListPlayer, libvlc_media_t item, String itemMrl) {
        System.out.println("nextItem()");
      }
    });
    
    mediaListPlayer.setMediaPlayer(mediaPlayer); // <--- Important, associate the media player with the media list player
    
    JButton siguiente = new JButton("Siguiente");
    siguiente.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mediaListPlayer.playNext();
            }
        });
    
    JButton pausa = new JButton("Pausa");
    pausa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mediaListPlayer.pause();
            }
        });
    
    JPanel cp = new JPanel();
    cp.setLayout(new BorderLayout());
    cp.add(pausa, BorderLayout.CENTER);
    cp.add(siguiente, BorderLayout.SOUTH);
    
    
    JFrame f = new JFrame("vlcj embedded media list player test");
    f.setContentPane(cp);
    f.setSize(200, 100);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
    
    MediaList mediaList = mediaPlayerFactory.newMediaList();
    mediaList.addMedia("1.mp3");
    mediaList.addMedia("2.mp3");
    
    mediaListPlayer.setMediaList(mediaList);
    mediaListPlayer.setMode(MediaListPlayerMode.LOOP);

    mediaListPlayer.play();

//    mediaList.release();
//    mediaListPlayer.release();
//    mediaPlayer.release();
//    mediaPlayerFactory.release();
  }
}