/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.datos;

import java.util.ArrayList;

/**
 * 
 * @author zellyalgo
 */
public class ModeloDatos {
    
    private static ModeloDatos INSTANCE = new ModeloDatos();
    public ArrayList<String> nombreLista = new ArrayList<String>();

    private ModeloDatos() {
    }

    public static ModeloDatos getInstance(){
        return INSTANCE;
    }
    
    
}
