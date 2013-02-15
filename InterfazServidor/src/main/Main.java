package main;

import conexion.ConnectionManager;
import elementosInterfaz.DialogoNombreLista;
import elementosInterfaz.ModeloTabla;
import elementosInterfaz.MyMouseListener;
import elementosInterfaz.ReproductorPanel;
import elementosInterfaz.Tabla;
import ficheros.FicherosManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import modelos.ListaCanciones;
import modelos.ListasCancionesManager;
import reproductor.PlayerReproductor;

/**
 * Prueba para hacer push desde netbeans
 * @author 66786575
 */
public class Main extends JFrame implements WindowListener {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Main f = new Main();
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(f);
                    f.setUndecorated(true);
                    f.setVisible(true);
                } catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }
    private JPanel panel = (JPanel) this.getContentPane();
    private static ListasCancionesManager listas_manager;
    private static FicherosManager ficheros_manager;
    private ModeloTabla modeloTablaSonando;
    private ModeloTabla modeloTablaPredeterminado;
    private Tabla tablaPredeterminada;
    private JScrollPane scrollSonando;
    private JScrollPane scrollPendientesPredeterminado;
    private static JTabbedPane pestanasPendientes;
    private JPanel botones;
    private JPanel conjunto;
    private ReproductorPanel panelReproductor;
    private BorderLayout border;
    private int listaSelec;
    public static ArrayList<String> nombresLista;
    private JMenuBar barramenus = new JMenuBar();
    private JMenu[] menus;
    ConnectionManager server = null;
    int puerto_socket = 2222;
    private boolean nuevo;

    public Main() {

//      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//      this.setSize(screen.width, screen.height - 30);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        border = new BorderLayout();

        //Listas de canciones en el programa en este momento
        listas_manager = new ListasCancionesManager();


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

        //Se inicializa el panel con los botones
        setBotones();

        conjunto = new JPanel();
        conjunto.add(botones, BorderLayout.NORTH);
        conjunto.add(pestanasPendientes, BorderLayout.SOUTH);
        panel.add(conjunto, BorderLayout.CENTER);
        panel.add(panelReproductor, BorderLayout.SOUTH);

        //Se crea el manager de la conexion, despues se crea el socket
        iniciarConexion();

        this.addWindowListener(this);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void setBotones() {

        botones = new JPanel();
        botones.setPreferredSize(new Dimension(700, 250));


        JButton iniciar_Siguiente = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                listas_manager.playNext();
            }
        });
        iniciar_Siguiente.setText("Iniciar/Siguiente canción");
        iniciar_Siguiente.setPreferredSize(new Dimension(150, 100));
        botones.add(iniciar_Siguiente);



        JButton pausar_Reanudar = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                PlayerReproductor.pausar();
            }
        });
        pausar_Reanudar.setText("Pausar/Reanudar");
        pausar_Reanudar.setPreferredSize(new Dimension(150, 100));
        botones.add(pausar_Reanudar);



        JButton anadirCanciones = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                listas_manager.addCanciones(pestanasPendientes.getSelectedIndex());
            }
        });
        anadirCanciones.setText("Anadir canciones");
        anadirCanciones.setPreferredSize(new Dimension(150, 100));
        botones.add(anadirCanciones);



        JButton borrarCancion = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                listas_manager.removeCancion(pestanasPendientes.getSelectedIndex());
            }
        });
        borrarCancion.setText("Borrar canción");
        borrarCancion.setPreferredSize(new Dimension(150, 100));
        botones.add(borrarCancion);



        JButton promocionarLista = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                listas_manager.promocionarLista(pestanasPendientes.getSelectedIndex());
            }
        });
        promocionarLista.setText("Promocionar lista");
        promocionarLista.setPreferredSize(new Dimension(150, 100));
        botones.add(promocionarLista);



        JButton anadirLista = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new DialogoNombreLista();
            }
        });
        anadirLista.setText("Anadir lista");
        anadirLista.setPreferredSize(new Dimension(150, 100));
        botones.add(anadirLista);



        JButton borrarLista = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (listas_manager.listas_canciones.size() != 1) {

                    listaSelec = pestanasPendientes.getSelectedIndex();
                    pestanasPendientes.remove(listaSelec);
                    listas_manager.removeLista(listaSelec);
                    nombresLista.remove(listaSelec);

                } else {
                    JOptionPane.showMessageDialog(null, "Tiene que tener al menos una lista abierta");
                }

            }
        });
        borrarLista.setText("Borrar lista");
        borrarLista.setPreferredSize(new Dimension(150, 100));
        botones.add(borrarLista);



        JButton salir = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarConexion();
                System.exit(0);
            }
        });
        salir.setText("Salir");
        salir.setPreferredSize(new Dimension(150, 100));
        botones.add(salir);
    }

    public static void addPestana(String nombreLista) {

        listas_manager.addLista(new ListaCanciones());

        listas_manager.tablasPendientes.add(new Tabla(new ModeloTabla(listas_manager.nombresColumnaPendientes, 1)));
        listas_manager.tablasPendientes.get(listas_manager.tablasPendientes.size() - 1).setValueAt("Añade Canciones", 0, 0);
        nombresLista.add(nombreLista);
        pestanasPendientes.addTab(nombreLista, new JScrollPane(listas_manager.tablasPendientes.get(listas_manager.tablasPendientes.size() - 1)));
    }

    private void iniciarListasCanciones() {

        listas_manager.tablasPendientes = new ArrayList();
        nombresLista = new ArrayList();

        modeloTablaPredeterminado = new ModeloTabla(listas_manager.nombresColumnaPendientes, 1);
        tablaPredeterminada = new Tabla(modeloTablaPredeterminado);
        tablaPredeterminada.setValueAt("Añade Canciones", 0, 0);

        listas_manager.tablasPendientes.add(tablaPredeterminada);
        listas_manager.addLista(new ListaCanciones());

        scrollPendientesPredeterminado = new JScrollPane(tablaPredeterminada);
        pestanasPendientes = new JTabbedPane();
        nombresLista.add("Predeterminada");
        pestanasPendientes.add(scrollPendientesPredeterminado, nombresLista.get(0));
        pestanasPendientes.setPreferredSize(new Dimension(600, 300));
    }

    private void iniciarListaSonando() {

        modeloTablaSonando = new ModeloTabla(listas_manager.nombresColumnaSonando, 1);
        listas_manager.tabla_sonando = new Tabla(modeloTablaSonando);

        listas_manager.tabla_sonando.setValueAt("Promociona una lista", 0, 0);
        listas_manager.tabla_sonando.setValueAt("", 0, 1);

        scrollSonando = new JScrollPane(listas_manager.tabla_sonando);
        scrollSonando.setPreferredSize(new Dimension(500, 700));
        panel.add(scrollSonando, border.WEST);
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
        JButton btnCerrar = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarConexion();
                System.exit(0);
            }
        });
        btnCerrar.addMouseListener(new MyMouseListener(btnCerrar, true));
        btnCerrar.setIcon(new ImageIcon("icons/cerrar.png"));
        btnCerrar.setPreferredSize(new Dimension(25, 20));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setBackground(null);
        btnCerrar.setContentAreaFilled(false);

        final JButton btnMinimizar = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(Cursor.CROSSHAIR_CURSOR);
            }
        });
        btnMinimizar.addMouseListener(new MyMouseListener(btnMinimizar, false));
        btnMinimizar.setIcon(new ImageIcon("icons/minimizar.png"));
        btnMinimizar.setPreferredSize(new Dimension(25, 20));
        btnMinimizar.setFocusPainted(false);
        btnMinimizar.setBorderPainted(false);
        btnMinimizar.setBackground(null);
        btnMinimizar.setContentAreaFilled(false);

        JPanel panelIntermediario = new JPanel(new GridLayout(1, 2));
        panelIntermediario.add(btnMinimizar);
        panelIntermediario.add(btnCerrar);
        panelBotonesVentana.add(panelIntermediario, BorderLayout.LINE_END);

        return panelBotonesVentana;
    }

    private void cerrarConexion() {

        ficheros_manager.guardarPreferencias();

        try {
            ConnectionManager.socket.closeSocket();
        } catch (IOException ex) {
            Main.log("Error al intentar cerrar el socket: " + ex.toString());
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