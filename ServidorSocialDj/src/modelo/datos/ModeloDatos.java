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
    public ArrayList<String> nombreLista;
    public ArrayList<Cancion> listaPromocionada;
    public ArrayList<ArrayList<Cancion>> listasCanciones;

    private ModeloDatos() {
        nombreLista = new ArrayList<String>();
        listasCanciones = new ArrayList<ArrayList<Cancion>>();
        listaPromocionada = new ArrayList<Cancion>();
        for (int i = 0; i <= 10; i++) {
            listaPromocionada.add(new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
        }
    }
    
    public static ModeloDatos getInstance() {
        return INSTANCE;
    }

    public ArrayList<Cancion> getLista(int posicion) {
        ArrayList<Cancion> dar = listasCanciones.get(posicion);
        if (dar.isEmpty()) {
            for (int i = 0; i <= 10; i++) {
                dar.add(new Cancion("Los Redondeles", "Siempre Fuertes", "HUAE", 0, 24567, false, false));
            }
        }
        return dar;
    }
    public void borrarCanciones (int posicion, ArrayList<Cancion> listaDeBorradas){
        listasCanciones.get(posicion).removeAll(listaDeBorradas);
    }
}