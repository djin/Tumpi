/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import javax.swing.*;
import modelos.ModeloTabla;

/**
 *
 * @author 66786575
 */
public class Tabla extends JTable {
        
    private ModeloTabla tabla;
    
    public Tabla(ModeloTabla modeloTabla){
        
        tabla = modeloTabla;
        
        setModel(tabla);
        setRowHeight(20);
        
        /*this.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent e) {
                
            }

            @Override
            public void focusLost(FocusEvent e) {
                clearSelection();
            }
            
        });*/        
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

