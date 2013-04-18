/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.datos;

/**
 *
 * @author 66785270
 */
public class CancionPromocionada extends Cancion {
    
    private int reproducida;
    private int votos;
    
    public CancionPromocionada(int _id, String name, String album,int album_id, String artist, int length, String _path) {
        
        super(_id, name, album, album_id, artist, length, _path);
        reproducida = 0;
        votos = 0;
        
    }
    
    @Override
    public String toString() {
        return super.id + "*" + super.nombreCancion + "*" + super.nombreAutor + "*" + super.nombreAlbum + "*" + super.duracion + "*" + reproducida;
    }

    /**
     * @return the reproducida
     */
    public int getReproducida() {
        return reproducida;
    }

    /**
     * @param reproducida the reproducida to set
     */
    public void setReproducida(int reproducida) {
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
