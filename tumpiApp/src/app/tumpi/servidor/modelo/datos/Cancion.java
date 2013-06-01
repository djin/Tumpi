/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tumpi.servidor.modelo.datos;

import java.io.Serializable;

/**
 *
 * @author 66785320
 */
public class Cancion implements Serializable{

    public String nombreCancion;
    public String nombreAutor;
    public String nombreAlbum;
    public int duracion;
    public int id;
    public int album_id;
    public String path;

    public Cancion(int _id, String name, String album, int _album_id,String artist, int length, String _path) {
        nombreCancion = name;
        nombreAutor = artist;
        nombreAlbum = album;
        album_id= _album_id;
        duracion = length;
        id = _id;
        path=_path;
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
    
    public String getLengthString(){
        int secs=duracion/1000;
        int mins=secs/60;
        secs=secs-(mins*60);
        String duration=mins+" : ";
        if(secs<10)
            duration=duration+"0";
        duration=duration+secs;
        return duration;
    }
}
