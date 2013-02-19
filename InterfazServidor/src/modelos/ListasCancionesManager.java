/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import conexion.ConnectionManager;
import elementosInterfaz.FramePrincipal;
import elementosInterfaz.ReproductorPanel;
import elementosInterfaz.Tabla;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import reproductor.PlayerReproductor;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;

/**
 *
 * @author 66785270
 */
public class ListasCancionesManager implements MediaPlayerEventListener {

    public static HashMap<String, ArrayList<Integer>> votos_cliente = new HashMap<String, ArrayList<Integer>>();
    public static Cancion cancion_sonando;
    public static Tabla tabla_sonando;
    public static ArrayList<ListaCanciones> listas_canciones;
    public static ArrayList<Tabla> tablasPendientes;
    public static ListaCanciones lista_sonando = new ListaCanciones();
    private PlayerReproductor reproductor;
    private static ArrayList<Cancion> canciones;
    public static String path;
    private static int columnaSonandoCancion = 0, columnaSonandoAutor = 1, columnaSonandoVotos = 2;
    public String[] nombresColumnaSonando = {"Cancion", "Artista", "Votos"};
    public static String[] nombresColumnaPendientes = {"Cancion"};
    private static final ListasCancionesManager manager = new ListasCancionesManager();

    private ListasCancionesManager() {

        reproductor = new PlayerReproductor();
        reproductor.getMediaPlayer().addMediaPlayerEventListener(this);
        listas_canciones = new ArrayList();
    }

    public static ListasCancionesManager getInstance() {
        return manager;
    }

    public void promocionarLista(int id_lista) {

        int x = 0;
        canciones = new ArrayList();

        if (!listas_canciones.get(id_lista).getCanciones().isEmpty()) {
            tabla_sonando.getTabla().setFilas(listas_canciones.get(id_lista).getCanciones().size());
            for (Cancion p : listas_canciones.get(id_lista).getCanciones()) {

                canciones.add(p);
                tabla_sonando.getTabla().setValueAt(p.getNombre(), x, columnaSonandoCancion);
                tabla_sonando.getTabla().setValueAt(p.getArtista(), x, columnaSonandoAutor);
                tabla_sonando.getTabla().setValueAt(0, x, columnaSonandoVotos);
                x++;
            }

            //fragmento cutre del siglo
            lista_sonando.getCanciones().clear();
            for (Cancion p : canciones) {
                lista_sonando.getCanciones().add(new Cancion(p.getId(), p.getNombre(), p.getDisco(), p.getArtista(), p.getDuracion(), p.getPath()));
            }
            canciones = lista_sonando.getCanciones();

            votos_cliente = new HashMap();
            try {
                ConnectionManager.socket.enviarMensajeServer("*", "0|" + lista_sonando);
            } catch (Exception ex) {
                FramePrincipal.log("Error al enviar la lista: " + ex.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Has promocionado una lista vacia");
        }
    }

    public static boolean procesarVoto(int id_cancion, boolean tipo) {
        int x = 0;
        for (Cancion p : canciones) {
            if (p.getId() == id_cancion) {
                String value_votos = (String) tabla_sonando.getValueAt(x, columnaSonandoVotos);
                if (!"*".equals(value_votos)) {
                    int votos = Integer.parseInt(value_votos);
                    if (tipo) {
                        votos++;
                    } else {
                        votos--;
                    }
                    tabla_sonando.setValueAt(votos, x, columnaSonandoVotos);
                    return true;
                }
                return false;
            }
            x++;
        }
        return false;
    }

    public boolean playNext() {

        if (canciones != null && !canciones.isEmpty()) {

            int x = 0, votos, id_max = 0;
            String valor, valor_max;
            for (Cancion p : canciones) {
                valor = (String) tabla_sonando.getValueAt(x, columnaSonandoVotos);
                if (!valor.equals("*")) {
                    votos = Integer.parseInt(valor);
                    valor_max = (String) tabla_sonando.getValueAt(id_max, columnaSonandoVotos);
                    if (valor_max.equals("*") || votos >= Integer.parseInt(valor_max)) {
                        id_max = x;
                    }
                }
                x++;
            }
            Cancion cancion = canciones.get(id_max);
            cancion_sonando = cancion;
            //lista_sonando.getCanciones().remove(id_max);
            tabla_sonando.setValueAt("*", id_max, columnaSonandoVotos);
            reproductor.reproducir(cancion.getPath());
            cancion.setReproducida(1);
            ReproductorPanel.song.setText(cancion.getNombre());
            ReproductorPanel.artist.setText(cancion.getArtista());

            //Esto no se por que no funciona

            //for(ArrayList<Integer> v:votos_cliente.values())
            //v.remove(cancion.getId());
            FramePrincipal.log("Reproduciendo cancion: " + cancion.getNombre());
            try {
                ConnectionManager.socket.enviarMensajeServer("*", "2|" + cancion.getId());
            } catch (Exception ex) {
                FramePrincipal.log("Error al enviar la cancion a reproducir: ");
            }
            return true;
        } else {
            return false;
        }
    }

    public void addCanciones(int index) {

        JFileChooser chooser = new JFileChooser();
        List<File> listaFiles;

        chooser.setMultiSelectionEnabled(true);
        chooser.setCurrentDirectory(new File(path));
        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (acabaEnMp3(f) || f.isDirectory()) {
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
        path = chooser.getCurrentDirectory().getPath();
        listaFiles = (List<File>) Arrays.asList(files);

        if (!listaFiles.isEmpty()) {

            int x = 0;
            int comienzo = listas_canciones.get(index).getCanciones().size();
            ListaCanciones lista = listas_canciones.get(index);
            int max_id = lista.getMaxId();
            Cancion c;

            for (File f : listaFiles) {
                if (f.exists()) {
                    c = reproductor.getCancion(f.getAbsolutePath());

                    max_id++;
                    c.setId(max_id);

                    listas_canciones.get(index).getCanciones().add(c);
                    if (!tablasPendientes.get(index).getTabla().getValueAt(0, columnaSonandoCancion).equals("Añade Canciones")) {
                        tablasPendientes.get(index).getTabla().setFilas(tablasPendientes.get(index).getTabla().getFilas() + 1);
                    }

                    tablasPendientes.get(index).getTabla().setValueAt(c.getNombre(), x + comienzo, columnaSonandoCancion);
                    x++;
                }
            }
        }
    }

    public static boolean acabaEnMp3(File f) {
        return f.getName().endsWith(".mp3");
    }

    public void removeCancion(int index) {

        int[] filasSelects = tablasPendientes.get(index).getSelectedRows();


        if (filasSelects.length == 0 || listas_canciones.get(index).getCanciones().isEmpty()) {

            JOptionPane.showMessageDialog(null, "No ha seleccionado una canción");

        } else {

            int validacion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de querer borrar las canciones Seleccionadas?");

            if (validacion == 0) {
                tablasPendientes.get(index).clearSelection();
                for (int y = 0; y < filasSelects.length; y++) {

                    listas_canciones.get(index).getCanciones().remove(filasSelects[0]);

                    for (int x = filasSelects[0]; x < listas_canciones.get(index).getCanciones().size(); x++) {

                        tablasPendientes.get(index).getTabla().setValueAt(tablasPendientes.get(index).getTabla().getValueAt(x + 1, columnaSonandoCancion), x, columnaSonandoCancion);
                    }

                    if (!listas_canciones.get(index).getCanciones().isEmpty()) {

                        tablasPendientes.get(index).getTabla().setValueAt("", listas_canciones.get(index).getCanciones().size(), columnaSonandoCancion);
                        tablasPendientes.get(index).getTabla().setFilas(tablasPendientes.get(index).getTabla().getFilas() - 1);

                    } else {
                        tablasPendientes.get(index).setValueAt("Añade Canciones", 0, columnaSonandoCancion);
                    }
                }
            }
        }
    }

    public void addLista(ListaCanciones lista) {


        listas_canciones.add(lista);
    }

    public void removeLista(int index) {

        listas_canciones.remove(index);
        tablasPendientes.remove(index);

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