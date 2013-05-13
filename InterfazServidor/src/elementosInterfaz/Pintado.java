/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.awt.Dimension;
import javax.swing.JButton;

/**
 *
 * @author Juan
 */
public class Pintado {
    
    public static void anularPintadoBotonParaImagen(JButton boton, String botonSinRaton, String botonConRaton, Dimension tamano) {
        boton.addMouseListener(new MyMouseListener(boton, Imagenes.getImagen(botonSinRaton), Imagenes.getImagen(botonConRaton)));
        boton.setIcon(Imagenes.getImagen(botonSinRaton));
        boton.setPreferredSize(tamano);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setBackground(null);
        boton.setContentAreaFilled(false);
    }
}
