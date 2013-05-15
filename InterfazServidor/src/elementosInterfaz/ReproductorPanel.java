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
    public static JPanel panelLogo;
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
        
        panelCaratula = new PanelCaratula("icons/1.jpg");
        this.add(panelCaratula);
        
        panelBotones = new JPanel(fl);
        playIcon = Imagenes.getImagen("icons/play.png");
        pauseIcon = Imagenes.getImagen("icons/pause.png");
        ImageIcon stopIcon = Imagenes.getImagen("icons/stop.png");
        ImageIcon nextIcon = Imagenes.getImagen("icons/next.png");
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
        
        playPress = Imagenes.getImagen("icons/play2.png");
        pausePress = Imagenes.getImagen("icons/pause2.png");
        ImageIcon stopPress = Imagenes.getImagen("icons/stop2.png");
        ImageIcon nextPress = Imagenes.getImagen("icons/next2.png");
        
        play.setPressedIcon(playPress);
        stop.setPressedIcon(stopPress);
        next.setPressedIcon(nextPress);
        
        play.setContentAreaFilled(false);
        stop.setContentAreaFilled(false);
        next.setContentAreaFilled(false);
        
        play.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(player.getReproductorMediaPlayer().isPlayable()){
                    if(player.getReproductorMediaPlayer().isPlaying()){
                       play.setIcon(playIcon);
                       play.setPressedIcon(playPress);
                    } else {
                        play.setIcon(pauseIcon);
                        play.setPressedIcon(pausePress);
                    }
                    PlayerReproductor.pausar();
                }
                else if(listas_manager.playNext()){
                    try{
                        panelCaratula.cambiarCaratula(PlayerReproductor.getImage());
                    }catch(Exception ex){
                        System.err.println("No se encontro imagen: "+ex);
                    }
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
                        System.err.println("No se encontro imagen: "+ex);
                    }
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
        
        panelBotones.add(play);
        panelBotones.add(next);
        this.add(panelBotones);
        
        panelNombres = new JPanel();
        song = new JLabel("Canción");
        song.setFont(new Font("", Font.BOLD, 14));
        artist = new JLabel("Artista");
        artist.setFont(new Font("", Font.BOLD, 10));
        panelNombres.setPreferredSize(new Dimension(300,55));
        GridLayout gl = new GridLayout(2, 1, 0,-12);
        panelNombres.setLayout(gl);
        panelNombres.add(song);
        panelNombres.add(artist);
        
        this.add(panelNombres);
        
        
        panelLogo = new PanelLogo();
        panelLogo.setPreferredSize(new Dimension(800,70));
        this.add(panelLogo);
    }
    
}
