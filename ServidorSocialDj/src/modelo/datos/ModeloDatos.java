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
    private Cancion cancionReproduciendo;

    private ModeloDatos() {
        nombreLista = new ArrayList<String>();
        listasCanciones = new ArrayList<ArrayList<Cancion>>();
        listaPromocionada = new ArrayList<Cancion>();
        cancionReproduciendo = new Cancion("Cancion Sonando", "Mangurrian", "HUAE", 0, 1234);
    }
    
    public void limpiarDatos(ArrayList<Cancion> datos) {
        int n = datos.size();
        for (int i = 0; i < n; i++) {
            datos.remove(0);
        }
    }
    
    public Cancion getCancionReproduciendo (){
        return cancionReproduciendo;
    }
    
    public void setCancionReproduciendo (Cancion c){
        cancionReproduciendo = c;
    }

    public static ModeloDatos getInstance() {
        return INSTANCE;
    }

    public ArrayList<Cancion> getLista(int posicion) {
        ArrayList<Cancion> dar = listasCanciones.get(posicion);
        return dar;
    }

    public void borrarCanciones(int posicion, ArrayList<Cancion> listaDeBorradas) {
        listasCanciones.get(posicion).removeAll(listaDeBorradas);
    }

    public void promocionar(int posicion) {
        limpiarDatos(listaPromocionada);
        for (Cancion c : listasCanciones.get(posicion)) {
            listaPromocionada.add(c);
        }
    }
    
    public void anadirCanciones (int posicion, ArrayList<Cancion> listaCanciones){
        for(Cancion c : listaCanciones){
            listasCanciones.get(posicion).add(c);
        }
    }
}