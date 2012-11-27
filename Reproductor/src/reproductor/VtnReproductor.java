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
    private static JButton siguiente;
    private static JButton pausa;
    private static JPanel panelPrincipal;
    private static JPanel panelCentral;
    private static JPanel panelBotones;
    private static boolean pausaReproducir = false;

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

        siguiente = new JButton("Siguiente");
        siguiente.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //mediaListPlayer.playNext();
            }
        });

        pausa = new JButton("Pausa");
        pausa.setPreferredSize(new Dimension(110, 26));

        pausa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //mediaListPlayer.pause();
                if (pausaReproducir == false) {
                    pausa.setText("Reproducir");
                    pausaReproducir = true;
                } else {
                    pausa.setText("Pausa");
                    pausaReproducir = false;
                }
            }
        });

        panelBotones = new JPanel();
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        panelBotones.add(pausa);
        panelBotones.add(siguiente);

        this.add(panelPrincipal);

    }
}
