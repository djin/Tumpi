/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author 66786575
 */
public class CancionPromocionada extends Cancion {
    
    private boolean reproducida;
    private int votos;
    
    public CancionPromocionada(int _id, String name, String album, String artist, long length, String _path) {
        
        super(_id, name, album, artist, length, _path);
        reproducida = false;
        votos = 0;
        
    }
    
    @Override
    public String toString() {
        return getId() + "*" + getNombre() + "*" + getArtista() + "*" + getDisco() + "*" + getDuracion() + "*" + getReproducida();
    }

    /**
     * @return the reproducida
     */
    public boolean getReproducida() {
        return reproducida;
    }

    /**
     * @param reproducida the reproducida to set
     */
    public void setReproducida(boolean reproducida) {
        this.reproducida = reproducida;
    }

    /**
     * @return the votos
     */
    public int getVotos() {
        return votos;
    }

    /**
     * @param votos the votos to set
     */
    public void setVotos(int votos) {
        this.votos = votos;
    }
}
