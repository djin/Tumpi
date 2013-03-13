/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author 66785320
 */
public class PanelInicial extends JPanel {

    private int w = 0;
    private Dimension tamanio;
    private boolean direccion = false;

    public PanelInicial() {
        tamanio = new Dimension(400, 200);
        Timer time = new Timer(4, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (w <= tamanio.width && !direccion) {
                    w++;
                    if (w == tamanio.width) {
                        direccion = true;
                    }
                } else {
                    w--;
                    if (w == 0) {
                        direccion = false;
                    }
                }
                repaint();
            }
        });
        time.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        tamanio = getSize();
        ImageIcon imagenFondo = Imagenes.getImagen("icons/socialDj2.png");
        if (imagenFondo != null) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, tamanio.width, tamanio.height);
            g.setColor(new Color(107,107,107));
            g.fillRect(0, 0, tamanio.width - (tamanio.width - w), tamanio.height);
            g.drawImage(imagenFondo.getImage(), 50, 65, tamanio.width - 110, tamanio.height - 130, null);
            setOpaque(false);
        } else {
            setOpaque(true);
        }
    }
}