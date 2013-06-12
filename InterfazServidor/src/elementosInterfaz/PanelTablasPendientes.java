package elementosInterfaz;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import modelos.Cancion;
import modelos.ConjuntoListasListener;
import modelos.ListaCanciones;
import modelos.ListasCancionesManager;

/**
 *
 * @author Juan
 */
public class PanelTablasPendientes extends JTabbedPane implements ConjuntoListasListener {

    private ArrayList<TablaPendiente> tablas_pendientes;
    private ArrayList<ListaCanciones> listas;
    private ListasCancionesManager manager;
    private ArrayList<JButton> botonesCerrar = new ArrayList<JButton>();
    private Dimension screen, ladoDerecho;
    private static int columnaPendienteCancion = 0, columnaPendienteAutor = 1, columnaPendienteAlbum = 2, columnaPendienteDuracion = 3;

    public PanelTablasPendientes() {

        screen = Toolkit.getDefaultToolkit().getScreenSize();
        ladoDerecho = new Dimension(screen.width * 55 / 100, 300);
        tablas_pendientes = new ArrayList();
        manager = ListasCancionesManager.getInstance();
        setPreferredSize(ladoDerecho);
    }

    @Override
    public void onInitialize(ArrayList _listas) {

        listas = _listas;
        int x = 0;
        for (ListaCanciones l : listas) {

            onNewList(l.nombre_lista);
            for (Cancion c : l.getCanciones()) {
                onAddSong(x,c);
            }
            x++;
        }
    }

    @Override
    public void onNewList(String nombre) {

        tablas_pendientes.add(new TablaPendiente());
        if (getTitleAt(0).equals("Inicio")) {
            remove(0);
        }

        remove(getTabCount() - 1);
        JScrollPane panelTabla = new JScrollPane(tablas_pendientes.get(tablas_pendientes.size() - 1));
        tablas_pendientes.get(tablas_pendientes.size() - 1).getActionMap().put("borrar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pestana_selec = getSelectedIndex();
                int[] filas_selec = tablas_pendientes.get(pestana_selec).getSelectedRows();
                manager.removeCancion(pestana_selec, filas_selec);
            }
        });

        addTab(nombre, panelTabla);
        GridBagConstraints gbc = new GridBagConstraints();
        PanelPestana panelPestana = new PanelPestana(nombre, gbc);
        JButton botonCerrar = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.borrarLista(botonesCerrar.indexOf(e.getSource()));
            }
        });
        Pintado.anularPintadoBotonParaImagen(botonCerrar, "icons/borrarpestana.png", "icons/borrarpestana2.png", new Dimension(13, 13));
        botonesCerrar.add(botonCerrar);
        gbc.gridx++;
        gbc.ipady = 3;
        gbc.weightx = 0;
        panelPestana.add(botonCerrar, gbc);
        setTabComponentAt(tablas_pendientes.size() - 1, panelPestana);
        setSelectedIndex(tablas_pendientes.size() - 1);
        anadirPestanaFinal();
        anadirMouseListener(tablas_pendientes.size() - 1);
    }

    @Override
    public void onRemoveList(int index) {

        botonesCerrar.remove(index);
        remove(index);
        if (getSelectedIndex() == getTabCount() - 1) {
            setSelectedIndex(getTabCount() - 2);
        }
        if (getTabCount() == 1) {
            remove(getTabCount() - 1);
            addTab("Inicio", generarPanelInicio());
            anadirPestanaFinal();
        }
        tablas_pendientes.remove(index);
    }

    @Override
    public void onAddSong(int index, Cancion cancion) {

        String duracion_no_calculada = "0:00";
        int comienzo = 0;
        if (!tablas_pendientes.get(index).getTabla().getValueAt(0, columnaPendienteCancion).equals("Añade Canciones")) {
            comienzo = tablas_pendientes.get(index).getTabla().getFilas();
            tablas_pendientes.get(index).getTabla().setFilas(tablas_pendientes.get(index).getTabla().getFilas() + 1);

        } else {
            borrarMouseListener(index);
        }
        System.out.println("" + cancion.getNombre() + cancion.getArtista() + cancion.getDisco() + duracion_no_calculada);
        tablas_pendientes.get(index).getTabla().setValueAt(cancion.getNombre(), comienzo, columnaPendienteCancion);
        tablas_pendientes.get(index).getTabla().setValueAt(cancion.getArtista(), comienzo, columnaPendienteAutor);
        tablas_pendientes.get(index).getTabla().setValueAt(cancion.getDisco(), comienzo, columnaPendienteAlbum);
        tablas_pendientes.get(index).getTabla().setValueAt(duracion_no_calculada, comienzo, columnaPendienteDuracion);
    }

    @Override
    public void onRemoveSongs(int index, int[] filas, boolean vacio) {

        tablas_pendientes.get(index).clearSelection();
        int tamano = tablas_pendientes.get(index).getTabla().getFilas() - 1;

        for (int y = 0; y < filas.length; y++) {
            if (!vacio || (y != tamano)) {
                for (int x = filas[0]; x < tamano; x++) {
                    tablas_pendientes.get(index).getTabla().setValueAt(tablas_pendientes.get(index).getTabla().getValueAt(x + 1, columnaPendienteCancion), x, columnaPendienteCancion);
                    tablas_pendientes.get(index).getTabla().setValueAt(tablas_pendientes.get(index).getTabla().getValueAt(x + 1, columnaPendienteAutor), x, columnaPendienteAutor);
                    tablas_pendientes.get(index).getTabla().setValueAt(tablas_pendientes.get(index).getTabla().getValueAt(x + 1, columnaPendienteAlbum), x, columnaPendienteAlbum);
                    tablas_pendientes.get(index).getTabla().setValueAt(tablas_pendientes.get(index).getTabla().getValueAt(x + 1, columnaPendienteDuracion), x, columnaPendienteDuracion);
                }
                tablas_pendientes.get(index).getTabla().setValueAt("", tamano, columnaPendienteCancion);
                tablas_pendientes.get(index).getTabla().setValueAt("", tamano, columnaPendienteAutor);
                tablas_pendientes.get(index).getTabla().setValueAt("", tamano, columnaPendienteAlbum);
                tablas_pendientes.get(index).getTabla().setValueAt("", tamano, columnaPendienteDuracion);
                tablas_pendientes.get(index).getTabla().setFilas(tablas_pendientes.get(index).getTabla().getFilas() - 1);
            } else {
                tablas_pendientes.get(index).setValueAt("Añade Canciones", 0, columnaPendienteCancion);
                tablas_pendientes.get(index).setValueAt("", 0, columnaPendienteAlbum);
                tablas_pendientes.get(index).setValueAt("", 0, columnaPendienteAutor);
                tablas_pendientes.get(index).setValueAt("", 0, columnaPendienteDuracion);
                anadirMouseListener(index);
            }
        }
    }

    @Override
    public void onUpdatedLength(int index, int fila, String duracion) {
        tablas_pendientes.get(index).setValueAt(duracion, fila, columnaPendienteDuracion);
    }

    public JPanel generarPanelInicio() {
        JPanel panelVacio = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        JLabel textoInicio = new JLabel("Presiona la pestaña ");
        textoInicio.setFont(new Font("", Font.BOLD, 22));
        JButton image = new JButton();
        Pintado.anularPintadoBotonParaImagen(image, "icons/anadirpestana.png", "icons/anadirpestana2.png", new Dimension(16, 16));
        JLabel textoInicio2 = new JLabel(" para crear tu propia lista de Tumpi");
        textoInicio2.setFont(new Font("", Font.BOLD, 22));
        panelVacio.add(textoInicio, gbc);
        gbc.gridx++;
        panelVacio.add(image, gbc);
        gbc.gridx++;
        panelVacio.add(textoInicio2, gbc);
        panelVacio.setPreferredSize(ladoDerecho);
        return panelVacio;
    }

    public void anadirPestanaFinal() {
        addTab("AnadirPestana", null);
        JButton anadirPestana;
        anadirPestana = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.anadirLista();

            }
        });
        Pintado.anularPintadoBotonParaImagen(anadirPestana, "icons/anadirpestana.png", "icons/anadirpestana2.png", new Dimension(16, 16));
        setTabComponentAt(getTabCount() - 1, anadirPestana);
        setEnabledAt(getTabCount() - 1, false);
    }

    public void borrarMouseListener(int index) {
        tablas_pendientes.get(index).removeMouseListener(tablas_pendientes.get(index).getMouseListeners()[2]);
    }

    public void anadirMouseListener(final int index) {
        tablas_pendientes.get(index).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tablas_pendientes.get(index).getSelectedRow() == 0) {
                    manager.addCanciones(index);
                }
            }
        });
    }

    public TablaPendiente getTabla_pendiente(int index) {
        return tablas_pendientes.get(index);
    }
}