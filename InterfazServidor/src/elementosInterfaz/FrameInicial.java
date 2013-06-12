package elementosInterfaz;

import java.awt.Image;
import javax.swing.JFrame;
/**
 *
 * @author 66785320
 */
public class FrameInicial extends JFrame {

    public FrameInicial() {
        setSize(400, 200);
        setUndecorated(true);
        Image icon = Imagenes.getImagen("icons/socialDJ.png").getImage();
        setIconImage(icon);
        setTitle("Tumpi Loader");
        setLocation(500, 300);
        getContentPane().add(new PanelInicial());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}