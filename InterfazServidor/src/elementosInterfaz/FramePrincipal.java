/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import conexion.ConnectionManager;
import ficheros.FicherosManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.swing.*;
import modelos.ListasCancionesManager;

/**
 *
 * @author 66786575
 */
public class FramePrincipal extends JFrame implements WindowListener {

    private JPanel panel = (JPanel) this.getContentPane();
    private ListasCancionesManager listas_manager;
    private FicherosManager ficheros_manager;
    private TablaSonando tabla_sonando;
    private PanelTablasPendientes pestanas_pendientes;
    private ReproductorPanel panelReproductor;
    private Menus barramenus;
    private Dimension screen, ladoIzquierdo;
    ConnectionManager server = null;
    int puerto_socket = 2222;

    public FramePrincipal() {
        
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        ladoIzquierdo = new Dimension(screen.width * 35 / 100, 50);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        Image icon = Imagenes.getImagen("icons/socialDJ.png").getImage();
        setIconImage(icon);
        setTitle("socialDj");
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));
        
        listas_manager = ListasCancionesManager.getInstance();
        listas_manager.getReproductor().getReproductorMediaPlayer().addMediaPlayerEventListener(listas_manager);
        panelReproductor = new ReproductorPanel(listas_manager);
        ficheros_manager = new FicherosManager(listas_manager);
        ficheros_manager.cargarPreferencias();
        
        iniciarListasCanciones();
        iniciarListaSonando();
        barramenus = new Menus();
        setMenus();

        

        JButton botonPromocion = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                listas_manager.promocionarLista(pestanas_pendientes.getSelectedIndex());
            }
        });
        Pintado.anularPintadoBotonParaImagen(botonPromocion, "icons/promocionar.png", "icons/promocionar.png", new Dimension(60, 60));
        botonPromocion.setToolTipText("Promocionar lista");
        JPanel panelBotonPromocion = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        panelBotonPromocion.add(botonPromocion, gbc);
        panel.add(panelBotonPromocion, BorderLayout.CENTER);

        JPanel pestanasBotones = new JPanel(new BorderLayout());
        JButton anadirCancion = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                anadirCanciones();
            }
        });
        anadirCancion.setToolTipText("Añadir canción");
        Pintado.anularPintadoBotonParaImagen(anadirCancion, "icons/anadirConCarpeta.png", "icons/anadirConCarpeta.png", new Dimension(31, 31));


        JButton borrarCancion = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pestanas_pendientes.getTitleAt(0).equals("Inicio")) {
                    listas_manager.removeCancion(pestanas_pendientes.getSelectedIndex());
                } else {
                    JOptionPane pane = new JOptionPane("Debes tener al menos una lista con canciones creada", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
                    JDialog dialog = pane.createDialog(null, "Error");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                }
            }
        });

        borrarCancion.setToolTipText("Borrar canción");
        Pintado.anularPintadoBotonParaImagen(borrarCancion, "icons/borrarCancion1.png", "icons/borrarCancion1.png", new Dimension(31, 31));


        JPanel panelSituarBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelSituarBoton.add(anadirCancion);

        panelSituarBoton.add(borrarCancion);

        pestanasBotones.add(panelSituarBoton, BorderLayout.NORTH);

        pestanasBotones.add(pestanas_pendientes, BorderLayout.CENTER);

        panel.add(pestanasBotones, BorderLayout.EAST);

        panel.add(panelReproductor, BorderLayout.SOUTH);
        
        //Se crea el manager de la conexion, despues se crea el socket
        iniciarConexion();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void iniciarListasCanciones() {
        
        pestanas_pendientes = new PanelTablasPendientes();
        pestanas_pendientes.addTab("Inicio", pestanas_pendientes.generarPanelInicio());
        pestanas_pendientes.anadirPestanaFinal();
    }

    private void iniciarListaSonando() {
        
        tabla_sonando = new TablaSonando();
        listas_manager.getLista_sonando().addListaPromocionadaListener(tabla_sonando);
        JScrollPane scrollSonando = new JScrollPane(tabla_sonando);
        scrollSonando.setPreferredSize(ladoIzquierdo);
        panel.add(scrollSonando, BorderLayout.WEST);
    }

    public void anadirLista() {
        JOptionPane pane = new JOptionPane("Nombre Lista", JOptionPane.PLAIN_MESSAGE);
        pane.setWantsInput(true);
        JDialog dialog = pane.createDialog(null, "Lista Nueva");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
        String nombreLista = (String) pane.getInputValue();
        if (nombreLista != null && !nombreLista.equals("") && !nombreLista.equals("uninitializedValue")) {
            pestanas_pendientes.addPestana(nombreLista);
        }
    }

    private void iniciarConexion() {

        try {
            server = listas_manager.getConector();
            if (server.createSocket()) {
                log("Socket creado con exito!!!");
            }
        } catch (Exception ex) {
            log("Error al crear y conectar el socket: " + ex.toString());
            System.exit(0);
        }
    }

    private void setMenus() {
        
        barramenus.menuItems.get(0).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                listas_manager.setNombreServidor();
            }
        });
        
        barramenus.menuItems.get(1).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                anadirLista();
            }
        });

        barramenus.menuItems.get(2).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pestanas_pendientes.getTitleAt(0).equals("Inicio")) {
                    pestanas_pendientes.borrarPestana(pestanas_pendientes.getSelectedIndex());
                } else {
                    JOptionPane pane = new JOptionPane("Debes tener al menos una lista creada", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                    JDialog dialog = pane.createDialog(null, "Error");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                }
            }
        });

        barramenus.menuItems.get(3).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                listas_manager.promocionarLista(pestanas_pendientes.getSelectedIndex());
            }
        });
        
        barramenus.menuItems.get(4).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                anadirCanciones();
            }
        });
        
        barramenus.menuItems.get(5).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pestanas_pendientes.getTitleAt(0).equals("Inicio")) {
                    listas_manager.removeCancion(pestanas_pendientes.getSelectedIndex());
                } else {
                    JOptionPane pane = new JOptionPane("Debes tener al menos una lista con canciones creada", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
                    JDialog dialog = pane.createDialog(null, "Error");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                }
            }
        });
        
        barramenus.menuItems.get(6).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog ventana = new JDialog();
                ventana.setAlwaysOnTop(true);
                ventana.setSize(480, 200);
                ventana.setLocation((screen.width / 2) - 240, (screen.height / 2) - 100);
                JPanel panel = (JPanel) ventana.getContentPane();
                JTextArea text = new JTextArea("Esta SuperMegaChuchichuli aplicacion llamada \"social DJ\" ha sido desarrollada\npor Pakier Arribas(plata 3), Charls Gomez(plata 5, peor que arribas), Juans Frances, Moxas and Redondels.\n\nDisfrutala ;)");
                text.setEditable(false);
                text.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(text);
                ventana.setVisible(true);
            }
        });
        barramenus.add(botonesVentana());
        setJMenuBar(barramenus);

    }

    private JPanel botonesVentana() {
        JPanel panelBotonesVentana = new JPanel(new BorderLayout());
        Dimension dimensionBotonesVentana = new Dimension(25, 20);
        JButton btnCerrar = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarConexion();
                System.exit(0);
            }
        });
        Pintado.anularPintadoBotonParaImagen(btnCerrar, "icons/cerrar.png", "icons/cerrar2.png", dimensionBotonesVentana);

        final JButton btnMinimizar = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(Cursor.CROSSHAIR_CURSOR);
            }
        });
        Pintado.anularPintadoBotonParaImagen(btnMinimizar, "icons/minimizar.png", "icons/minimizar2.png", dimensionBotonesVentana);

        JPanel panelIntermediario = new JPanel(new GridLayout(1, 2));
        panelIntermediario.add(btnMinimizar);
        panelIntermediario.add(btnCerrar);
        panelBotonesVentana.add(panelIntermediario, BorderLayout.LINE_END);

        return panelBotonesVentana;
    }

    public void anadirCanciones() {
        if (pestanas_pendientes.getTitleAt(0).equals("Inicio")) {
            JOptionPane pane = new JOptionPane("Nombre Lista", JOptionPane.PLAIN_MESSAGE);
            pane.setWantsInput(true);
            JDialog dialog = pane.createDialog(null, "Lista Nueva");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            String nombreLista = (String) pane.getInputValue();
            if (nombreLista != null && !nombreLista.equals("") && !nombreLista.equals("uninitializedValue")) {
                pestanas_pendientes.addPestana(nombreLista);
                listas_manager.addCanciones(pestanas_pendientes.getSelectedIndex());
            }
        } else {
            listas_manager.addCanciones(pestanas_pendientes.getSelectedIndex());
        }
    }

    private void cerrarConexion() {

        ficheros_manager.guardarPreferencias();

        try {
            server.closeSocket();
        } catch (IOException ex) {
            FramePrincipal.log("Error al intentar cerrar el socket: " + ex.toString());
        } catch (Exception ex) {
            FramePrincipal.log("Error al intentar cerrar el socket: " + ex.toString());
        }
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosing(WindowEvent we) {

        cerrarConexion();
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
    }

    //Metodo para debug
    public static void log(String cadena) {
        System.out.println(cadena);
    }
}