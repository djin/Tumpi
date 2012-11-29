/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import conexion.ConnectionManager;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import main.Main;
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
    private ConnectionManager conexion;

    public PromocionarLista(int numList, ArrayList <ListaCanciones> listasCanciones, Tabla _tablaSonando,ConnectionManager _conexion){
       
        numLista = numList;
        listasDeCanciones = listasCanciones;
        tablaSonando = _tablaSonando;
        conexion=_conexion;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
    }
    
}
