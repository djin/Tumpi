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
    
    int id;
    String nombre, disco, artista, duracion, path;
    
    public Cancion(int _id, String name, String album, String artist, String length, String _path){
        
        id = _id;
        nombre = name;
        disco = album;
        artista = artist;
        duracion = length;
        path = _path;
        
    }
}
