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
    private Boolean sonado;
    public Cancion(String n, String a, String al, int i, Boolean v, Boolean s) {
        nombreCancion = n;
        nombreAutor = a;
        nombreAlbum = al;
        id = i;
        votado = v;
        sonado = s;
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

    /**
     * @return the sonado
     */
    public Boolean getSonado() {
        return sonado;
    }

    /**
     * @param sonado the sonado to set
     */
    public void setSonado(Boolean sonado) {
        this.sonado = sonado;
    }
    
}
