/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import tablas.ModeloTabla;
import tablas.Tabla;

/**
 *
 * @author 66786575
 */
public class PromocionarLista extends AbstractAction{
    
    private ModeloTabla tablaPendientes;
    private ModeloTabla tablaSonando;

    public PromocionarLista(ModeloTabla pendientes, ModeloTabla sonando){
       
        tablaPendientes = pendientes;
        tablaSonando = sonando;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
    }
    
}
