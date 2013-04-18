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
public class ListasManager {

    private static ListasManager INSTANCE = new ListasManager();
    public ArrayList<String> nombreLista;
    public ArrayList<ListaCanciones> listasCanciones;
    private ListaPromocionada lista_promocionada;
    private Cancion cancionReproduciendo;

    private ListasManager() {
        nombreLista = new ArrayList<String>();
        listasCanciones = new ArrayList<ListaCanciones>();
        lista_promocionada=new ListaPromocionada(new ListaCanciones());
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

    public static ListasManager getInstance() {
        return INSTANCE;
    }

    public ListaCanciones getLista(int posicion) {
        ListaCanciones dar = listasCanciones.get(posicion);
        return dar;
    }

    public void borrarCanciones(int posicion, ArrayList<Cancion> listaDeBorradas) {
        listasCanciones.get(posicion).removeCanciones(listaDeBorradas);
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