
package main;

import conexion.ConnectionManager;
import tablas.Tabla;
import tablas.ModeloTabla;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import modelos.Cancion;
import modelos.ListaCanciones;



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
    
    private ArrayList <ListaCanciones> listasDeCanciones;
    private ListaCanciones listaPredeterminada;
    
    private ModeloTabla modeloTablaSonando;
    private ModeloTabla modeloTablaPendientes;
    
    private String[] nombresColumnaSonando = {"Cancion", "Votos"};
    private String[] nombresColumnaPendientes = {"Cancion"};
    private ArrayList <String>  contenidos;
        
    private Tabla listaSonando;
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
        
        //Se crea el socket que dara servicio a los clientes
        
        
        //Listas de canciones en el programa en este momento
        listasDeCanciones = new ArrayList();
        
        
        //Se inicializan los menus
        menus=new JMenu[2];
        SetMenus();
        
        
        //El siguiente fragmento es solo una prueba hasta que tengamos la busqueda de canciones terminada
        //Servirá para meter a piñón el viernes la cancion con su path de la canción a la hora de reproducirla.
        
        listaPredeterminada = new ListaCanciones();
        listasDeCanciones.add(listaPredeterminada);
        
        listaPredeterminada.getCanciones().add(new Cancion(1, "nevergonnagive", "lololol", "rick astley", "3:48", "c://nevergonna.mp3"));
        listaPredeterminada.getCanciones().add(new Cancion(2, "wheneveryouneed", "lolo2", "rick astley", "2:10", "c://wheneveryou.mp3"));
        
        contenidos = new ArrayList();
        for(Cancion p: listaPredeterminada.getCanciones()){
            
            contenidos.add(p.getNombre());
        }      
        
        //Se inicializa la tabla de canciones en reproduccion
        
        modeloTablaSonando = new ModeloTabla(nombresColumnaSonando, 60);         
        listaSonando = new Tabla(modeloTablaSonando);
        scrollSonando = new JScrollPane(listaSonando);
        scrollSonando.setPreferredSize(new Dimension(500,700));
        panel.add(scrollSonando, border.WEST);
        
        
        //Se inicializa la tabla de listas de canciones pendientes
        
        modeloTablaPendientes = new ModeloTabla(nombresColumnaPendientes, 60, contenidos);
        listasPendientes = new Tabla(modeloTablaPendientes);
        scrollPendientes = new JScrollPane(listasPendientes);
        pestanasPendientes = new JTabbedPane();
        
        pestanasPendientes.add(scrollPendientes, "Predeterminada");
        pestanasPendientes.setPreferredSize(new Dimension(700,400));     
        
        
        //Se inicializa el panel con los botones
        SetBotones();
        
        
        //Se inicializa y añade el panel conjunto a la interfaz
        conjunto = new JPanel();
        conjunto.add(botones, border.NORTH);
        conjunto.add(pestanasPendientes, border.SOUTH); 
        panel.add(conjunto, border.CENTER);
        
        try {
            server=new ConnectionManager(listaSonando,listaPredeterminada);
            if(server.createSocket(puerto_socket))
                log("Socket creado con exito!!!");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.toString());
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
        
        JButton siguienteCancion = new JButton(new actions.SiguienteCancion());
        siguienteCancion.setText("Siguiente cancion");
        siguienteCancion.setPreferredSize(new Dimension (150,100));
        getBotones().add(siguienteCancion);    
        
        JButton anadirCanciones = new JButton(new actions.AnadirCanciones());
        anadirCanciones.setText("Anadir canciones");
        anadirCanciones.setPreferredSize(new Dimension (150,100));
        getBotones().add(anadirCanciones);
        
        JButton borrarCancion = new JButton(new actions.BorrarCancion());
        borrarCancion.setText("Borrar cancion");
        borrarCancion.setPreferredSize(new Dimension (150,100));
        getBotones().add(borrarCancion);
        
        JButton promocionarLista = new JButton(new actions.PromocionarLista(pestanasPendientes.getSelectedIndex(),listasDeCanciones, listaSonando,server));
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
     * @return the listaSonando
     */
    public Tabla getListaSonando() {
        return listaSonando;
    }

    /**
     * @param listaSonando the listaSonando to set
     */
    public void setListaSonando(Tabla listaSonando) {
        this.listaSonando = listaSonando;
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