/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author 66785320
 */
public class PanelInicial extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension tamanio = getSize();
        ImageIcon imagenFondo = new ImageIcon("socialDj2.png");
        if (imagenFondo != null) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, tamanio.width, tamanio.height);
            g.drawImage(imagenFondo.getImage(), 50, 65, tamanio.width-110, tamanio.height-130, null);
            setOpaque(false);
        } else {
            setOpaque(true);
        }
    }
}
