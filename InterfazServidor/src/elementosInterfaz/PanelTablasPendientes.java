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
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import modelos.CancionPromocionada;
import modelos.ConjuntoListasListener;
import modelos.ListaCanciones;
import modelos.ListasCancionesManager;

/**
 *
 * @author Juan
 */
public class PanelTablasPendientes extends JTabbedPane implements ConjuntoListasListener {
    
    ArrayList<TablaPendiente> tablas_pendientes;
    ListasCancionesManager manager;
    private ArrayList<JButton> botonesCerrar = new ArrayList<JButton>();
    private Dimension screen, ladoDerecho;
    private ArrayList<String> nombresLista;
    
    public PanelTablasPendientes(){
        
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        ladoDerecho = new Dimension(screen.width * 55 / 100, 300);
        tablas_pendientes = new ArrayList();
        manager=ListasCancionesManager.getInstance();
        nombresLista = new ArrayList();
        nombresLista.add("Inicio");
        setPreferredSize(ladoDerecho);
    }
    
    public void addPestana(String nombre_lista) {

        if (getTitleAt(0).equals("Inicio")) {
            nombresLista.remove(0);
            remove(0);
        }
        
        int numListas = getTabCount() - 1;
        remove(numListas);
        manager.addLista(new ListaCanciones(nombre_lista));
        JScrollPane panelTabla = new JScrollPane(manager.crearTabla());
        manager.tablasPendientes.get(manager.tablasPendientes.size() - 1).getActionMap().put("borrar", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                manager.removeCancion(getSelectedIndex());
            }
        });

        nombresLista.add(nombre_lista);
        addTab(nombre_lista, panelTabla);
        GridBagConstraints gbc = new GridBagConstraints();
        PanelPestana panelPestana = new PanelPestana(nombre_lista, gbc);
        JButton botonCerrar = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                borrarPestana(botonesCerrar.indexOf(e.getSource()));
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
    
    public void borrarPestana(int pestanaBorrar) {
        JOptionPane pane = new JOptionPane("¿Desea borrar la lista?", JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = pane.createDialog(null, "Confirmar borrado");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
        Integer opcion = (Integer) pane.getValue();
        if (opcion != null && opcion == 0) {
            botonesCerrar.remove(pestanaBorrar);
            remove(pestanaBorrar);
            manager.removeLista(pestanaBorrar);
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
                    addPestana(nombreLista);
                }
            }
        });
        Pintado.anularPintadoBotonParaImagen(anadirPestana, "icons/anadirpestana.png", "icons/anadirpestana2.png", new Dimension(16, 16));
        setTabComponentAt(getTabCount() - 1, anadirPestana);
        setEnabledAt(getTabCount() - 1, false);
    }
    @Override
    public void onNewList(ArrayList<CancionPromocionada> canciones) {
        
        tablas_pendientes.add(new TablaPendiente());
        
    }

    @Override
    public void onAddSongs(int fila, boolean tipo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onRemoveSongs(int fila) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}