/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author 66786575
 */
public class Cancion {
    
    private int id;
    private String nombre;
    private String disco;
    private String artista;
    private String duracion;
    private String path;
    
    public Cancion(int _id, String name, String album, String artist, String length, String _path){
        
        id = _id;
        nombre = name;
        disco = album;
        artista = artist;
        duracion = length;
        path = _path;
        
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the disco
     */
    public String getDisco() {
        return disco;
    }

    /**
     * @param disco the disco to set
     */
    public void setDisco(String disco) {
        this.disco = disco;
    }

    /**
     * @return the artista
     */
    public String getArtista() {
        return artista;
    }

    /**
     * @param artista the artista to set
     */
    public void setArtista(String artista) {
        this.artista = artista;
    }

    /**
     * @return the duracion
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }
}
