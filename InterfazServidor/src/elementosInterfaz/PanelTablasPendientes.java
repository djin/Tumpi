/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import modelos.Cancion;
import modelos.ConjuntoListasListener;
import modelos.ListasCancionesManager;

/**
 *
 * @author Juan
 */
public class PanelTablasPendientes extends JTabbedPane implements ConjuntoListasListener {

    private ArrayList<TablaPendiente> tablas_pendientes;
    ListasCancionesManager manager;
    private ArrayList<JButton> botonesCerrar = new ArrayList<JButton>();
    private Dimension screen, ladoDerecho;
    private ArrayList<String> nombresLista;
    private static int columnaPendienteCancion = 0, columnaPendienteAutor = 1, columnaPendienteAlbum = 2, columnaPendienteDuracion = 3;

    public PanelTablasPendientes() {

        screen = Toolkit.getDefaultToolkit().getScreenSize();
        ladoDerecho = new Dimension(screen.width * 55 / 100, 300);
        tablas_pendientes = new ArrayList();
        manager = ListasCancionesManager.getInstance();
        nombresLista = new ArrayList();
        nombresLista.add("Inicio");
        setPreferredSize(ladoDerecho);
    }

    public void anadirPestana(String nombre_lista) {

        if (getTitleAt(0).equals("Inicio")) {
            nombresLista.remove(0);
            remove(0);
        }
        
        remove(getTabCount()-1);
        JScrollPane panelTabla = new JScrollPane(new TablaPendiente());
        tablas_pendientes.get(tablas_pendientes.size() - 1).getActionMap().put("borrar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pestana_selec = getSelectedIndex();
                int[] filas_selec = tablas_pendientes.get(pestana_selec).getSelectedRows();
                manager.removeCancion(pestana_selec, filas_selec);
            }
        });

        nombresLista.add(nombre_lista);
        addTab(nombre_lista, panelTabla);
        GridBagConstraints gbc = new GridBagConstraints();
        PanelPestana panelPestana = new PanelPestana(nombre_lista, gbc);
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
        setTabComponentAt(nombresLista.size() - 1, panelPestana);
        setSelectedIndex(nombresLista.size() - 1);
        anadirPestanaFinal();
    }

    public void eliminarPestana(int pestanaBorrar) {
        
            botonesCerrar.remove(pestanaBorrar);
            remove(pestanaBorrar);
            nombresLista.remove(pestanaBorrar);
            if (getSelectedIndex() == getTabCount() - 1) {
                setSelectedIndex(getTabCount() - 2);
            }
            if (getTabCount() == 1) {
                nombresLista.add("Inicio");
                remove(getTabCount() - 1);
                addTab(nombresLista.get(0), generarPanelInicio());
                anadirPestanaFinal();
            }
        
    }

    @Override
    public void onNewList(String nombre) {

        tablas_pendientes.add(new TablaPendiente());
        anadirPestana(nombre);
        anadirMouseListener(tablas_pendientes.size() - 1);
    }

    @Override
    public void onRemoveList(int index) {
        eliminarPestana(index);
        tablas_pendientes.remove(index);
    }

    @Override
    public void onAddSong(int index, Cancion cancion) {

        int duracion_no_calculada = 0;
        int comienzo = 0;
        if (!tablas_pendientes.get(index).getTabla().getValueAt(0, columnaPendienteCancion).equals("Añade Canciones")) {
            comienzo = tablas_pendientes.get(index).getTabla().getFilas();
            tablas_pendientes.get(index).getTabla().setFilas(tablas_pendientes.get(index).getTabla().getFilas() + 1);
            
        } else {
            borrarMouseListener(index);
        }
        System.out.println(""+cancion.getNombre()+cancion.getArtista()+cancion.getDisco()+duracion_no_calculada);
        tablas_pendientes.get(index).getTabla().setValueAt(cancion.getNombre(), comienzo, columnaPendienteCancion);
        tablas_pendientes.get(index).getTabla().setValueAt(cancion.getArtista(), comienzo, columnaPendienteAutor);
        tablas_pendientes.get(index).getTabla().setValueAt(cancion.getDisco(), comienzo, columnaPendienteAlbum);
        tablas_pendientes.get(index).getTabla().setValueAt(duracion_no_calculada, comienzo, columnaPendienteDuracion);
    }

    @Override
    public void onRemoveSongs(int index, int[] filas, boolean vacio) {

        tablas_pendientes.get(index).clearSelection();
        int tamaño = tablas_pendientes.get(index).getTabla().getFilas();
        for (int x = filas[0]; x < tamaño; x++) {

            tablas_pendientes.get(index).getTabla().setValueAt(tablas_pendientes.get(index).getTabla().getValueAt(x + 1, columnaPendienteCancion), x, columnaPendienteCancion);
            tablas_pendientes.get(index).getTabla().setValueAt(tablas_pendientes.get(index).getTabla().getValueAt(x + 1, columnaPendienteAutor), x, columnaPendienteAutor);
            tablas_pendientes.get(index).getTabla().setValueAt(tablas_pendientes.get(index).getTabla().getValueAt(x + 1, columnaPendienteAlbum), x, columnaPendienteAlbum);
            tablas_pendientes.get(index).getTabla().setValueAt(tablas_pendientes.get(index).getTabla().getValueAt(x + 1, columnaPendienteDuracion), x, columnaPendienteDuracion);
        }

        if (!vacio) {
            tamaño = tablas_pendientes.get(index).getTabla().getFilas()-1;
            
            tablas_pendientes.get(index).getTabla().setValueAt("", tamaño, columnaPendienteCancion);
            tablas_pendientes.get(index).getTabla().setValueAt("", tamaño, columnaPendienteAutor);
            tablas_pendientes.get(index).getTabla().setValueAt("", tamaño, columnaPendienteAlbum);
            tablas_pendientes.get(index).getTabla().setValueAt("", tamaño, columnaPendienteDuracion);
            tablas_pendientes.get(index).getTabla().setFilas(tablas_pendientes.get(index).getTabla().getFilas() - 1);

        } else {
            tablas_pendientes.get(index).setValueAt("Añade Canciones", 0, columnaPendienteCancion);
            tablas_pendientes.get(index).setValueAt("", 0, columnaPendienteAlbum);
            tablas_pendientes.get(index).setValueAt("", 0, columnaPendienteAutor);
            tablas_pendientes.get(index).setValueAt("", 0, columnaPendienteDuracion);
            anadirMouseListener(index);
        }
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
        JLabel textoInicio2 = new JLabel(" para crear tu propia lista de socialDj");
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
                JOptionPane pane = new JOptionPane("Nombre Lista", JOptionPane.PLAIN_MESSAGE);
                pane.setWantsInput(true);
                JDialog dialog = pane.createDialog(null, "Lista Nueva");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
                String nombreLista = (String) pane.getInputValue();
                if (nombreLista != null && !nombreLista.equals("") && !nombreLista.equals("uninitializedValue")) {
                    manager.anadirLista(nombreLista);
                }
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