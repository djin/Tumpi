/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reproductor;

import javax.swing.SwingUtilities;

/**
 *
 * @author 66785379
 */
public class Reproductor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                VtnReproductor ventana = new VtnReproductor();
                ventana.setVisible(true);
                ventana.setLocation(250, 150);
            }
        });
        
    }
}
