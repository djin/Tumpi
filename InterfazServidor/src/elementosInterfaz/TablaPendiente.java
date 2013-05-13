/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import javax.swing.JTable;
import modelos.ModeloTabla;

/**
 *
 * @author Juan
 */
public class TablaPendiente extends JTable {
    
    private ModeloTabla tabla;
    public static String[] nombresColumnaPendientes = {"Cancion", "Artista", "Album", "Duración"};
    private static int columnaPendienteCancion = 0, columnaPendienteAutor = 1, 
            columnaPendienteAlbum = 2, columnaPendienteDuracion = 3;
    
    public TablaPendiente() {

        tabla = new ModeloTabla(nombresColumnaPendientes, 1);
        setModel(tabla);
        setRowHeight(20);

    }

    /**
     * @return the tabla
     */
    public ModeloTabla getTabla() {
        return tabla;
    }

    /**
     * @param tabla the tabla to set
     */
    public void setTabla(ModeloTabla _tabla) {
        tabla = _tabla;
        setModel(tabla);
    }
}
