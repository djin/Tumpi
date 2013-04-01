/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author
 */
public class PanelLogo extends JPanel {

    private BufferedImage image;

    PanelLogo() {
        image = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB);
        image.createGraphics().drawImage(Imagenes.getImagen("icons/socialDj2.png").getImage(), 0, 0, 250, 60, null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 550, 10, null);
        setOpaque(false);
    }
}
