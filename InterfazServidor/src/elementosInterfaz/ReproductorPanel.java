package elementosInterfaz;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modelos.ListasCancionesManager;
import reproductor.PlayerReproductor;

/**
 *
 * @author 66785379
 */
public class ReproductorPanel extends JPanel {

    public static JButton play;
    public static JButton next;
    public static JButton stop;
    private ImageIcon playIcon;
    private ImageIcon playPress;
    private ImageIcon pauseIcon;
    private ImageIcon pausePress;
    public static JPanel panelBotones;
    public static JPanel panelNombres;
    public static JLabel song;
    public static JLabel artist;
    public static PanelCaratula panelCaratula;
    PlayerReproductor player=new PlayerReproductor();
    public ListasCancionesManager listas_manager;
    
    public ReproductorPanel(ListasCancionesManager lista_manager) {
        
        this.listas_manager = lista_manager;
        
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        this.setLayout(fl);
        this.setPreferredSize(new Dimension(500,80));
        
        panelCaratula = new PanelCaratula("1.jpg");
        this.add(panelCaratula);
        //panelCaratula.cambiarCaratula("1.jpg");
        
        panelBotones = new JPanel(fl);
        playIcon = new ImageIcon("icons/play.png");
        pauseIcon = new ImageIcon("icons/pause.png");
        ImageIcon stopIcon = new ImageIcon("icons/stop.png");
        ImageIcon nextIcon = new ImageIcon("icons/next.png");
        play = new JButton(playIcon);
        stop = new JButton(stopIcon);
        next = new JButton(nextIcon);
        
        Dimension tamanoBoton = new Dimension (53,53);
        
        play.setPreferredSize(tamanoBoton);
        stop.setPreferredSize(tamanoBoton);
        next.setPreferredSize(tamanoBoton);
        
        play.setBackground(null);
        stop.setBackground(null);
        next.setBackground(null);
        
        play.setBorderPainted(false);
        stop.setBorderPainted(false);
        next.setBorderPainted(false);
        
        play.setFocusPainted(false);
        stop.setFocusPainted(false);
        next.setFocusPainted(false);
        
        play.setToolTipText("Reproducir");
        stop.setToolTipText("Parar");
        next.setToolTipText("Siguiente");
        
        playPress = new ImageIcon("icons/play2.png");
        pausePress = new ImageIcon("icons/pause2.png");
        ImageIcon stopPress = new ImageIcon("icons/stop2.png");
        ImageIcon nextPress = new ImageIcon("icons/next2.png");
        
        play.setPressedIcon(playPress);
        stop.setPressedIcon(stopPress);
        next.setPressedIcon(nextPress);
        
        play.setContentAreaFilled(false);
        stop.setContentAreaFilled(false);
        next.setContentAreaFilled(false);
        
        play.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(player.getMediaPlayer().isPlayable()){
                    if(player.getMediaPlayer().isPlaying()){
                       play.setToolTipText("Reproducir");
                       play.setIcon(playIcon);
                       play.setPressedIcon(playPress);
                    } else {
                        play.setToolTipText("Pausar");
                        play.setIcon(pauseIcon);
                        play.setPressedIcon(pausePress);
                    }
                    PlayerReproductor.pausar();
                }
                else if(listas_manager.playNext()){
                    try{
                        panelCaratula.cambiarCaratula(PlayerReproductor.getImage());
                    }catch(Exception ex){
                        FramePrincipal.log("No se encontro imagen");
                    }
                    play.setToolTipText("Pausar");
                    play.setIcon(pauseIcon);
                    play.setPressedIcon(pausePress);
                }
            }
        });
        
        next.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(listas_manager.playNext()){
                    try{
                        panelCaratula.cambiarCaratula(PlayerReproductor.getImage());
                    }catch(Exception ex){
                        FramePrincipal.log("No se encontro imagen");
                    }
                    play.setToolTipText("Pausar");
                    play.setIcon(pauseIcon);
                    play.setPressedIcon(pausePress);
                }
            }
        });
        
        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        
        //panelBotones.add(stop);
        panelBotones.add(play);
        panelBotones.add(next);
        this.add(panelBotones);
        
        panelNombres = new JPanel();
        song = new JLabel("Nombre de la canción asd");
        song.setFont(new Font("", Font.BOLD, 14));
        artist = new JLabel("Artista");
        artist.setFont(new Font("", Font.BOLD, 10));
        panelNombres.setPreferredSize(new Dimension(300,55));
        GridLayout gl = new GridLayout(2, 1, 0,-12);
        panelNombres.setLayout(gl);
        panelNombres.add(song);
        panelNombres.add(artist);
        
        this.add(panelNombres);
        
    }
    
    
}
