package main;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.sun.jna.NativeLibrary;
import elementosInterfaz.FrameInicial;
import elementosInterfaz.FramePrincipal;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.swing.*;

/**
 * Prueba para hacer push desde netbeans
 *
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
//                        NativeDiscovery nd = new NativeDiscovery();
                        String path, pathVlc;
                        try {
                            path = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
//                            pathVlc = path.replace("InterfazServidor.jar", "VLC");
                            pathVlc = path + "VLC";
                            System.out.println(pathVlc);
//                            JOptionPane.showConfirmDialog(null, pathVlc);
//                            pathVlc = path.replace("InterfazServidor.jar", "VLC");
                        } catch (URISyntaxException ex) {
                            System.err.println("Error encontrando el path: "+ex);
                        }
//                        NativeLibrary.addSearchPath("libvlc", pathVlc);
                        NativeLibrary.addSearchPath("libvlc", "VLC/");
                        interfaz = new FramePrincipal();

                        try {

                            Properties props = new Properties();
                            props.put("logoString", "socialDj");
                            HiFiLookAndFeel.setCurrentTheme(props);
                            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
                            SwingUtilities.updateComponentTreeUI(interfaz);

                            interfaz.setUndecorated(true);
                            interfaz.setVisible(true);

                        } catch (UnsupportedLookAndFeelException ex) {
                            System.err.println("Error estableciendo LookAndFeel: "+ex);
                        } catch (ClassNotFoundException ex) {
                            System.err.println("Error estableciendo LookAndFeel: "+ex);
                        } catch (InstantiationException ex) {
                            System.err.println("Error estableciendo LookAndFeel: "+ex);
                        } catch (IllegalAccessException ex) {
                            System.err.println("Error estableciendo LookAndFeel: "+ex);
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