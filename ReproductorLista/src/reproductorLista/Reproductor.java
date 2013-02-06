package reproductorLista;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

    private static MediaPlayerFactory mediaPlayerFactory;
    private static EmbeddedMediaPlayer mediaPlayer;
    private static MediaListPlayer mediaListPlayer;
    private static JLabel nombreCancion;
    private static JLabel artistaCancion;
    private static boolean pausaReproducir = false;

    public static void main(String[] args) throws Exception {

        mediaPlayerFactory = new MediaPlayerFactory();

        mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();

        mediaListPlayer = mediaPlayerFactory.newMediaListPlayer();

        mediaListPlayer.addMediaListPlayerEventListener(new MediaListPlayerEventAdapter() {

            @Override
            public void nextItem(MediaListPlayer mediaListPlayer, libvlc_media_t item, String itemMrl) {
                nombreCancion.setText(mediaPlayer.getMediaMeta().getTitle());
            }
            
            @Override
            public void played(MediaListPlayer mediaListPlayer){
                nombreCancion.setText(mediaPlayer.getMediaMeta().getTitle());
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

        final JButton pausa = new JButton("Pausa");
        pausa.setPreferredSize(new Dimension(110,26));
              
        pausa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mediaListPlayer.pause();
                if (pausaReproducir == false) {
                    pausa.setText("Reproducir");
                    pausaReproducir=true;
                }else{
                    pausa.setText("Pausa");
                    pausaReproducir=false;
                }
            }
        });

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel();
        nombreCancion = new JLabel("-");
        artistaCancion = new JLabel("-");

        panelCentral.add(nombreCancion);
        panelCentral.add(artistaCancion);

        JPanel panelBotones = new JPanel();
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        panelBotones.add(pausa);
        panelBotones.add(siguiente);

        JFrame ventana = new JFrame("Reproductor");
        ventana.setContentPane(panelPrincipal);
        ventana.setSize(250, 150);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);

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