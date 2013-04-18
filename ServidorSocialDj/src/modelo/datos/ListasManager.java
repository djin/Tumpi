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
    public ListaPromocionada lista_promocionada;
    private Cancion cancionReproduciendo;

    private ListasManager() {
        nombreLista = new ArrayList<String>();
        listasCanciones = new ArrayList<ListaCanciones>();
        lista_promocionada=new ListaPromocionada(new ListaCanciones());
        cancionReproduciendo = new CancionPromocionada(0, "Los Redondeles", "Siempre Fuertes", 1, "HUAE", 1234, "C:/");
    }
    
    public void limpiarDatos(ArrayList<Cancion> datos) {
        int n = datos.size();
        for (int i = 0; i < n; i++) {
            datos.remove(0);
        }
    }
    
    public void anadirCanciones(int posicion, ArrayList<Cancion> canciones_anadir){
        listasCanciones.get(posicion).addCanciones(canciones_anadir);
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
        lista_promocionada = new ListaPromocionada(listasCanciones.get(posicion));
    }
}