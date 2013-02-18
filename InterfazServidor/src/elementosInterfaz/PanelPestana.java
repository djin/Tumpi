/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author 66785320
 */
public class PanelPestana extends JPanel {

    public PanelPestana(String nombreL, GridBagConstraints gbc) {
        JLabel nombre = new JLabel(nombreL + "  ");
        setLayout(new GridBagLayout());
        setOpaque(false);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 4;
        add(nombre, gbc);
//        setPreferredSize(new Dimension(nombre.getWidth() + 40, 16));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
