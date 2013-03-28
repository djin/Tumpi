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
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import modelos.ListaCanciones;
import modelos.ListasCancionesManager;
import modelos.ModeloTabla;

/**
 *
 * @author 66786575
 */
//Habra que mirarse esta clase DETALLADAMENTE, para empezar a refactorizar aqui a saco, es simplemente demasiado grande.
public class FramePrincipal extends JFrame implements WindowListener {

    private JPanel panel = (JPanel) this.getContentPane();
    private ListasCancionesManager listas_manager;
    private FicherosManager ficheros_manager;
    private ModeloTabla modeloTablaSonando;
    private JTabbedPane pestanasPendientes;
    private ReproductorPanel panelReproductor;
    private ArrayList<String> nombresLista;
    private ArrayList<JButton> botonesCerrar = new ArrayList<JButton>();
    private Menus barramenus;
    private Dimension screen, ladoDerecho, ladoIzquierdo;
    ConnectionManager server = null;
    int puerto_socket = 2222;

    public FramePrincipal() {
        
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        ladoDerecho = new Dimension(screen.width * 55 / 100, 300);
        ladoIzquierdo = new Dimension(screen.width * 35 / 100, 50);
//      this.setSize(screen.width, screen.height - 30);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        Image icon = Imagenes.getImagen("icons/socialDJ.png").getImage();
        setIconImage(icon);
        setTitle("socialDj");
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));
        //Listas de canciones en el programa en este momento
        
        listas_manager = ListasCancionesManager.getInstance();

        panelReproductor = new ReproductorPanel(listas_manager);
        //Manejador de ficheros
        ficheros_manager = new FicherosManager(listas_manager);
        ficheros_manager.cargarPreferencias();
        
        iniciarListasCanciones();
        
        //Se inicializan los menus
        barramenus = new Menus();
        setMenus();

        //Se inicializa la tabla de canciones en reproduccion
        iniciarListaSonando();

        JButton botonPromocion = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                listas_manager.promocionarLista(pestanasPendientes.getSelectedIndex());
            }
        });
        anularPintadoBotonParaImagen(botonPromocion, "icons/promocionar.png", "icons/promocionar.png", new Dimension(60, 60));
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
        anularPintadoBotonParaImagen(anadirCancion, "icons/anadirConCarpeta.png", "icons/anadirConCarpeta.png", new Dimension(31, 31));


        JButton borrarCancion = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pestanasPendientes.getTitleAt(0).equals("Inicio")) {
                    listas_manager.removeCancion(pestanasPendientes.getSelectedIndex());
                } else {
                    JOptionPane pane = new JOptionPane("Debes tener al menos una lista con canciones creada", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
                    JDialog dialog = pane.createDialog(null, "Error");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                }
            }
        });

        borrarCancion.setToolTipText("Borrar canción");
        anularPintadoBotonParaImagen(borrarCancion, "icons/borrarCancion1.png", "icons/borrarCancion1.png", new Dimension(31, 31));


        JPanel panelSituarBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelSituarBoton.add(anadirCancion);

        panelSituarBoton.add(borrarCancion);

        pestanasBotones.add(panelSituarBoton, BorderLayout.NORTH);

        pestanasBotones.add(pestanasPendientes, BorderLayout.CENTER);

        panel.add(pestanasBotones, BorderLayout.EAST);

        panel.add(panelReproductor, BorderLayout.SOUTH);
        
        //Se crea el manager de la conexion, despues se crea el socket
        iniciarConexion();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addPestana(String nombreLista) {

        if (pestanasPendientes.getTitleAt(0).equals("Inicio")) {
            nombresLista.remove(0);
            pestanasPendientes.remove(0);
        }
        
        int numListas = pestanasPendientes.getTabCount() - 1;
        pestanasPendientes.remove(numListas);
        listas_manager.addLista(new ListaCanciones());
        listas_manager.listas_canciones.get(numListas).nombreLista=nombreLista;
        JScrollPane panelTabla = new JScrollPane(listas_manager.crearTabla());
        listas_manager.tablasPendientes.get(listas_manager.tablasPendientes.size() - 1).getActionMap().put("borrar", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                listas_manager.removeCancion(pestanasPendientes.getSelectedIndex());
            }
        });

        nombresLista.add(nombreLista);
        pestanasPendientes.addTab(nombreLista, panelTabla);
        GridBagConstraints gbc = new GridBagConstraints();
        PanelPestana panelPestana = new PanelPestana(nombreLista, gbc);
        JButton botonCerrar = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                borrarPestana(botonesCerrar.indexOf(e.getSource()));
            }
        });
        anularPintadoBotonParaImagen(botonCerrar, "icons/borrarpestana.png", "icons/borrarpestana2.png", new Dimension(13, 13));
        botonesCerrar.add(botonCerrar);
        gbc.gridx++;
        gbc.ipady = 3;
        gbc.weightx = 0;
        panelPestana.add(botonCerrar, gbc);
        pestanasPendientes.setTabComponentAt(nombresLista.size() - 1, panelPestana);
        pestanasPendientes.setSelectedIndex(nombresLista.size() - 1);
        anadirPestanaFinal();
    }

    private void iniciarListasCanciones() {

        nombresLista = new ArrayList();

        pestanasPendientes = new JTabbedPane();
        nombresLista.add("Inicio");

        pestanasPendientes.addTab(nombresLista.get(0), generarPanelInicio());
        anadirPestanaFinal();
        pestanasPendientes.setPreferredSize(ladoDerecho);
    }

    public void anadirPestanaFinal() {
        pestanasPendientes.addTab("AnadirPestana", null);
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
        anularPintadoBotonParaImagen(anadirPestana, "icons/anadirpestana.png", "icons/anadirpestana2.png", new Dimension(16, 16));
        pestanasPendientes.setTabComponentAt(pestanasPendientes.getTabCount() - 1, anadirPestana);
        pestanasPendientes.setEnabledAt(pestanasPendientes.getTabCount() - 1, false);
    }

    private void iniciarListaSonando() {
//CHIRRIA, esto deberia de estar casi con toda seguridad en una funcion de listas_manager.
        modeloTablaSonando = new ModeloTabla(listas_manager.nombresColumnaSonando, 1);
        listas_manager.tabla_sonando = new Tabla(modeloTablaSonando);
        listas_manager.tabla_sonando.getTableHeader().setReorderingAllowed(false);
        listas_manager.tabla_sonando.getColumnModel().getColumn(2).setMaxWidth(60);
        listas_manager.tabla_sonando.getColumnModel().getColumn(2).setMinWidth(60);
        listas_manager.tabla_sonando.getColumnModel().getColumn(1).setMinWidth(150);
        listas_manager.tabla_sonando.getColumnModel().getColumn(0).setMinWidth(150);
        listas_manager.tabla_sonando.setValueAt("Promociona una lista", 0, 0);
        listas_manager.tabla_sonando.setValueAt("", 0, 1);
        listas_manager.tabla_sonando.setValueAt("", 0, 2);
        listas_manager.tabla_sonando.getColumnModel().getColumn(2).setCellRenderer(new TableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel valor = new JLabel(value.toString());
                valor.setHorizontalAlignment(JLabel.CENTER);
                return valor;
            }
        });

        JScrollPane scrollSonando = new JScrollPane(listas_manager.tabla_sonando);
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
            addPestana(nombreLista);
        }
    }

    private void iniciarConexion() {

        try {
            server = new ConnectionManager();
            if (server.createSocket(puerto_socket)) {
                log("Socket creado con exito!!!");
            }
        } catch (Exception ex) {
            log("Error al crear y conectar el socket: " + ex.toString());
            System.exit(0);
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
        anularPintadoBotonParaImagen(image, "icons/anadirpestana.png", "icons/anadirpestana2.png", new Dimension(16, 16));
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

    private void setMenus() {
        
        barramenus.menuItems.get(0).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                anadirLista();
            }
        });

        barramenus.menuItems.get(1).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pestanasPendientes.getTitleAt(0).equals("Inicio")) {
                    borrarPestana(pestanasPendientes.getSelectedIndex());
                } else {
                    JOptionPane pane = new JOptionPane("Debes tener al menos una lista creada", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                    JDialog dialog = pane.createDialog(null, "Error");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                }
            }
        });

        barramenus.menuItems.get(2).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                listas_manager.promocionarLista(pestanasPendientes.getSelectedIndex());
            }
        });
        
        barramenus.menuItems.get(3).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //GuardarSesion
            }
        });
        
        barramenus.menuItems.get(4).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(ficheros_manager.cargarSesion()){
                    
                }
                else{
                    
                }
            }
        });
        
        barramenus.menuItems.get(5).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                anadirCanciones();
            }
        });
        
        barramenus.menuItems.get(6).addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pestanasPendientes.getTitleAt(0).equals("Inicio")) {
                    listas_manager.removeCancion(pestanasPendientes.getSelectedIndex());
                } else {
                    JOptionPane pane = new JOptionPane("Debes tener al menos una lista con canciones creada", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
                    JDialog dialog = pane.createDialog(null, "Error");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                }
            }
        });
        
        barramenus.menuItems.get(7).addActionListener(new ActionListener() {

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
        anularPintadoBotonParaImagen(btnCerrar, "icons/cerrar.png", "icons/cerrar2.png", dimensionBotonesVentana);

        final JButton btnMinimizar = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(Cursor.CROSSHAIR_CURSOR);
            }
        });
        anularPintadoBotonParaImagen(btnMinimizar, "icons/minimizar.png", "icons/minimizar2.png", dimensionBotonesVentana);

        JPanel panelIntermediario = new JPanel(new GridLayout(1, 2));
        panelIntermediario.add(btnMinimizar);
        panelIntermediario.add(btnCerrar);
        panelBotonesVentana.add(panelIntermediario, BorderLayout.LINE_END);

        return panelBotonesVentana;
    }

    private void anularPintadoBotonParaImagen(JButton boton, String botonSinRaton, String botonConRaton, Dimension tamano) {
        boton.addMouseListener(new MyMouseListener(boton, Imagenes.getImagen(botonSinRaton), Imagenes.getImagen(botonConRaton)));
        boton.setIcon(Imagenes.getImagen(botonSinRaton));
        boton.setPreferredSize(tamano);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setBackground(null);
        boton.setContentAreaFilled(false);
    }

    public void anadirCanciones() {
        if (pestanasPendientes.getTitleAt(0).equals("Inicio")) {
            JOptionPane pane = new JOptionPane("Nombre Lista", JOptionPane.PLAIN_MESSAGE);
            pane.setWantsInput(true);
            JDialog dialog = pane.createDialog(null, "Lista Nueva");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            String nombreLista = (String) pane.getInputValue();
            if (nombreLista != null && !nombreLista.equals("") && !nombreLista.equals("uninitializedValue")) {
                addPestana(nombreLista);
                listas_manager.addCanciones(pestanasPendientes.getSelectedIndex());
            }
        } else {
            listas_manager.addCanciones(pestanasPendientes.getSelectedIndex());
        }
    }

    public void borrarPestana(int pestanaBorrar) {
        JOptionPane pane = new JOptionPane("¿Desea borrar la lista?", JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = pane.createDialog(null, "Confirmar borrado");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
        Integer opcion = (Integer) pane.getValue();
        if (opcion != null && opcion == 0) {
            botonesCerrar.remove(pestanaBorrar);
            pestanasPendientes.remove(pestanaBorrar);
            listas_manager.removeLista(pestanaBorrar);
            nombresLista.remove(pestanaBorrar);
            if (pestanasPendientes.getSelectedIndex() == pestanasPendientes.getTabCount() - 1) {
                pestanasPendientes.setSelectedIndex(pestanasPendientes.getTabCount() - 2);
            }
            if (pestanasPendientes.getTabCount() == 1) {
                nombresLista.add("Inicio");
                pestanasPendientes.remove(pestanasPendientes.getTabCount() - 1);
                pestanasPendientes.addTab(nombresLista.get(0), generarPanelInicio());
                anadirPestanaFinal();
            }
        }
    }

    private void cerrarConexion() {

        ficheros_manager.guardarPreferencias();

        try {
            ConnectionManager.socket.closeSocket();
        } catch (IOException ex) {
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