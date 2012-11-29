/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import conexion.ConnectionManager;
import java.util.ArrayList;
import main.Main;
import reproductor.PlayerReproductor;
import tablas.Tabla;

/**
 *
 * @author 66785270
 */
public class ListasCancionesManager {
    public static ListaCanciones lista_sonando;
    public static Tabla tabla_sonando;
    public static ArrayList <ListaCanciones> listas_canciones;
    private PlayerReproductor  reproductor = new PlayerReproductor(); //Reproductor de musica
    
    public void promocionarLista(int id_lista){
        int x = 0;
        ArrayList <Cancion> canciones=listas_canciones.get(id_lista).getCanciones();
        for(Cancion p: canciones){
            tabla_sonando.setValueAt(p.getNombre(), x, 0);
            tabla_sonando.setValueAt(0, x, 1);
            x++;
        }
        lista_sonando=listas_canciones.get(id_lista);
        try {
            ConnectionManager.socket.enviarMensajeServer("*", "0|"+lista_sonando);            
        } catch (Exception ex) {
            Main.log("Error al enviar la lista: "+ex.toString());
        }
    }
    
    public static boolean procesarVoto(int id_cancion,boolean tipo){
        ArrayList <Cancion> canciones=lista_sonando.getCanciones();
        int x=0;
        for(Cancion p: canciones){
            if(p.getId()==id_cancion){
                int votos=Integer.parseInt((String)tabla_sonando.getValueAt(x, 1));
                if(tipo)
                    votos++;
                else
                    votos--;
                tabla_sonando.setValueAt(votos, x, 1);
                return true;
            }
            x++;
        }
        return false;
    }
    
    public void playNext(){
        
        if(lista_sonando!=null){
            ArrayList <Cancion> canciones=lista_sonando.getCanciones();
            int x=0,votos,id_max=0;
            for(Cancion p: canciones){
                votos=Integer.parseInt((String)tabla_sonando.getValueAt(x, 1));
                if(votos>=Integer.parseInt((String)tabla_sonando.getValueAt(id_max, 1)))
                    id_max=x;
                x++;
            }
            Cancion cancion=canciones.get(id_max);        
            /*
             * No suena la cancion que es la primera de la lista.
            */
            reproductor.reproducir(cancion.getPath());
            Main.log("Reproduciendo cancion: "+cancion.getNombre());
            try {
                ConnectionManager.socket.enviarMensajeServer("*", "2|"+cancion.getId());
            } catch (Exception ex) {
                Main.log("Error al enviar la cancion a reproducir: ");
            }
        }
    }
    
    public void addLista(ListaCanciones lista){
        listas_canciones.add(lista);
    }
    
}
