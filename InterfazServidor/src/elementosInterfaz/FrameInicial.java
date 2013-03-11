/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import javax.swing.JFrame;
/**
 *
 * @author 66785320
 */
public class FrameInicial extends JFrame {

    public FrameInicial() {
        setSize(400, 200);
        getContentPane().add(new PanelInicial());
        setLocation(500, 300);
        setUndecorated(true);
        setVisible(true);
    }
}