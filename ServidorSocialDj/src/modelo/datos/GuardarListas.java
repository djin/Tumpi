/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.datos;

import interfaces.ListenerListaCambio;
import java.util.ArrayList;

/**
 *
 * @author Zellyalgo
 */
public class GuardarListas implements ListenerListaCambio {

    ListasManager manager;
    boolean terminado = true;

    public GuardarListas() {
        manager = ListasManager.getInstance();
        initListeners();
    }

    private void initListeners() {
        manager.addListasChangedListener(this);
    }

    public void ListasChanged(ArrayList<ListaCanciones> listas, ArrayList<String> nombresLista) {
        if (terminado) {
            terminado = false;
            Thread hiloGuardar = new Thread(new RunnableGuardado(listas, nombresLista, this));
            hiloGuardar.start();
        }
    }
}
