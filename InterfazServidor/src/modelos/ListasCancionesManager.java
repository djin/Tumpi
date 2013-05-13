/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import conexion.ConnectionManager;
import conexion.ServerSocketListener;
import elementosInterfaz.FramePrincipal;
import elementosInterfaz.ReproductorPanel;
import elementosInterfaz.Tabla;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import reproductor.PlayerReproductor;
import reproductor.ThreadGetDuraciones;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;

/**
 *
 * @author 66785270
 */
//Tres cuartas partes de lo mismo, hay que mirarselo muchisimo, demasidas cosas, habra que delegar.
public class ListasCancionesManager implements MediaPlayerEventListener, ServerSocketListener {

    private static final ListasCancionesManager manager = new ListasCancionesManager();
    public static ArrayList<ListaCanciones> listas_canciones;
    public static ArrayList<Tabla> tablasPendientes;
    private HashMap<String, ArrayList> votos_cliente;
    private static ListaPromocionada lista_sonando;
    private ConnectionManager conection;
    private PlayerReproductor reproductor;
    private String path;
    private static int columnaPendienteCancion = 0, columnaPendienteAutor = 1, columnaPendienteAlbum = 2, columnaPendienteDuracion = 3;
    public static String[] nombresColumnaPendientes = {"Cancion", "Artista", "Album", "Duración"};
    private static boolean hay_lista_promocionada;
    private static String nombre_servidor;

    private ListasCancionesManager() {

        reproductor = new PlayerReproductor();
        listas_canciones = new ArrayList();
        tablasPendientes = new ArrayList();
        lista_sonando = new ListaPromocionada();
        conection = new ConnectionManager();
        hay_lista_promocionada = false;
        nombre_servidor = "servidor";
    }

    public void promocionarLista(int id_lista) {

        if (!listas_canciones.isEmpty() && !listas_canciones.get(id_lista).getCanciones().isEmpty()) {

            getLista_sonando().NuevasCanciones(listas_canciones.get(id_lista));
            hay_lista_promocionada = true;
            try {
                conection.getSocket().enviarMensajeServer("*", "0|" + getLista_sonando());
            } catch (Exception ex) {
                FramePrincipal.log("Error al enviar la lista: " + ex.toString());
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
                FramePrincipal.log("Error al reproducir la cancion.");
            }
            ReproductorPanel.song.setText(cancion.getNombre());
            ReproductorPanel.artist.setText(cancion.getArtista());

            FramePrincipal.log("Reproduciendo cancion: " + cancion.getNombre());
            try {
                conection.getSocket().enviarMensajeServer("*", "2|" + cancion.getId());
            } catch (Exception ex) {
                FramePrincipal.log("Error al enviar la cancion a reproducir: " + ex);
            }
            return true;
        } else {
            return false;
        }
    }

    public void addCanciones(int index) {

        JFileChooser chooser = new JFileChooser() {
            @Override
            protected JDialog createDialog(Component parent) throws HeadlessException {
                JDialog dialog = super.createDialog(parent);
                dialog.setAlwaysOnTop(true);
                return dialog;
            }
        };
        List<File> listaFiles;
        ArrayList<Cancion> cancionesDuracion = new ArrayList();

        chooser.setMultiSelectionEnabled(true);
        chooser.setCurrentDirectory(new File(getPath()));
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
        int eleccion = chooser.showOpenDialog(null);
        if (eleccion == 0) {
            File[] files = chooser.getSelectedFiles();
            setPath(chooser.getCurrentDirectory().getPath());
            listaFiles = (List<File>) Arrays.asList(files);

            if (!listaFiles.isEmpty()) {

                int x = 0;
                int comienzo = listas_canciones.get(index).getCanciones().size();
                ListaCanciones lista = listas_canciones.get(index);
                int max_id = lista.getMaxId();
                Cancion c;

                for (File f : listaFiles) {
                    if (f.exists()) {

                        String duracionFormateada;
                        c = getReproductor().getCancion(f.getAbsolutePath());
                        max_id++;
                        c.setId(max_id);
                        cancionesDuracion.add(c);
                        duracionFormateada = getReproductor().formatearDuracion(c.getDuracion());
                        listas_canciones.get(index).getCanciones().add(c);
                        if (!tablasPendientes.get(index).getTabla().getValueAt(0, columnaPendienteCancion).equals("Añade Canciones")) {
                            tablasPendientes.get(index).getTabla().setFilas(tablasPendientes.get(index).getTabla().getFilas() + 1);

                        } else {
                            borrarMouseListener(index);
                        }

                        tablasPendientes.get(index).getTabla().setValueAt(c.getNombre(), x + comienzo, columnaPendienteCancion);
                        tablasPendientes.get(index).getTabla().setValueAt(c.getArtista(), x + comienzo, columnaPendienteAutor);
                        tablasPendientes.get(index).getTabla().setValueAt(c.getDisco(), x + comienzo, columnaPendienteAlbum);
                        tablasPendientes.get(index).getTabla().setValueAt(duracionFormateada, x + comienzo, columnaPendienteDuracion);
                        x++;

                    }
                }
                ThreadGetDuraciones thread = new ThreadGetDuraciones(cancionesDuracion, getReproductor().getIdentificadorMediaPlayer());
                thread.start();
            }
        }
    }

    public void borrarMouseListener(int index) {
        tablasPendientes.get(index).removeMouseListener(tablasPendientes.get(index).getMouseListeners()[2]);
    }

    public void anadirMouseListener(final int index) {
        tablasPendientes.get(index).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tablasPendientes.get(index).getSelectedRow() == 0) {
                    manager.addCanciones(index);
                }
            }
        });
    }

    public Tabla crearTabla() {
        tablasPendientes.add(new Tabla(new ModeloTabla(nombresColumnaPendientes, 1)));

        tablasPendientes.get(tablasPendientes.size() - 1).setValueAt("Añade Canciones", 0, 0);
        tablasPendientes.get(tablasPendientes.size() - 1).setValueAt("", 0, 1);
        tablasPendientes.get(tablasPendientes.size() - 1).setValueAt("", 0, 2);
        tablasPendientes.get(tablasPendientes.size() - 1).setValueAt("", 0, 3);
        anadirMouseListener(tablasPendientes.size() - 1);
        tablasPendientes.get(tablasPendientes.size() - 1).getColumnModel().getColumn(0).setMinWidth(160);
        tablasPendientes.get(tablasPendientes.size() - 1).getColumnModel().getColumn(1).setMinWidth(160);
        tablasPendientes.get(tablasPendientes.size() - 1).getColumnModel().getColumn(2).setMaxWidth(250);
        tablasPendientes.get(tablasPendientes.size() - 1).getColumnModel().getColumn(2).setMinWidth(140);
        tablasPendientes.get(tablasPendientes.size() - 1).getColumnModel().getColumn(3).setMaxWidth(60);
        tablasPendientes.get(tablasPendientes.size() - 1).getColumnModel().getColumn(3).setMinWidth(60);

        tablasPendientes.get(tablasPendientes.size() - 1).getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tablasPendientes.get(tablasPendientes.size() - 1).getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        tablasPendientes.get(tablasPendientes.size() - 1).getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "borrar");
        return tablasPendientes.get(tablasPendientes.size() - 1);
    }

    public static boolean acabaEnMp3(File f) {
        return f.getName().endsWith(".mp3");
    }

    public void removeCancion(int index) {

        int[] filasSelects = tablasPendientes.get(index).getSelectedRows();


        if (filasSelects.length == 0 || listas_canciones.get(index).getCanciones().isEmpty()) {

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
                tablasPendientes.get(index).clearSelection();
                for (int y = 0; y < filasSelects.length; y++) {

                    listas_canciones.get(index).getCanciones().remove(filasSelects[0]);

                    for (int x = filasSelects[0]; x < listas_canciones.get(index).getCanciones().size(); x++) {

                        tablasPendientes.get(index).getTabla().setValueAt(tablasPendientes.get(index).getTabla().getValueAt(x + 1, columnaPendienteCancion), x, columnaPendienteCancion);
                        tablasPendientes.get(index).getTabla().setValueAt(tablasPendientes.get(index).getTabla().getValueAt(x + 1, columnaPendienteAutor), x, columnaPendienteAutor);
                        tablasPendientes.get(index).getTabla().setValueAt(tablasPendientes.get(index).getTabla().getValueAt(x + 1, columnaPendienteAlbum), x, columnaPendienteAlbum);
                        tablasPendientes.get(index).getTabla().setValueAt(tablasPendientes.get(index).getTabla().getValueAt(x + 1, columnaPendienteDuracion), x, columnaPendienteDuracion);
                    }

                    if (!listas_canciones.get(index).getCanciones().isEmpty()) {

                        tablasPendientes.get(index).getTabla().setValueAt("", listas_canciones.get(index).getCanciones().size(), columnaPendienteCancion);
                        tablasPendientes.get(index).getTabla().setValueAt("", listas_canciones.get(index).getCanciones().size(), columnaPendienteAutor);
                        tablasPendientes.get(index).getTabla().setValueAt("", listas_canciones.get(index).getCanciones().size(), columnaPendienteAlbum);
                        tablasPendientes.get(index).getTabla().setValueAt("", listas_canciones.get(index).getCanciones().size(), columnaPendienteDuracion);
                        tablasPendientes.get(index).getTabla().setFilas(tablasPendientes.get(index).getTabla().getFilas() - 1);

                    } else {
                        tablasPendientes.get(index).setValueAt("Añade Canciones", 0, columnaPendienteCancion);
                        tablasPendientes.get(index).setValueAt("", 0, columnaPendienteAlbum);
                        tablasPendientes.get(index).setValueAt("", 0, columnaPendienteAutor);
                        tablasPendientes.get(index).setValueAt("", 0, columnaPendienteDuracion);
                        anadirMouseListener(index);
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
            System.out.println("Error al loggearte: "+ex);
        }
    }

    public String getNombreServidor() {
        return nombre_servidor;
    }

    public static ListasCancionesManager getInstance() {
        return manager;
    }

    public ListaPromocionada getLista_sonando() {
        return lista_sonando;
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
            System.err.println("Error en la recepcion de mensaje: "+ex);
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
}