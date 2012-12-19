package interfazreproductorbasico;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author 66785379
 */
public class InterfazReproductorBasico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame ventana = new JFrame();
                ReproductorPanel panel = new ReproductorPanel();
                ventana.add(panel);
                ventana.setVisible(true);
                ventana.setLocation(250, 150);
                ventana.setSize(600,400);
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        
    }
}
