package main;

import elementosInterfaz.FrameInicial;
import elementosInterfaz.FramePrincipal;
import java.util.logging.Level;
import java.util.logging.Logger;
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

            private FramePrincipal interfaz;

            @Override
            public void run() {
                final FrameInicial ventanaInicio = new FrameInicial();
                Thread cargarLibrerias = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        new NativeDiscovery().discover();
                        
                        interfaz = new FramePrincipal();

                        try {
                            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
                            SwingUtilities.updateComponentTreeUI(interfaz);
                            interfaz.setUndecorated(true);
                            interfaz.setVisible(true);

                        } catch (UnsupportedLookAndFeelException ex) {
                            ex.printStackTrace();
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (InstantiationException ex) {
                            ex.printStackTrace();
                        } catch (IllegalAccessException ex) {
                            ex.printStackTrace();
                        }
                        interfaz.addWindowListener(interfaz);
                        
                        ventanaInicio.setVisible(false);
                        ventanaInicio.dispose();
                    }
                });
                cargarLibrerias.start();
            }
        });
    }
}