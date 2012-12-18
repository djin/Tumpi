/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import conexion.ConnectionManager;
import elementosInterfaz.DialogoNombreLista;
import elementosInterfaz.ModeloTabla;
import elementosInterfaz.Tabla;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import main.Main;
import reproductor.PlayerReproductor;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;

/**
 *
 * @author 66785270
 */
public class ListasCancionesManager implements MediaPlayerEventListener {

    public static ListaCanciones lista_sonando;
    public static HashMap <String,Integer> votos_cliente=new HashMap();
    public static Cancion cancion_sonando;
    public static Tabla tabla_sonando;
    public static ArrayList<ListaCanciones> listas_canciones;
    public static ArrayList<Tabla> tablasPendientes;
    private PlayerReproductor reproductor = new PlayerReproductor(); //Reproductor de musica

    public ListasCancionesManager() {
        reproductor.getMediaPlayer().addMediaPlayerEventListener(this);
    }

    public void promocionarLista(int id_lista) {
        int x = 0;
        ArrayList<Cancion> canciones = listas_canciones.get(id_lista).getCanciones();

        for (Cancion p : canciones) {
            tabla_sonando.setValueAt(p.getNombre(), x, 0);
            tabla_sonando.setValueAt(0, x, 1);
            x++;
        }

        for (int y = x; y < 60; y++) {

            tabla_sonando.setValueAt("", y, 0);
            tabla_sonando.setValueAt(0, y, 1);
        }

        lista_sonando = listas_canciones.get(id_lista);
        votos_cliente=new HashMap();
        try {
            ConnectionManager.socket.enviarMensajeServer("*", "0|" + lista_sonando);
        } catch (Exception ex) {
            Main.log("Error al enviar la lista: " + ex.toString());
        }
    }

    public static boolean procesarVoto(int id_cancion, boolean tipo) {
        ArrayList<Cancion> canciones = lista_sonando.getCanciones();
        int x = 0;
        for (Cancion p : canciones) {
            if (p.getId() == id_cancion) {
                String value_votos=(String) tabla_sonando.getValueAt(x, 1);
                if(!"*".equals(value_votos)){
                    int votos = Integer.parseInt(value_votos);
                    if (tipo) {
                        votos++;
                    } else {
                        votos--;
                    }
                    tabla_sonando.setValueAt(votos, x, 1);
                    return true;
                    }
                return false;
            }
            x++;
        }
        return false;
    }

    public void playNext() {

        if (lista_sonando != null) {
            ArrayList<Cancion> canciones = lista_sonando.getCanciones();
            int x = 0, votos, id_max = 0;
            String valor, valor_max;
            for (Cancion p : canciones) {
                valor = (String) tabla_sonando.getValueAt(x, 1);
                if (!valor.equals("*")) {
                    votos = Integer.parseInt(valor);
                    valor_max = (String) tabla_sonando.getValueAt(id_max, 1);
                    if (valor_max.equals("*") || votos >= Integer.parseInt(valor_max)) {
                        id_max = x;
                    }
                }
                x++;
            }
            Cancion cancion = canciones.get(id_max);
            cancion_sonando=cancion;
            //lista_sonando.getCanciones().remove(id_max);
            tabla_sonando.setValueAt("*", id_max, 1);
            reproductor.reproducir(cancion.getPath());
            cancion.setReproducida(1);
            Main.log("Reproduciendo cancion: " + cancion.getNombre());
            try {
                ConnectionManager.socket.enviarMensajeServer("*", "2|" + cancion.getId());
            } catch (Exception ex) {
                Main.log("Error al enviar la cancion a reproducir: ");
            }
        }
    }

    public List<File> addCanciones() {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setCurrentDirectory(new File("C:\\Users\\66785361\\Documents\\GitHub\\socialDj\\InterfazServidor"));
        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (acabaEnMp3(f)) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public String getDescription() {
                return "Filtro mp3";
            }
        });
        
        chooser.showOpenDialog(null);
        File[] files = chooser.getSelectedFiles();
        List<File> lista =Arrays.asList(files);
        return lista;
        

    }

    public static boolean acabaEnMp3(File f) {
        return f.getName().endsWith(".mp3");
    }

    public void removeCancion(int index) {


        int filaSelec = tablasPendientes.get(index).getSelectedRow();
        listas_canciones.get(index).getCanciones().size();
        listas_canciones.get(index).getCanciones().remove(filaSelec);

        if (filaSelec != -1) {

            for (int x = filaSelec; x < 59; x++) {

                tablasPendientes.get(index).setValueAt(tablasPendientes.get(index).getValueAt(x + 1, 0), x, 0);
            }
            tablasPendientes.get(index).setValueAt("", 59, 0);
        } else {
            JOptionPane.showMessageDialog(null, "No ha seleccionado una canción");
        }
    }

    public void addLista(ListaCanciones lista) {


        listas_canciones.add(lista);
    }

    public void removeLista(int index) {

        if (!tablasPendientes.isEmpty()) {

            listas_canciones.remove(index);
            tablasPendientes.remove(index);
        } else {
            JOptionPane.showMessageDialog(null, "No quedan listas abiertas");
        }

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
