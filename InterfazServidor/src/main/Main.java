package main;

import elementosInterfaz.FramePrincipal;
import javax.swing.*;
import reproductor.configVlcj;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

/**
 * Prueba para hacer push desde netbeans
 * @author 66786575
 */
public class Main {

    private static FramePrincipal interfaz;
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JDialog ventanaInicio = new JDialog();
                ventanaInicio.setSize(200, 200);
                ventanaInicio.setLocation(500, 300);
                JPanel panel = (JPanel) ventanaInicio.getContentPane();
                JTextArea text = new JTextArea("caca");
                panel.add(text);
                ventanaInicio.setVisible(true);
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
                ventanaInicio.setVisible(false);
                ventanaInicio.dispose();
                interfaz.addWindowListener(interfaz);
            }
        });
    }
}