/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import conexion.ConnectionManager;
import ficheros.FicherosManager;
import java.awt.*;
import java.awt.event.ActionEvent;
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
public class FramePrincipal extends JFrame implements WindowListener {

    private JPanel panel = (JPanel) this.getContentPane();
    private ListasCancionesManager listas_manager;
    private FicherosManager ficheros_manager;
    private ModeloTabla modeloTablaSonando;
    private JTabbedPane pestanasPendientes;
    private ReproductorPanel panelReproductor;
    private ArrayList<String> nombresLista;
    private ArrayList<JButton> botonesCerrar = new ArrayList<JButton>();
    private JMenuBar barramenus = new JMenuBar();
    private JMenu[] menus;
    private Dimension screen, ladoDerecho, ladoIzquierdo;
    ConnectionManager server = null;
    int puerto_socket = 2222;
    private boolean nuevo;

    public FramePrincipal() {

        screen = Toolkit.getDefaultToolkit().getScreenSize();
        ladoDerecho = new Dimension(screen.width * 55 / 100, 300);
        ladoIzquierdo = new Dimension(screen.width * 35 / 100, 50);
//      this.setSize(screen.width, screen.height - 30);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));
        //Listas de canciones en el programa en este momento
        listas_manager = ListasCancionesManager.getInstance();

        panelReproductor = new ReproductorPanel(listas_manager);
        //Manejador de ficheros
        ficheros_manager = new FicherosManager(listas_manager);
        nuevo = ficheros_manager.cargarPreferencias();

        if (nuevo) {
            //Se inicializa las tablas de listas de canciones pendientes dado que no habia ninguno anterior
            iniciarListasCanciones();
        } else {
            //temporal, hasta que se implemente el cargado de listas.
            iniciarListasCanciones();
        }
        //Se inicializan los menus
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
                if (pestanasPendientes.getTitleAt(0).equals("Inicio")) {
                    String nombreLista = JOptionPane.showInputDialog(new Label("Nombre Lista"), "Nombre Lista", "ListaNueva", JOptionPane.PLAIN_MESSAGE);
                    if (nombreLista != null && !nombreLista.equals("")) {
                        addPestana(nombreLista);
                        listas_manager.addCanciones(pestanasPendientes.getSelectedIndex());
                    }
                }
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
                    JOptionPane.showMessageDialog(null, "Debes tener al menos una lista con canciones creada");
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
        pestanasPendientes.remove(pestanasPendientes.getTabCount() - 1);
        listas_manager.addLista(new ListaCanciones());
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

                int opcion = JOptionPane.showConfirmDialog(null, "¿Desea borrar la lista?");
                if (opcion == 0) {
                    int pestanaBorrar = botonesCerrar.indexOf(e.getSource());
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
        JButton anadirPestana = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombreLista = JOptionPane.showInputDialog(new Label("Nombre Lista"), "Nombre Lista", "ListaNueva", JOptionPane.PLAIN_MESSAGE);
                if (nombreLista != null && !nombreLista.equals("")) {
                    addPestana(nombreLista);
                }
            }
        });
        anadirPestana.setToolTipText("Añadir Lista");
        anularPintadoBotonParaImagen(anadirPestana, "icons/anadirpestana.png", "icons/anadirpestana2.png", new Dimension(16, 16));
        pestanasPendientes.setTabComponentAt(pestanasPendientes.getTabCount() - 1, anadirPestana);
        pestanasPendientes.setEnabledAt(pestanasPendientes.getTabCount() - 1, false);
    }

    private void iniciarListaSonando() {

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

        menus = new JMenu[2];

        menus[0] = new JMenu("Archivo");
        menus[0].setMnemonic('A');

        JMenuItem hola = new JMenuItem("Hola");
        hola.setMnemonic('H');
        menus[0].add(hola);

        JMenuItem adios = new JMenuItem("Adios");
        adios.setMnemonic('d');
        menus[0].add(adios);

        barramenus.add(menus[0]);

        menus[1] = new JMenu("Sobre");
        menus[1].setMnemonic('S');

        JMenuItem autores = new JMenuItem("autores");
        autores.setMnemonic('A');
        menus[1].add(autores);

        barramenus.add(menus[1]);
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
        boton.addMouseListener(new MyMouseListener(boton, botonSinRaton, botonConRaton));
        boton.setIcon(new ImageIcon(botonSinRaton));
        boton.setPreferredSize(tamano);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setBackground(null);
        boton.setContentAreaFilled(false);
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