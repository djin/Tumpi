/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

/**
 *
 * @author 66785320
 */
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
 
/**
 * Clase que permite cargar imágenes dentro del paquete e implementa un buffer
 * de las mismas para optimizar la lectura a disco requiriéndose la operación
 * la primera vez que se carga la imagen
 * @author Miguel González - Ceura
 */
public class Imagenes {
     
    private static HashMap<String, ImageIcon> imagenes;
     
    /**
     * Constructor estático de la clase
     */
    static {
        imagenes = new HashMap<String, ImageIcon>();
    }
     
    /**
     * Devuelve una imagen
     * @param nombreImagen Nombre de la imagen
     * @return Image imagen
     */
    public static ImageIcon getImagen(String nombreImagen) {
        if(!imagenes.containsKey(nombreImagen)) {
            imagenes.put(nombreImagen, new ImageIcon(
            Imagenes.class.getResource(nombreImagen)
            ));
        }
         
        return imagenes.get(nombreImagen);
    }
    
    public static String getVLCPath(){
        return Imagenes.class.getResource("VLC/").getPath();
    }
}
