
package main;

import conexion.ConnectionManager;
import java.awt.event.ActionEvent;
import tablas.Tabla;
import tablas.ModeloTabla;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import modelos.Cancion;
import modelos.ListaCanciones;
import modelos.ListasCancionesManager;
import reproductor.PlayerReproductor;



/**
 *
 * @author 66786575
 */
public class Main extends JFrame{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {

                JFrame main = new Main();
                main.setVisible(true);
            }
        });  
    }
    
    private JPanel panel = (JPanel) this.getContentPane();
    
    private ListasCancionesManager listas_manager;
    
    //private ArrayList <ListaCanciones> listasDeCanciones;
    private ListaCanciones listaPredeterminada;
    
    private ModeloTabla modeloTablaSonando;
    private ModeloTabla modeloTablaPendientes;
    
    private String[] nombresColumnaSonando = {"Cancion", "Votos"};
    private String[] nombresColumnaPendientes = {"Cancion"};
    private ArrayList <String>  contenidos;
        
    //private Tabla listaSonando;
    private Tabla listasPendientes;
    
    private JScrollPane scrollSonando;
    private JScrollPane scrollPendientes;
    private JTabbedPane pestanasPendientes;
    
    private JPanel botones;
    private JPanel conjunto;
    
    private BorderLayout border;
    private int numeroListas = 1;
    
    
    private JMenuBar barramenus = new JMenuBar();
    private JMenu[] menus;
    
    ConnectionManager server=null;
    int puerto_socket=2222;
    
    public Main(){
        
        
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 20)); 
        border = new BorderLayout();
        //Look and Feel de la aplicacion
        SetLookAndFeel();             
        
        //Listas de canciones en el programa en este momento
        //listasDeCanciones = new ArrayList();
        listas_manager=new ListasCancionesManager();
        listas_manager.listas_canciones=new ArrayList();
        
        
        //Se inicializan los menus
        menus=new JMenu[2];
        SetMenus();
        
        
        //El siguiente fragmento es solo una prueba hasta que tengamos la busqueda de canciones terminada
        //Servirá para meter a piñón el viernes la cancion con su path de la canción a la hora de reproducirla.
        
        listaPredeterminada = new ListaCanciones();
        //listasDeCanciones.add(listaPredeterminada);
        
        listaPredeterminada.getCanciones().add(new Cancion(1, "Gold on The Ceiling", "The Black Keys", "El Camino", "3:48", "1.mp3"));
        listaPredeterminada.getCanciones().add(new Cancion(2, "Never Gonna Give You Up", "Rick Astley", "RickRoll", "2:10", "2.mp3"));
        listaPredeterminada.getCanciones().add(new Cancion(3, "Sleep Alone", "Two Door Cinema Club", "Indie hits", "4:10", "3.mp3"));
        listaPredeterminada.getCanciones().add(new Cancion(4, "Sleep Alone without your mother tonight, always with anal sex", "Two Door Cinema Club", "Indie hits", "20:10", "3.mp3"));
//        listaPredeterminada.getCanciones().add(new Cancion(4, "Two Hearts", "Phill Collins", "Indie Phill is Indie", "3:45", "c://wheneveryou.mp3"));
//        listaPredeterminada.getCanciones().add(new Cancion(5, "I will survive", "Gloria Gaynor", "Gay hits", "4:47", "c://wheneveryou.mp3"));
//        listaPredeterminada.getCanciones().add(new Cancion(6, "9 crimes", "Damien Rice", "For the Hipster life", "3:40", "c://wheneveryou.mp3"));
//        listaPredeterminada.getCanciones().add(new Cancion(7, "Beer in Mexico", "Kenny Chesney", "Kenny hits", "4:29", "c://wheneveryou.mp3"));
//        listaPredeterminada.getCanciones().add(new Cancion(8, "Night Life", "Willie Nelson", "Willie Nelson & Family", "4:07", "c://wheneveryou.mp3"));
//        listaPredeterminada.getCanciones().add(new Cancion(9, "I just call to say I love you", "Stivie Wonder", "70's Hits", "4:22", "c://wheneveryou.mp3"));
//        listaPredeterminada.getCanciones().add(new Cancion(10, "Hound Dog", "Elvis Presley", "Elvis bonus album", "2:18", "c://wheneveryou.mp3"));
//        listaPredeterminada.getCanciones().add(new Cancion(11, "Another day in paradise", "Phill Collins", "Indie hits", "4:10", "c://wheneveryou.mp3"));
        listas_manager.addLista(listaPredeterminada);
        contenidos = new ArrayList();
        for(Cancion p: listaPredeterminada.getCanciones()){
            
            contenidos.add(p.getNombre());
        }      
        
        //Se inicializa la tabla de canciones en reproduccion
        
        modeloTablaSonando = new ModeloTabla(nombresColumnaSonando, 60);         
        //listaSonando = new Tabla(modeloTablaSonando);
        listas_manager.tabla_sonando=new Tabla(modeloTablaSonando);
        scrollSonando = new JScrollPane(listas_manager.tabla_sonando);
        scrollSonando.setPreferredSize(new Dimension(500,700));
        panel.add(scrollSonando, border.WEST);
        
        
        //Se inicializa la tabla de listas de canciones pendientes
        
        modeloTablaPendientes = new ModeloTabla(nombresColumnaPendientes, 60, contenidos);
        listasPendientes = new Tabla(modeloTablaPendientes);
        scrollPendientes = new JScrollPane(listasPendientes);
        pestanasPendientes = new JTabbedPane();
        
        pestanasPendientes.add(scrollPendientes, "Predeterminada");
        pestanasPendientes.setPreferredSize(new Dimension(600,300));     
        
        
        //Se inicializa el panel con los botones
        SetBotones();
        
        
        //Se inicializa y añade el panel conjunto a la interfaz
        conjunto = new JPanel();
        conjunto.add(botones, border.NORTH);
        conjunto.add(pestanasPendientes, border.SOUTH); 
        panel.add(conjunto, border.CENTER);
        listas_manager.promocionarLista(pestanasPendientes.getSelectedIndex());
        //Se crea el manager de la conexion, despues se crea el socket
        try {
            server=new ConnectionManager();
            if(server.createSocket(puerto_socket))
                log("Socket creado con exito!!!");
        } catch (Exception ex) {
            log("Error al crear y conectar el socket: "+ex.toString());
            System.exit(0);
        }
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    private void SetBotones(){
        
        setBotones(new JPanel());
        getBotones().setPreferredSize(new Dimension (700,250));
        
        JButton reproducirCancion = new JButton(new actions.ReproducirCancion());
        reproducirCancion.setText("Reproducir cancion");
        reproducirCancion.setPreferredSize(new Dimension (150,100));
        getBotones().add(reproducirCancion);
        
        JButton siguienteCancion = new JButton(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                listas_manager.playNext();
            }            
        });
        siguienteCancion.setText("Siguiente cancion");
        siguienteCancion.setPreferredSize(new Dimension (150,100));
        getBotones().add(siguienteCancion);    
        
        JButton anadirCanciones = new JButton(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerReproductor.pausar();
            }            
        });
        anadirCanciones.setText("Anadir canciones");
        anadirCanciones.setPreferredSize(new Dimension (150,100));
        getBotones().add(anadirCanciones);
        
        JButton borrarCancion = new JButton(new actions.BorrarCancion());
        borrarCancion.setText("Borrar cancion");
        borrarCancion.setPreferredSize(new Dimension (150,100));
        getBotones().add(borrarCancion);
        
        JButton promocionarLista = new JButton(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                listas_manager.promocionarLista(pestanasPendientes.getSelectedIndex());
            }            
        });
        promocionarLista.setText("Promocionar lista");
        promocionarLista.setPreferredSize(new Dimension (150,100));
        getBotones().add(promocionarLista);
        
        JButton anadirLista = new JButton(new actions.AnadirLista());
        anadirLista.setText("Anadir lista");
        anadirLista.setPreferredSize(new Dimension (150,100));
        getBotones().add(anadirLista);
        
        JButton borrarLista = new JButton(new actions.BorrarLista());
        borrarLista.setText("Borrar lista");
        borrarLista.setPreferredSize(new Dimension (150,100));
        getBotones().add(borrarLista);
        
        JButton salir = new JButton(new actions.Salir());
        salir.setText("Salir");
        salir.setPreferredSize(new Dimension (150,100));
        getBotones().add(salir);    
        
    }
    
    private void SetMenus(){
        
        getMenus()[0]= new JMenu("Archivo");
        getMenus()[0].setMnemonic('A');
        
        JMenuItem hola = new JMenuItem("Hola");
        hola.setMnemonic('H');
        getMenus()[0].add(hola);
        
        JMenuItem adios = new JMenuItem("Adios");
        adios.setMnemonic('d');
        getMenus()[0].add(adios);
        
        getBarramenus().add(getMenus()[0]);
        
        getMenus()[1] = new JMenu ("Sobre");
        getMenus()[1].setMnemonic('S');
        
        JMenuItem autores = new JMenuItem("autores");
        autores.setMnemonic('A');
        getMenus()[1].add(autores);
                
        getBarramenus().add(getMenus()[1]);
        
        setJMenuBar(getBarramenus());
        
    }
    
    private void SetLookAndFeel(){
        
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * @param panel the panel to set
     */
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    /**
     * @return the modeloTablaSonando
     */
    public ModeloTabla getModeloTablaSonando() {
        return modeloTablaSonando;
    }

    /**
     * @param modeloTablaSonando the modeloTablaSonando to set
     */
    public void setModeloTablaSonando(ModeloTabla modeloTablaSonando) {
        this.modeloTablaSonando = modeloTablaSonando;
    }

    /**
     * @return the modeloTablaPendientes
     */
    public ModeloTabla getModeloTablaPendientes() {
        return modeloTablaPendientes;
    }

    /**
     * @param modeloTablaPendientes the modeloTablaPendientes to set
     */
    public void setModeloTablaPendientes(ModeloTabla modeloTablaPendientes) {
        this.modeloTablaPendientes = modeloTablaPendientes;
    }

    /**
     * @return the nombresColumnaSonando
     */
    public String[] getNombresColumnaSonando() {
        return nombresColumnaSonando;
    }

    /**
     * @param nombresColumnaSonando the nombresColumnaSonando to set
     */
    public void setNombresColumnaSonando(String[] nombresColumnaSonando) {
        this.nombresColumnaSonando = nombresColumnaSonando;
    }

    /**
     * @return the nombresColumnaPendientes
     */
    public String[] getNombresColumnaPendientes() {
        return nombresColumnaPendientes;
    }

    /**
     * @param nombresColumnaPendientes the nombresColumnaPendientes to set
     */
    public void setNombresColumnaPendientes(String[] nombresColumnaPendientes) {
        this.nombresColumnaPendientes = nombresColumnaPendientes;
    }

    /**
     * @return the contenidos
     */
    public ArrayList <String> getContenidos() {
        return contenidos;
    }

    /**
     * @param contenidos the contenidos to set
     */
    public void setContenidos(ArrayList <String> contenidos) {
        this.contenidos = contenidos;
    }

    /**
     * @return the listasPendientes
     */
    public Tabla getListasPendientes() {
        return listasPendientes;
    }

    /**
     * @param listasPendientes the listasPendientes to set
     */
    public void setListasPendientes(Tabla listasPendientes) {
        this.listasPendientes = listasPendientes;
    }

    /**
     * @return the scrollSonando
     */
    public JScrollPane getScrollSonando() {
        return scrollSonando;
    }

    /**
     * @param scrollSonando the scrollSonando to set
     */
    public void setScrollSonando(JScrollPane scrollSonando) {
        this.scrollSonando = scrollSonando;
    }

    /**
     * @return the scrollPendientes
     */
    public JScrollPane getScrollPendientes() {
        return scrollPendientes;
    }

    /**
     * @param scrollPendientes the scrollPendientes to set
     */
    public void setScrollPendientes(JScrollPane scrollPendientes) {
        this.scrollPendientes = scrollPendientes;
    }

    /**
     * @return the pestanasPendientes
     */
    public JTabbedPane getPestanasPendientes() {
        return pestanasPendientes;
    }

    /**
     * @param pestanasPendientes the pestanasPendientes to set
     */
    public void setPestanasPendientes(JTabbedPane pestanasPendientes) {
        this.pestanasPendientes = pestanasPendientes;
    }

    /**
     * @return the botones
     */
    public JPanel getBotones() {
        return botones;
    }

    /**
     * @param botones the botones to set
     */
    public void setBotones(JPanel botones) {
        this.botones = botones;
    }

    /**
     * @return the conjunto
     */
    public JPanel getConjunto() {
        return conjunto;
    }

    /**
     * @param conjunto the conjunto to set
     */
    public void setConjunto(JPanel conjunto) {
        this.conjunto = conjunto;
    }

    /**
     * @return the border
     */
    public BorderLayout getBorder() {
        return border;
    }

    /**
     * @param border the border to set
     */
    public void setBorder(BorderLayout border) {
        this.border = border;
    }

    /**
     * @return the numeroListas
     */
    public int getNumeroListas() {
        return numeroListas;
    }

    /**
     * @param numeroListas the numeroListas to set
     */
    public void setNumeroListas(int numeroListas) {
        this.numeroListas = numeroListas;
    }

    /**
     * @return the barramenus
     */
    public JMenuBar getBarramenus() {
        return barramenus;
    }

    /**
     * @param barramenus the barramenus to set
     */
    public void setBarramenus(JMenuBar barramenus) {
        this.barramenus = barramenus;
    }

    /**
     * @return the menus
     */
    public JMenu[] getMenus() {
        return menus;
    }

    /**
     * @param menus the menus to set
     */
    public void setMenus(JMenu[] menus) {
        this.menus = menus;
    }
    //Metodo para debug
    
     public static void log(String cadena){
        System.out.println(cadena);
    }
}