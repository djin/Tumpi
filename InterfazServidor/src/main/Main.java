package main;

import elementosInterfaz.FramePrincipal;
import javax.swing.*;

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
            }
        });
    }
}