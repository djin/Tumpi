/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import modelos.ListaCanciones;
import tablas.ModeloTabla;

/**
 *
 * @author 66786575
 */
public class PromocionarLista extends AbstractAction{
    
    private int numLista;
    private ArrayList <ListaCanciones> listasDeCanciones;

    public PromocionarLista(int numList, ArrayList <ListaCanciones> listasCanciones){
       
        numLista = numList;
        listasDeCanciones = listasCanciones;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
    }
    
}
