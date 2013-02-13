package elementosInterfaz;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author 66785320
 */
public class MyMouseListener implements MouseInputListener {

    JButton btn;
    private boolean bool;

    public MyMouseListener(JButton boton, boolean bool) {
        btn = boton;
        this.bool = bool;
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
        if (bool) {
            btn.setIcon(new ImageIcon("icons/cerrar2.png"));
        } else {
            btn.setIcon(new ImageIcon("icons/minimizar2.png"));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (bool) {
            btn.setIcon(new ImageIcon("icons/cerrar.png"));
        } else {
            btn.setIcon(new ImageIcon("icons/minimizar.png"));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
