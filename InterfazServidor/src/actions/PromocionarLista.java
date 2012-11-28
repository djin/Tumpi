/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import modelos.Cancion;
import modelos.ListaCanciones;
import tablas.Tabla;

/**
 *
 * @author 66786575
 */
public class PromocionarLista extends AbstractAction{
    
    private int numLista;
    private ArrayList <ListaCanciones> listasDeCanciones;
    private Tabla tablaSonando;

    public PromocionarLista(int numList, ArrayList <ListaCanciones> listasCanciones, Tabla _tablaSonando){
       
        numLista = numList;
        listasDeCanciones = listasCanciones;
        tablaSonando = _tablaSonando;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        int x = 0;
        
        for(Cancion p: listasDeCanciones.get(numLista).getCanciones()){
            
            System.out.println("hola");
            
            tablaSonando.setValueAt(p.getNombre(), x, 0);
            x++;
        }
    }
    
}
