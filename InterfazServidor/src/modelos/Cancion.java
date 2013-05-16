package modelos;

import java.io.Serializable;

/**
 *
 * @author 66786575
 */
public class Cancion implements Serializable{

    private int id;
    private String nombre;
    private String disco;
    private String artista;
    private long duracion;
    private String duracion_formateada;
    private String path;

    public Cancion(int _id, String name, String album, String artist, long length, String _path) {

        id = _id;
        nombre = name;
        disco = album;
        artista = artist;
        duracion = length;
        path = _path;
    }
    
    public void setDuracion(long duracion) {
        this.duracion = duracion;
        
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

    /**
     * @return the duracion
     */
    public long getDuracion() {
        return duracion;
    }
    
    @Override
    public String toString() {
        return getId() + "*" + getNombre() + "*" + getArtista() + "*" + getDisco() + "*" + getDuracion();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Cancion) {
                Cancion cancion = (Cancion) obj;
                if (cancion.getArtista().equals(this.getArtista()) && cancion.getDisco().equals(this.getDisco())
                        && cancion.getDuracion()==this.getDuracion() && cancion.getId() == this.getId()
                        && cancion.getPath().equals(this.getPath()) && cancion.getNombre().equals(this.getNombre())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return the duracion_formateada
     */
    public String getDuracion_formateada() {
        return duracion_formateada;
    }

    /**
     * @param duracion_formateada the duracion_formateada to set
     */
    public void setDuracion_formateada(String duracion_formateada) {
        this.duracion_formateada = duracion_formateada;
    }
}
