/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
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
        
        setValueAt("Añade Canciones", 0, 0);
        setValueAt("", 0, 1);
        setValueAt("", 0, 2);
        setValueAt("", 0, 3);
        getColumnModel().getColumn(0).setMinWidth(160);
        getColumnModel().getColumn(1).setMinWidth(160);
        getColumnModel().getColumn(2).setMaxWidth(250);
        getColumnModel().getColumn(2).setMinWidth(140);
        getColumnModel().getColumn(3).setMaxWidth(60);
        getColumnModel().getColumn(3).setMinWidth(60);

        getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "borrar");
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
