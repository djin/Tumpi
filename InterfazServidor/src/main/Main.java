package main;

import elementosInterfaz.FrameInicial;
import elementosInterfaz.FramePrincipal;
import javax.swing.*;
import reproductor.configVlcj;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

/**
 * Prueba para hacer push desde netbeans
 * @author 66786575
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                final FrameInicial ventanaInicio = new FrameInicial();
                Thread cargarLibrerias = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new NativeDiscovery().discover();

                        ventanaInicio.setVisible(false);
                        ventanaInicio.dispose();
                    }
                });
                cargarLibrerias.start();
            }
        });
    }
}