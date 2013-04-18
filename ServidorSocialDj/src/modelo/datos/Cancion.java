/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.datos;

/**
 *
 * @author 66785320
 */
public class Cancion {

    private String nombreCancion;
    private String nombreAutor;
    private String nombreAlbum;
    private long duracion;
    private int id;
    private int album_id;

    public Cancion(String n, String a, String al, int i, long dur) {
        nombreCancion = n;
        nombreAutor = a;
        nombreAlbum = al;
        duracion = dur;
        id = i;
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


//    @Override
//    public boolean equals(Object obj) {
//        if (obj != null) {
//            if (obj instanceof Cancion) {
//                Cancion cancion = (Cancion) obj;
//                if (cancion.getNombreAutor().equals(nombreAutor) && cancion.getNombreAlbum().equals(nombreAlbum)
//                        && cancion.getId() == id && cancion.getNombreCancion().equals(nombreCancion)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 97 * hash + (this.nombreCancion != null ? this.nombreCancion.hashCode() : 0);
//        hash = 97 * hash + (this.nombreAutor != null ? this.nombreAutor.hashCode() : 0);
//        hash = 97 * hash + (this.nombreAlbum != null ? this.nombreAlbum.hashCode() : 0);
//        hash = 97 * hash + this.id;
//        return hash;
//    }
    
    @Override
    public String toString(){
        return nombreCancion + " " + nombreAutor + " " + nombreAlbum + " " + id;
    }

    /**
     * @return the duracion
     */
    public long getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(long duracion) {
        this.duracion = duracion;
    }
}
