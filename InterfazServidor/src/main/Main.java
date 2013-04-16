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
                        String path, pathVlc = null;
                        try {
                            path = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                            pathVlc = path.replace("InterfazServidor.jar", "VLC");
//                            pathVlc = path.replace("InterfazServidor.jar", "VLC");
                            pathVlc = path + "VLC";
                            System.out.println(pathVlc);
//                            JOptionPane.showConfirmDialog(null, pathVlc);
                        } catch (URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                        NativeLibrary.addSearchPath("libvlc", pathVlc);
//                        NativeLibrary.addSearchPath("libvlc", "/VLC");
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