/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import conexion.ConnectionManager;
import conexion.ServerSocketListener;
import elementosInterfaz.ReproductorPanel;
import ficheros.FicherosManager;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import reproductor.PlayerReproductor;
import reproductor.ThreadGetDuracion;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;

/**
 *
 * @author 66785270
 */
public class ListasCancionesManager implements MediaPlayerEventListener, ServerSocketListener {

    private static ListasCancionesManager manager;
    private HashMap<String, ArrayList> votos_cliente;
    private static ListaPromocionada lista_sonando;
    private static ConjuntoListas listas_canciones;
    private ConnectionManager conection;
    private PlayerReproductor reproductor;
    private static boolean hay_lista_promocionada;
    private static String nombre_servidor;
    private String path;

    private ListasCancionesManager() {

        reproductor = new PlayerReproductor();
        listas_canciones = new ConjuntoListas();
        lista_sonando = new ListaPromocionada();
        conection = new ConnectionManager();
        hay_lista_promocionada = false;
        nombre_servidor = "servidor";
    }

    public void inicializarListas(){
        listas_canciones.inicializar(FicherosManager.cargarSesion());
    }
    public void promocionarLista(int id_lista) {

        if (!listas_canciones.estaVacia() && !listas_canciones.getLista(id_lista).getCanciones().isEmpty()) {

            getLista_sonando().NuevasCanciones(listas_canciones.getLista(id_lista));
            hay_lista_promocionada = true;
            try {
                conection.getSocket().enviarMensajeServer("*", "0|" + getLista_sonando());
            } catch (Exception ex) {
                System.err.println("Error al enviar la lista: " + ex.toString());
            }
        } else {
            JOptionPane pane = new JOptionPane("Has promocionado una lista vacia", JOptionPane.DEFAULT_OPTION);
            JDialog dialog = pane.createDialog(null, "Error");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
        }
    }

    public boolean procesarVoto(int id_cancion, boolean tipo) {

        if (lista_sonando.añadirVoto(id_cancion, tipo)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean playNext() {

        if (hay_lista_promocionada) {

            CancionPromocionada cancion = getLista_sonando().reproducirCancion();

            if (!reproductor.reproducir(cancion.getPath())) {
                System.err.println("Error al reproducir la cancion.");
            }
            ReproductorPanel.song.setText(cancion.getNombre());
            ReproductorPanel.artist.setText(cancion.getArtista());

            try {
                conection.getSocket().enviarMensajeServer("*", "2|" + cancion.getId());
            } catch (Exception ex) {
                System.err.println("Error al enviar la cancion a reproducir: " + ex);
            }
            return true;
        } else {
            return false;
        }
    }

    public void addCanciones(int index) {

        if (getListas_canciones().estaVacia()) {
            anadirLista();
        }
        JFileChooser chooser = new JFileChooser() {
            @Override
            protected JDialog createDialog(Component parent) throws HeadlessException {
                JDialog dialog = super.createDialog(parent);
                dialog.setAlwaysOnTop(true);
                return dialog;
            }
        };

        chooser.setMultiSelectionEnabled(true);
        chooser.setCurrentDirectory(new File(getPath()));
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.getName().endsWith(".mp3") || f.isDirectory()) {
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

        int eleccion = chooser.showOpenDialog(null);
        if (eleccion == 0) {
            File[] files = chooser.getSelectedFiles();
            setPath(chooser.getCurrentDirectory().getPath());
            List<File> listaFiles = (List<File>) Arrays.asList(files);

            if (!listaFiles.isEmpty()) {

                ListaCanciones lista = listas_canciones.getLista(index);
                int fila = lista.getCanciones().size();
                int max_id = lista.getMaxId();
                Cancion c;

                for (File f : listaFiles) {
                    if (f.exists()) {

                        c = getReproductor().getCancion(f.getAbsolutePath());
                        max_id++;
                        c.setId(max_id);
                        listas_canciones.addCancion(c, index);
                        ThreadGetDuracion thread = new ThreadGetDuracion(listas_canciones, c, index, fila);
                        thread.start();
                        fila++;
                    }
                }
                FicherosManager.guardarSesion(listas_canciones.getListas());
            }
        }
    }

    public void removeCancion(int index, int[] filas_selec) {

        if (filas_selec.length == 0 || listas_canciones.getLista(index).getCanciones().isEmpty()) {

            JOptionPane pane = new JOptionPane("No ha seleccionado una canción", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
            JDialog dialog = pane.createDialog(null, "Error");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);

        } else {

            JOptionPane pane = new JOptionPane("¿Esta seguro de querer borrar las canciones seleccionadas?", JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
            JDialog dialog = pane.createDialog(null, "Borrar canciones");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            Integer validacion = (Integer) pane.getValue();
            if (validacion != null && validacion == 0) {
                listas_canciones.removeCanciones(filas_selec, index);
                FicherosManager.guardarSesion(listas_canciones.getListas());
            }
        }
    }

    public void anadirLista() {

        JOptionPane pane = new JOptionPane("Nombre Lista", JOptionPane.PLAIN_MESSAGE);
        pane.setWantsInput(true);
        JDialog dialog = pane.createDialog(null, "Lista Nueva");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
        String nombre = (String) pane.getInputValue();
        
        if (nombre != null && !nombre.equals("") && !nombre.equals("uninitializedValue")) {
            listas_canciones.addLista(nombre);
            FicherosManager.guardarSesion(listas_canciones.getListas());
        }
    }

    public void borrarLista(int index) {

        JOptionPane pane = new JOptionPane("¿Desea borrar la lista?", JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = pane.createDialog(null, "Confirmar borrado");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
        Integer opcion = (Integer) pane.getValue();
        if (opcion != null && opcion == 0) {
            listas_canciones.removeLista(index);
            FicherosManager.guardarSesion(listas_canciones.getListas());
        }
    }

    public void setNombreServidor() {
        try {
            JOptionPane pane = new JOptionPane("Nombre del servidor", JOptionPane.PLAIN_MESSAGE);
            pane.setWantsInput(true);
            JDialog dialog = pane.createDialog(null, "Nombre Servidor");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            nombre_servidor = (String) pane.getInputValue();
            if (conection.getSocket().logIn(nombre_servidor)) {
                conection.getSocket().addServerSocketListener(this);
                conection.getSocket().startListenBridge();
            }
        } catch (Exception ex) {
            System.out.println("Error al loggearte: " + ex);
        }
    }

    public static ListasCancionesManager getInstance() {
        if (manager == null) {
            manager = new ListasCancionesManager();
        }
        return manager;
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

    @Override
    public void onMessageReceived(String ip, String message) {
        ArrayList<Integer> _votos_cliente = votos_cliente.get(ip);
        int tipo = Integer.parseInt(message.split("\\|")[0]);
        message = message.split("\\|")[1];
        try {
            switch (tipo) {
                case 0:
                    if (lista_sonando != null) {
                        System.out.println("0|" + lista_sonando.toString());
                        conection.getSocket().enviarMensajeServer(ip, "0|" + lista_sonando.toString());
                    }
                    if (lista_sonando.getCancionSonando() != null) {
                        conection.getSocket().enviarMensajeServer(ip, "4|" + lista_sonando.getCancionSonando().toString());
                    }
                    if (_votos_cliente != null) {
                        for (int id_cancion : _votos_cliente) {
                            conection.getSocket().enviarMensajeServer(ip, "1|" + id_cancion);
                        }
                    }
                    break;
                case 1:
                    int id_cancion = Integer.parseInt(message);
                    if (lista_sonando.añadirVoto(id_cancion, true)) {
                        conection.getSocket().enviarMensajeServer(ip, "1|" + message);
                        if (_votos_cliente != null && !_votos_cliente.contains(id_cancion)) {
                            _votos_cliente.add(id_cancion);
                        }
                    } else {
                        conection.getSocket().enviarMensajeServer(ip, "1|0");
                    }
                    break;
                case 3:
                    id_cancion = Integer.parseInt(message);
                    if (lista_sonando.añadirVoto(id_cancion, false)) {
                        conection.getSocket().enviarMensajeServer(ip, "3|" + message);
                        if (_votos_cliente != null) {
                            _votos_cliente.remove((Integer) id_cancion);
                        }
                    } else {
                        conection.getSocket().enviarMensajeServer(ip, "3|0");
                    }
                    break;
            }
        } catch (IOException ex) {
            System.err.println("Error en la recepcion de mensaje: " + ex);
        }
    }

    @Override
    public void onClientConnected(String ip) {
        if (votos_cliente.get(ip) == null) {
            votos_cliente.put(ip, new ArrayList<Integer>());
        }
    }

    @Override
    public void onClientDisconnected(String ip) {
    }

    /**
     * @return the reproductor
     */
    public PlayerReproductor getReproductor() {
        return reproductor;
    }

    public String getNombreServidor() {
        return nombre_servidor;
    }

    public ListaPromocionada getLista_sonando() {
        return lista_sonando;
    }

    public ConjuntoListas getListas_canciones() {
        return listas_canciones;
    }

    public ConnectionManager getConector() {
        return conection;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String aPath) {
        path = aPath;
    }
}