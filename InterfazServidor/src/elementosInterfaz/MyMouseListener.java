/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author 66785320
 */
public class MyMouseListener implements MouseInputListener {

    JButton btn;

    public MyMouseListener(JButton boton) {
        btn = boton;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        btn.setBackground(Color.DARK_GRAY);
        btn.setContentAreaFilled(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        btn.setBackground(null);
        btn.setContentAreaFilled(false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
