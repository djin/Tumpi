/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
/**
 *
 * @author 66785320
 */
public class FrameInicial extends JFrame {

    public FrameInicial() {
        setSize(400, 200);
        getContentPane().add(new PanelInicial());
        Image icon = new ImageIcon("icons/socialDJ.png").getImage();
        setIconImage(icon);
        setTitle("socialDj Loader");
        setLocation(500, 300);
        setUndecorated(true);
        setVisible(true);
    }
}