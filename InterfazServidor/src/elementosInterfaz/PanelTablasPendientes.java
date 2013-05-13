/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.util.ArrayList;
import javax.swing.JTabbedPane;
import modelos.CancionPromocionada;
import modelos.ConjuntoListasListener;
import modelos.ListasCancionesManager;

/**
 *
 * @author Juan
 */
public class PanelTablasPendientes extends JTabbedPane implements ConjuntoListasListener {
    
    ArrayList<TablaPendiente> tablas_pendientes;
    ListasCancionesManager manager;
    
    public PanelTablasPendientes(){
        
        tablas_pendientes = new ArrayList();
        manager=ListasCancionesManager.getInstance();
    }
    
    @Override
    public void onNewList(ArrayList<CancionPromocionada> canciones) {
        
        tablas_pendientes.add(new TablaPendiente());
        
    }

    @Override
    public void onAddSongs(int fila, boolean tipo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onRemoveSongs(int fila) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
