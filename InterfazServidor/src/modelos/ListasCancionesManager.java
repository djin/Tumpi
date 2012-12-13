/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import conexion.ConnectionManager;
import elementosInterfaz.Tabla;
import java.util.ArrayList;
import main.Main;
import reproductor.PlayerReproductor;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;

/**
 *
 * @author 66785270
 */
public class ListasCancionesManager implements MediaPlayerEventListener{
    public static ListaCanciones lista_sonando;
    public static Tabla tabla_sonando;
    public static ArrayList <ListaCanciones> listas_canciones;
    private PlayerReproductor  reproductor = new PlayerReproductor(); //Reproductor de musica
    
    public ListasCancionesManager(){
        reproductor.getMediaPlayer().addMediaPlayerEventListener(this);
    }
    
    public void promocionarLista(int id_lista){
        int x = 0;
        ArrayList <Cancion> canciones=listas_canciones.get(id_lista).getCanciones();
        
        for(Cancion p: canciones){
            tabla_sonando.setValueAt(p, x, 0);
            tabla_sonando.setValueAt(0, x, 1);
            x++;
        }
        
        for(int y = x; y<60; y++){
            
            tabla_sonando.setValueAt("", y, 0);
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
            String valor,valor_max;
            for(Cancion p: canciones){
                valor=(String)tabla_sonando.getValueAt(x, 1);
                if(!valor.equals("*")){
                    votos=Integer.parseInt(valor);
                    valor_max=(String)tabla_sonando.getValueAt(id_max, 1);
                    if(valor_max.equals("*") || votos>=Integer.parseInt(valor_max))
                        id_max=x;
                }
                x++;
            }
            Cancion cancion=canciones.get(id_max);
            //lista_sonando.getCanciones().remove(id_max);
            tabla_sonando.setValueAt("*", id_max, 1);
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
    
    public void removeLista(int index){
        listas_canciones.remove(index);
    }
    
    public void removeCancion(int numLista, int numCancion){
        
        listas_canciones.get(numLista).getCanciones().size();
        listas_canciones.get(numLista).getCanciones().remove(numCancion);
    }

    @Override
    public void mediaChanged(MediaPlayer mp, libvlc_media_t l, String string) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void opening(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void buffering(MediaPlayer mp, float f) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void playing(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void paused(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void stopped(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void forward(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void backward(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void finished(MediaPlayer mp) {
        playNext();
    }

    @Override
    public void timeChanged(MediaPlayer mp, long l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void positionChanged(MediaPlayer mp, float f) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void seekableChanged(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void pausableChanged(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void titleChanged(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void snapshotTaken(MediaPlayer mp, String string) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void lengthChanged(MediaPlayer mp, long l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void videoOutput(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void error(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mediaMetaChanged(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mediaSubItemAdded(MediaPlayer mp, libvlc_media_t l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mediaDurationChanged(MediaPlayer mp, long l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mediaParsedChanged(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mediaFreed(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mediaStateChanged(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void newMedia(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void subItemPlayed(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void subItemFinished(MediaPlayer mp, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void endOfSubItems(MediaPlayer mp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
