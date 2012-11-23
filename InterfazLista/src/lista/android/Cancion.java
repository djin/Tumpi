/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android;

/**
 *
 * @author 66785320
 */
public class Cancion {
    private String nombreCancion;
    private String nombreAutor;
    private String nombreAlbum;
    private int id;
    private Boolean votado;
    public Cancion(String n, int i, Boolean v) {
        nombreCancion = n;
        id = i;
        votado = v;
    }

    /**
     * @return the nombreCancion
     */
    public String getNombreCancion() {
        return nombreCancion;
    }

    /**
     * @param nombreCancion the nombreCancion to set
     */
    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
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
     * @return the votado
     */
    public Boolean getVotado() {
        return votado;
    }

    /**
     * @param votado the votado to set
     */
    public void setVotado(Boolean votado) {
        this.votado = votado;
    }

    /**
     * @return the nombreAutor
     */
    public String getNombreAutor() {
        return nombreAutor;
    }

    /**
     * @param nombreAutor the nombreAutor to set
     */
    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    /**
     * @return the nombreAlbum
     */
    public String getNombreAlbum() {
        return nombreAlbum;
    }

    /**
     * @param nombreAlbum the nombreAlbum to set
     */
    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }
    
}
