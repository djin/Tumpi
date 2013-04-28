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

    public TablaPendiente(ModeloTabla modeloTabla) {

        tabla = modeloTabla;

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
