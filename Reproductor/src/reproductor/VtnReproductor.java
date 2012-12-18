/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reproductor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author 66785379
 */
public class VtnReproductor extends JFrame {

    private static JLabel nombreCancion;
    private static JLabel artistaCancion;
    private static JButton pausa;
    private static JButton siguiente;
    private static JPanel panelPrincipal;
    private static JPanel panelCentral;
    private static JPanel panelBotones;
    private static boolean pausaReproducir = false;
    private static PlayerReproductor reproductor = new PlayerReproductor();

    VtnReproductor() {
        this.setSize(250, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        panelCentral = new JPanel();
        nombreCancion = new JLabel("-");
        artistaCancion = new JLabel("-");

        panelCentral.add(nombreCancion);
        panelCentral.add(artistaCancion);


        pausa = new JButton();
        pausa.setPreferredSize(new Dimension(40, 40));

        pausa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                reproductor.pausa();
            }
        });
        
        siguiente = new JButton();
        siguiente.setPreferredSize(new Dimension(40, 40));

        siguiente.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
            
        });

        panelBotones = new JPanel();
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        panelBotones.add(pausa);
        
        this.add(panelPrincipal);

    }
}
