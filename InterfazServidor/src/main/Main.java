
package main;

import tablas.Tabla;
import tablas.ModeloTabla;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
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
    private ListaCanciones listaPredeterminada;
    
    private JMenuBar barramenus = new JMenuBar();
    private JMenu[] menus;
    
    
    public Main(){
        
        menus=new JMenu[2];
        SetMenus();
        
        listasDeCanciones = new ArrayList();
        
        //El siguiente fragmento es solo una prueba hasta que tengamos la busqueda de canciones terminada
        //Servirá para meter a piñón el viernes la cancion con su path de la canción a la hora de reproducirla.
        
        listaPredeterminada = new ListaCanciones();
        listaPredeterminada.getCanciones().add(new Cancion(1, "nevergonnagive", "lololol", "rick astley", "3:48", "c://nevergonna.mp3"));
        contenidos = new ArrayList();
        contenidos.add(listaPredeterminada.getCanciones().get(0).getNombre());
        
        
        modeloTablaSonando = new ModeloTabla(nombresColumnaSonando, 60);
        modeloTablaPendientes = new ModeloTabla(nombresColumnaPendientes, 60, contenidos);
        
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 27, 20)); 
        border = new BorderLayout();
        
        listaSonando = new Tabla(modeloTablaSonando);
        scrollSonando = new JScrollPane(listaSonando);
        scrollSonando.setPreferredSize(new Dimension(500,700));
        panel.add(scrollSonando, border.WEST);
        
        conjunto = new JPanel();
        
        SetBotones();
        botones.setPreferredSize(new Dimension (700,250));
        conjunto.add(botones, border.NORTH);
        
        listasPendientes = new Tabla(modeloTablaPendientes);
        scrollPendientes = new JScrollPane(listasPendientes);
        pestanasPendientes = new JTabbedPane();
        
        pestanasPendientes.add(scrollPendientes, "Predeterminada");
        pestanasPendientes.setPreferredSize(new Dimension(700,400));
        conjunto.add(pestanasPendientes, border.SOUTH);      
        
        panel.add(conjunto, border.CENTER);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    private void SetBotones(){
        
        setBotones(new JPanel());
        
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
        
        JButton promocionarLista = new JButton(new actions.PromocionarLista(pestanasPendientes.getSelectedIndex(),listasDeCanciones));
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
}
