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

    private int n = 0;
    private Dimension tamanio;
    private boolean direccion = false;

    public PanelInicial() {

        Timer time = new Timer(2, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (n <= tamanio.width && !direccion) {
                    n++;
                    if(n==tamanio.width){
                        direccion = true;
                    }
                } else {
                    n--;
                    if(n==0){
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
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, tamanio.width - (tamanio.width - n), tamanio.height);
            g.drawImage(imagenFondo.getImage(), 50, 65, tamanio.width - 110, tamanio.height - 130, null);
            setOpaque(false);
        } else {
            setOpaque(true);
        }
    }
}
